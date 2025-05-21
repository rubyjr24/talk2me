package com.serpies.talk2me.service;

import com.serpies.talk2me.db.dao.*;
import com.serpies.talk2me.db.dto.MessageDto;
import com.serpies.talk2me.db.entity.*;
import com.serpies.talk2me.exceptions.ChatNotFoundException;
import com.serpies.talk2me.exceptions.NotValidTokenException;
import com.serpies.talk2me.exceptions.UserNotFoundException;
import com.serpies.talk2me.exceptions.UserNotInChatException;
import com.serpies.talk2me.model.CreateMessageRequestDto;
import com.serpies.talk2me.security.auth.AuthUtil;
import com.serpies.talk2me.security.auth.JwtUtil;
import com.serpies.talk2me.utilities.Assert;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {

    private static final String NEW_MESSAGE_PATH = "/private/message/new";

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private IMessageDao messageDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IChatDao chatDao;

    @Autowired
    private IChatUserDao chatUserDao;

    @Autowired
    private IChatMessageDao chatMessageDao;

    @Autowired
    private ITextMessageDao textMessageDao;

    @Transactional
    public void createMessage(CreateMessageRequestDto createMessageRequestDto, String token){

        Assert.isNull(createMessageRequestDto, "No data received");
        Assert.isNull(token, "Token not received");
        Assert.isNull(createMessageRequestDto.getChatId(), "Chat id not received");
        Assert.isNull(createMessageRequestDto.getMessage(), "Message not received");

        Long userId = this.authUtil.validateAndGetUser(token);
        Optional<User> userOptional = this.userDao.findById(userId);

        Assert.ifCondition(userOptional.isEmpty(), new UserNotFoundException("The user must be exist"));

        Optional<Chat> chatOptional = this.chatDao.findByIdFechingUsers(createMessageRequestDto.getChatId());

        Assert.ifCondition(chatOptional.isEmpty(), new ChatNotFoundException("The chat must be exist"));
        Assert.ifCondition(!this.chatUserDao.userExistsInChat(userId, createMessageRequestDto.getChatId()), new UserNotInChatException("The user must be in the chat to send a message"));

        Chat chat = chatOptional.get();

        Message message = new Message();
        this.messageDao.save(message);

        TextMessage textMessage = new TextMessage();
        textMessage.setMessageId(message.getId());
        textMessage.setMessageData(createMessageRequestDto.getMessage());
        textMessage.setMessage(message);
        this.textMessageDao.save(textMessage);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessageId(message);
        chatMessage.setChatId(chat);
        this.chatMessageDao.save(chatMessage);

        MessageDto messageDto = new MessageDto();
        messageDto.setChatId(chat.getId());
        messageDto.setUserId(userId);
        messageDto.setMessage(createMessageRequestDto.getMessage());
        messageDto.setCreatedAt(message.getCreatedAt());
        messageDto.setImportance(message.getImportance());

        for (ChatUser chatUser: chat.getChatUserList()){

            User userToSend = chatUser.getUser();

            //if (userToSend.getId() == userId) continue;

            this.messagingTemplate.convertAndSendToUser(
                    userToSend.getEmail(),
                    NEW_MESSAGE_PATH,
                    messageDto
            );

        }

    }

}
