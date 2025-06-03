package com.serpies.talk2me.service;

import com.serpies.talk2me.db.dao.IFileDao;
import com.serpies.talk2me.db.entity.File;
import com.serpies.talk2me.exceptions.FileNotFoundException;
import com.serpies.talk2me.utilities.Assert;
import com.serpies.talk2me.utilities.FileUtils;
import com.serpies.talk2me.utilities.auth.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private IFileDao fileDao;

    @Autowired
    private FileUtils fileUtils;

    public ResponseEntity<byte[]> getFileData(String authorizationHeaderValue, Long messageId){

        Assert.isNull(messageId, "messageId cannot be null");

        String tokenParesed = this.authUtil.getTokenFromAuthorization(authorizationHeaderValue);
        Long userId = this.authUtil.validateAndGetUser(tokenParesed);

        Optional<File> fileOptional = this.fileDao.getFileByMessageIdAndUserId(userId, messageId);

        Assert.ifCondition(fileOptional.isEmpty(), new FileNotFoundException("file not found"));

        File file = fileOptional.get();
        URI uri = URI.create(file.getUri());
        byte[] data = this.fileUtils.readFile(uri);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename\"".concat(this.fileUtils.getName(uri)).concat("\""))
                .body(data);
    }

}
