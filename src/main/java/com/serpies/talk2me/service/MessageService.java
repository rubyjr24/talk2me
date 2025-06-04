package com.serpies.talk2me.service;

import com.serpies.talk2me.db.dao.*;
import com.serpies.talk2me.db.dto.MessageDto;
import com.serpies.talk2me.db.entity.*;
import com.serpies.talk2me.exceptions.*;
import com.serpies.talk2me.utilities.FileUtils;
import com.serpies.talk2me.utilities.Properties;
import com.serpies.talk2me.utilities.auth.AuthUtil;
import com.serpies.talk2me.utilities.auth.JwtUtil;
import com.serpies.talk2me.utilities.Assert;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class MessageService {

    private static final String NEW_MESSAGE_PATH = "/private/message/new";

    @Autowired
    private Properties properties;

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
    private ITextMessageDao textMessageDao;

    @Autowired
    private IFileDao fileDao;

    @Autowired
    private IFileMessageDao fileMessageDao;

    @Transactional
    public MessageDto createMessage(MessageDto messageDto, String authorizationHeaderValue){

        Assert.isNull(messageDto, "No data received");
        Assert.isNull(authorizationHeaderValue, "Token not received");
        Assert.isNull(messageDto.getChatId(), "Chat id not received");
        Assert.ifCondition(
                messageDto.getMessage() == null
                        && (
                            messageDto.getImage() == null
                            || messageDto.getFileExtension() == null
                        ),
                "You have to send a text or image message"
        );

        boolean isImage = messageDto.getMessage() == null;

        String tokenParesed = this.authUtil.getTokenFromAuthorization(authorizationHeaderValue);
        Long userId = this.authUtil.validateAndGetUser(tokenParesed);
        Optional<User> userOptional = this.userDao.findById(userId);

        Assert.ifCondition(userOptional.isEmpty(), new UserNotFoundException("The user must be exist"));

        Optional<Chat> chatOptional = this.chatDao.findByIdFechingUsers(messageDto.getChatId());

        Assert.ifCondition(chatOptional.isEmpty(), new ChatNotFoundException("The chat must be exist"));

        Optional<ChatUser> chatUserOptional = this.chatUserDao.getChatUserByUserIdAndChatId(userId, messageDto.getChatId());
        Assert.ifCondition(chatUserOptional.isEmpty(), new UserNotInChatException("The user must be in the chat to send a message"));

        Chat chat = chatOptional.get();
        ChatUser chatUser = chatUserOptional.get();

        Message message = new Message();
        message.setChatUserId(chatUser.getId());
        this.messageDao.save(message);

        String fileName = null;
        if (!isImage){

            TextMessage textMessage = new TextMessage();
            textMessage.setMessageId(message.getId());
            textMessage.setMessageData(messageDto.getMessage());
            textMessage.setMessage(message);
            this.textMessageDao.save(textMessage);

        }else{

            Date now = new Date();
            Optional<URI> uriOptional = fileUtils.createFile(String.format("%d.%s", now.getTime(), messageDto.getFileExtension()), messageDto.getImage());

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


        MessageDto messageDtoResult = new MessageDto();
        messageDtoResult.setMessageId(message.getId());
        messageDtoResult.setChatId(chat.getId());
        messageDtoResult.setUserId(userId);
        messageDtoResult.setCreatedAt(message.getCreatedAt());
        messageDtoResult.setImportance(message.getImportance());

        if (isImage){
            messageDtoResult.setImage(messageDto.getImage());
            messageDtoResult.setFileName(fileName);
        }else{
            messageDtoResult.setMessage(messageDto.getMessage());
        }

        for (ChatUser chatUserToSend: chat.getChatUserList()){

            User userToSend = chatUserToSend.getUser();

            //if (userToSend.getId() == userId) continue;

            this.messagingTemplate.convertAndSendToUser(
                    userToSend.getEmail(),
                    NEW_MESSAGE_PATH,
                    messageDtoResult
            );

        }

        return messageDtoResult;

    }

    public List<MessageDto> getAllMessagesByUser(String authorizationHeaderValue){

        Assert.isNull(authorizationHeaderValue, new NotValidTokenException("You must have a token"));

        String tokenParesed = this.authUtil.getTokenFromAuthorization(authorizationHeaderValue);
        Long userId = this.authUtil.validateAndGetUser(tokenParesed);

        List<Chat> chats = this.chatDao.findByUserId(userId);

        List<MessageDto> result = new ArrayList<>();
        for (Chat chat : chats){

            List<Map<String, Object>> messages = this.messageDao.findAllByChatIdWithLimit(chat.getId(), this.properties.getMessageLimit()); // cambiar para obtenerlo del properties

            for (Map<String, Object> row : messages) {

                MessageDto messageDto = new MessageDto();
                messageDto.setMessageId(((Number) row.get("id")).longValue());
                messageDto.setChatId(((Number) row.get("chatId")).longValue());
                messageDto.setUserId(((Number) row.get("userId")).longValue());
                messageDto.setCreatedAt((Date) row.get("createdAt"));
                messageDto.setImportance(((Number) row.get("importance")).shortValue());

                Object message = row.get("message");
                Object uriObj = row.get("uri");

                if (message != null) messageDto.setMessage((String) message);
                else if (uriObj != null) messageDto.setUri((String) uriObj);

                result.add(messageDto);

            }

        }

        return result;

    }

}
