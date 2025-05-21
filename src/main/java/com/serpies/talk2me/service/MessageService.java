package com.serpies.talk2me.service;

import com.serpies.talk2me.db.dao.*;
import com.serpies.talk2me.db.dto.MessageDto;
import com.serpies.talk2me.db.entity.*;
import com.serpies.talk2me.exceptions.ChatNotFoundException;
import com.serpies.talk2me.exceptions.InternalServerError;
import com.serpies.talk2me.exceptions.UserNotFoundException;
import com.serpies.talk2me.exceptions.UserNotInChatException;
import com.serpies.talk2me.model.CreateMessageRequestDto;
import com.serpies.talk2me.utilities.FileUtils;
import com.serpies.talk2me.utilities.auth.AuthUtil;
import com.serpies.talk2me.utilities.auth.JwtUtil;
import com.serpies.talk2me.utilities.Assert;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


import java.net.URI;
import java.util.Date;
import java.util.Optional;

@Service
public class MessageService {

    private static final String NEW_MESSAGE_PATH = "/private/message/new";

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private FileUtils fileUtils;

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

    @Autowired
    private IFileDao fileDao;

    @Autowired
    private IFileMessageDao fileMessageDao;

    @Transactional
    public void createMessage(CreateMessageRequestDto createMessageRequestDto, String token){

        Assert.isNull(createMessageRequestDto, "No data received");
        Assert.isNull(token, "Token not received");
        Assert.isNull(createMessageRequestDto.getChatId(), "Chat id not received");
        Assert.ifCondition(
                createMessageRequestDto.getMessage() == null
                        && (
                                createMessageRequestDto.getImage() == null
                                || createMessageRequestDto.getFileExtension() == null
                        ),
                "You have to send a text or image message"
        );

        boolean isImage = createMessageRequestDto.getMessage() == null;

        Long userId = this.authUtil.validateAndGetUser(token);
        Optional<User> userOptional = this.userDao.findById(userId);

        Assert.ifCondition(userOptional.isEmpty(), new UserNotFoundException("The user must be exist"));

        Optional<Chat> chatOptional = this.chatDao.findByIdFechingUsers(createMessageRequestDto.getChatId());

        Assert.ifCondition(chatOptional.isEmpty(), new ChatNotFoundException("The chat must be exist"));
        Assert.ifCondition(!this.chatUserDao.userExistsInChat(userId, createMessageRequestDto.getChatId()), new UserNotInChatException("The user must be in the chat to send a message"));

        Chat chat = chatOptional.get();

        Message message = new Message();
        this.messageDao.save(message);

        String fileName = null;
        if (!isImage){

            TextMessage textMessage = new TextMessage();
            textMessage.setMessageId(message.getId());
            textMessage.setMessageData(createMessageRequestDto.getMessage());
            textMessage.setMessage(message);
            this.textMessageDao.save(textMessage);

        }else{

            Date now = new Date();
            Optional<URI> uriOptional = fileUtils.createFile(String.format("%d.%s", now.getTime(), createMessageRequestDto.getFileExtension()), createMessageRequestDto.getImage());

            Assert.ifCondition(uriOptional.isEmpty(), new InternalServerError("An error occurred while saving the image"));
            URI uri = uriOptional.get();

            File file = new File();
            file.setUri(uri.toString());
            fileDao.save(file);

            FileMessage fileMessage = new FileMessage();
            fileMessage.setMessageId(message.getId());
            fileMessage.setFile(file);
            fileMessage.setMessage(message);
            this.fileMessageDao.save(fileMessage);

            fileName = this.fileUtils.getName(uri);

        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessageId(message);
        chatMessage.setChatId(chat);
        this.chatMessageDao.save(chatMessage);

        MessageDto messageDto = new MessageDto();
        messageDto.setChatId(chat.getId());
        messageDto.setUserId(userId);
        messageDto.setCreatedAt(message.getCreatedAt());
        messageDto.setImportance(message.getImportance());

        if (isImage){
            messageDto.setImage(createMessageRequestDto.getImage());
            messageDto.setFileName(fileName);
        }else{
            messageDto.setMessage(createMessageRequestDto.getMessage());
        }

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
