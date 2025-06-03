package com.serpies.talk2me.service;

import com.serpies.talk2me.utilities.Properties;
import com.serpies.talk2me.db.dao.IAuthTokenDao;
import com.serpies.talk2me.db.dao.IUserDao;
import com.serpies.talk2me.db.dto.AuthTokenDto;
import com.serpies.talk2me.db.dto.UserDto;
import com.serpies.talk2me.db.entity.AuthToken;
import com.serpies.talk2me.db.entity.User;
import com.serpies.talk2me.db.enums.Gender;
import com.serpies.talk2me.utilities.Assert;
import com.serpies.talk2me.utilities.auth.AuthUtil;
import com.serpies.talk2me.exceptions.EmailAlreadyExistsException;
import com.serpies.talk2me.exceptions.IncorrectPasswordOfUserException;
import com.serpies.talk2me.exceptions.TimeOutLoginException;
import com.serpies.talk2me.exceptions.UserNotFoundException;
import com.serpies.talk2me.utilities.auth.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private Properties properties;

    @Autowired
    private IAuthTokenDao authTokenDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthUtil authUtil;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthTokenDto login(UserDto userDto){

        String email = userDto.getEmail();
        String password = userDto.getPassword();

        Assert.isNull(email, "email cannot be null");
        Assert.isNull(password, "password cannot be null");

        Optional<User> optionalUser = this.userDao.findByEmail(email);

        Assert.ifCondition(optionalUser.isEmpty(), new UserNotFoundException("There is not user with this email address"));

        User user = optionalUser.get();
        String passwordUserDb = user.getPassword();

        Date now = new Date();
        Date expiration = new Date(now.getTime() + properties.getTokenExpirationTime() * 1000);

        Optional<AuthToken> optionalAuthToken = this.authTokenDao.findById(user.getId());

        AuthToken authToken = optionalAuthToken.orElseGet(() -> {
            AuthToken newAuthToken = new AuthToken();
            newAuthToken.setUserId(user.getId());
            newAuthToken.setCreatedAt(now);
            newAuthToken.setExpiresAt(expiration);
            newAuthToken.setToken(this.jwtUtil.generateToken(user.getId(), user.getEmail(), expiration));

            return this.authTokenDao.save(newAuthToken);
        });

        long secondsTimeOut = (authToken.getUnsuccessfulAttempts() / properties.getTokenAttemptsBeforeTimeOut()) * properties.getTokenTimeOut();
        Instant timeOut = authToken.getLastAttempt().toInstant().plusSeconds(secondsTimeOut);

        Assert.ifCondition(authUtil.hasTimeOut(authToken), new TimeOutLoginException("Many unsuccessful attempts have been made", Date.from(timeOut)));

        if (!encoder.matches(password, passwordUserDb)){

            authToken.setUnsuccessfulAttempts( (short) (authToken.getUnsuccessfulAttempts().intValue() + 1));
            authToken.setLastAttempt(now);

            this.authTokenDao.save(authToken);

            throw new IncorrectPasswordOfUserException("Passwords are not equals");

        }

        if (authToken.getExpiresAt().before(now)){

            authToken.setCreatedAt(now);
            authToken.setExpiresAt(expiration);
            authToken.setLastAttempt(now);
            authToken.setToken(this.jwtUtil.generateToken(user.getId(), user.getEmail(), expiration));

            authToken = this.authTokenDao.save(authToken);

        }

        return new AuthTokenDto(
                authToken.getUserId(),
                authToken.getToken(),
                authToken.getExpiresAt()
        );

    }

    @Transactional
    public AuthTokenDto signUp(UserDto userDto){

        String email = userDto.getEmail();
        String password = userDto.getPassword();
        String name = userDto.getName();
        String surname = userDto.getSurname();
        Gender gender = userDto.getGender();

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
        user.setPassword(encoder.encode(password));

        user = this.userDao.save(user);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + properties.getTokenExpirationTime() * 1000);

        AuthToken authToken = new AuthToken();
        authToken.setUserId(user.getId());
        authToken.setCreatedAt(now);
        authToken.setExpiresAt(expiration);
        authToken.setToken(this.jwtUtil.generateToken(user.getId(), user.getEmail(), expiration));

        authToken = this.authTokenDao.save(authToken);

        return new AuthTokenDto(
                authToken.getUserId(),
                authToken.getToken(),
                authToken.getExpiresAt()
        );

    }

}
