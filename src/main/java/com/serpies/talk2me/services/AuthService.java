package com.serpies.talk2me.services;

import com.serpies.talk2me.config.Config;
import com.serpies.talk2me.db.dtos.AuthTokenDto;
import com.serpies.talk2me.db.entities.AuthToken;
import com.serpies.talk2me.db.daos.IAuthTokenDao;
import com.serpies.talk2me.db.daos.IUserDao;
import com.serpies.talk2me.db.entities.User;
import com.serpies.talk2me.db.enums.Gender;
import com.serpies.talk2me.models.LoginRequestDto;
import com.serpies.talk2me.models.SignUpRequestDto;
import com.serpies.talk2me.utilities.Assert;
import com.serpies.talk2me.utilities.exceptions.EmailAlreadyExistsException;
import com.serpies.talk2me.utilities.exceptions.IncorrectPasswordOfUserException;
import com.serpies.talk2me.utilities.exceptions.UserNotFoundException;
import com.serpies.talk2me.utilities.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private Config config;

    @Autowired
    private IAuthTokenDao authTokenDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthTokenDto login(LoginRequestDto loginRequestDto){

        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        Assert.isNull(email, "email cannot be null");
        Assert.isNull(password, "password cannot be null");

        Optional<User> optionalUser = this.userDao.findByEmail(email);

        Assert.ifCondition(optionalUser.isEmpty(), new UserNotFoundException("There is not user with this email address"));

        User user = optionalUser.get();
        String passwordUserDb = user.getPassword();

        Assert.ifCondition(!password.equals(passwordUserDb), new IncorrectPasswordOfUserException("Passwords are not equals"));

        Optional<AuthToken> optionalAuthToken = this.authTokenDao.findById(user.getId());

        Date now = new Date();
        Date expiration = new Date(now.getTime() + config.getTokenExpirationTime());

        if (optionalAuthToken.isPresent()){

            AuthToken authTokenDb = optionalAuthToken.get();
            authTokenDb.setCreatedAt(new Date());
            authTokenDb.setExpiresAt(expiration);
            authTokenDb.setToken(this.jwtUtil.generateToken(user.getId(), user.getEmail(), expiration));

            authTokenDb = this.authTokenDao.save(authTokenDb);
            return new AuthTokenDto(authTokenDb.getToken(), authTokenDb.getExpiresAt());

        }

        AuthToken authToken = new AuthToken();
        authToken.setUserId(user.getId());
        authToken.setCreatedAt(now);
        authToken.setExpiresAt(expiration);
        authToken.setToken(this.jwtUtil.generateToken(user.getId(), user.getEmail(), expiration));

        authToken = this.authTokenDao.save(authToken);

        return new AuthTokenDto(authToken.getToken(), authToken.getExpiresAt());

    }

    @Transactional
    public AuthTokenDto signUp(SignUpRequestDto signUpRequestDto){

        String email = signUpRequestDto.getEmail();
        String password = signUpRequestDto.getPassword();
        String name = signUpRequestDto.getName();
        String surname = signUpRequestDto.getSurname();
        Gender gender = signUpRequestDto.getGender();

        Assert.isNull(email, "email cannot be null");
        Assert.isNull(password, "password cannot be null");
        Assert.isNull(name, "name cannot be null");
        Assert.isNull(gender, "gender cannot be null");

        Boolean userExists = this.userDao.existsByEmail(email);

        Assert.ifCondition(userExists, new EmailAlreadyExistsException("There is an user with this email"));

        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setGender(gender);
        user.setEmail(email);
        user.setPassword(password);

        user = this.userDao.save(user);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + config.getTokenExpirationTime());

        AuthToken authToken = new AuthToken();
        authToken.setUserId(user.getId());
        authToken.setCreatedAt(now);
        authToken.setExpiresAt(expiration);
        authToken.setToken(this.jwtUtil.generateToken(user.getId(), user.getEmail(), expiration));

        authToken = this.authTokenDao.save(authToken);

        return new AuthTokenDto(authToken.getToken(), authToken.getExpiresAt());

    }

}
