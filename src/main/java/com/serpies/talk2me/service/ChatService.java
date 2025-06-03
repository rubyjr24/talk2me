package com.serpies.talk2me.service;

import com.serpies.talk2me.db.dao.IChatDao;
import com.serpies.talk2me.db.dao.IChatUserDao;
import com.serpies.talk2me.db.dao.IUserDao;
import com.serpies.talk2me.db.dto.ChatDto;
import com.serpies.talk2me.db.dto.ChatUserDto;
import com.serpies.talk2me.db.dto.UserDto;
import com.serpies.talk2me.db.entity.Chat;
import com.serpies.talk2me.db.entity.ChatUser;
import com.serpies.talk2me.db.entity.User;
import com.serpies.talk2me.exceptions.NotValidTokenException;
import com.serpies.talk2me.model.CreateChatRequestDto;
import com.serpies.talk2me.utilities.auth.AuthUtil;
import com.serpies.talk2me.utilities.Assert;
import com.serpies.talk2me.utilities.auth.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ChatService {

    private static final String NEW_CHAT_PATH = "/private/chat/new";

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private IChatDao chatDao;

    @Autowired
    private IChatUserDao chatUserDao;

    @Autowired
    private IUserDao userDao;

    @Transactional
    public void createChat(CreateChatRequestDto createChatRequestDto, String token){

        Assert.isNull(createChatRequestDto, "No data received");
        Assert.isNull(token, "Token not received");
        Assert.isNull(createChatRequestDto.getName(), "Name not received");
        Assert.isNull(createChatRequestDto.getUserIds(), "User list has not been received");

        Long userId = this.authUtil.validateAndGetUser(token);
        Set<Long> usersIds = createChatRequestDto.getUserIds();
        usersIds.add(userId);

        Assert.ifCondition(usersIds.size() == 1, "Unable to create a chat for one user");
        Assert.ifCondition(createChatRequestDto.isPrivate() && usersIds.size() > 2, "Too many users for private chat");

        List<User> users = this.userDao.findUsersByUserIds(usersIds);
        Assert.ifCondition(users.size() != usersIds.size(), "Not all users exists the app");

        Chat chat = new Chat();
        chat.setName(createChatRequestDto.getName());
        chat.setDescription(createChatRequestDto.getDescription());
        chat.setPrivate(createChatRequestDto.isPrivate());

        chat = chatDao.save(chat);

        List<ChatUser> chatUserList = new ArrayList<>();
        for (User user: users){

            ChatUser chatUser = new ChatUser();
            chatUser.setChatId(chat.getId());
            chatUser.setUserId(user.getId());
            chatUser.setIsAdmin(user.getId() == userId);

            chatUserList.add(chatUser);
        }

        this.chatUserDao.saveAll(chatUserList);

        Set<ChatUserDto> chatUserDtoSet = new HashSet<>();
        for (User user: users){
            chatUserDtoSet.add(new ChatUserDto(
                    null,
                    user.getId() == userId,
                    new UserDto(
                            user.getId(),
                            user.getName(),
                            user.getSurname(),
                            user.getEmail(),
                            user.getLastConnection(),
                            user.getGender()
                    ))
            );
        }

        ChatDto chatDto = new ChatDto(
                chat.getId(),
                chat.getName(),
                chat.getDescription(),
                chat.isPrivate(),
                chatUserDtoSet
        );

        for (User user: users){

            this.messagingTemplate.convertAndSendToUser(
                    user.getEmail(),
                    NEW_CHAT_PATH,
                    chatDto
            );

        }

    }
    
    public List<ChatDto> getAllChatsByUserId(String authorizationHeaderValue){

        Assert.isNull(authorizationHeaderValue, new NotValidTokenException("You must have a token"));

        String tokenParesed = this.authUtil.getTokenFromAuthorization(authorizationHeaderValue);
        Long userId = this.authUtil.validateAndGetUser(tokenParesed);

        List<Chat> chats = this.chatDao.findByUserIdFechingUsers(userId);
        
        List<ChatDto> result = new ArrayList<>();
        for (Chat chat: chats){
            
            ChatDto chatDto = new ChatDto();
            chatDto.setId(chat.getId());
            chatDto.setName(chat.getName());
            chatDto.setDescription(chat.getDescription());
            chatDto.setIsPrivate(chat.isPrivate());
            
            Set<ChatUserDto> chatUserDtoSet = new HashSet<>();
            
            for (ChatUser chatUser: chat.getChatUserList()){
                ChatUserDto chatUserDto = getChatUserDto(chatUser);
                chatUserDtoSet.add(chatUserDto);
            }

            chatDto.setUsers(chatUserDtoSet);
            
            result.add(chatDto);
            
        }
        
        return result;
        
    }

    private static ChatUserDto getChatUserDto(ChatUser chatUser) {
        ChatUserDto chatUserDto = new ChatUserDto();
        chatUserDto.setAdmin(chatUser.getIsAdmin());

        User user = chatUser.getUser();

        UserDto userDto = new UserDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getLastConnection(),
                user.getGender()
        );

        chatUserDto.setUser(userDto);
        return chatUserDto;
    }

}
