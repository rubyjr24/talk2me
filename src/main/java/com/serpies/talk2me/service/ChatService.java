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
import com.serpies.talk2me.model.CreateChatRequestDto;
import com.serpies.talk2me.utilities.Assert;
import com.serpies.talk2me.exceptions.NotValidTokenException;
import com.serpies.talk2me.security.auth.JwtUtil;
import com.serpies.talk2me.security.websocket.WebSocketEventListener;
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
    private IChatDao chatDao;

    @Autowired
    private IChatUserDao chatUserDao;

    @Autowired
    private IUserDao userDao;

    @Transactional
    public void createChat(CreateChatRequestDto createChatRequestDto, String token){

        Assert.ifCondition(!jwtUtil.isTokenValid(token), new NotValidTokenException("The token must be valid"));

        Long userId = jwtUtil.getUserId(token);
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

            String sessionId = WebSocketEventListener.getSessionIdForUser(user.getId());

            if (sessionId == null) continue;

            this.messagingTemplate.convertAndSendToUser(
                    user.getEmail(),
                    NEW_CHAT_PATH,
                    chatDto
            );

        }

    }

}
