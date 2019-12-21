package ntu.csie.selab.inventorysystem.service;

import ntu.csie.selab.inventorysystem.exception.NotFoundException;
import ntu.csie.selab.inventorysystem.exception.UnauthorizedException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntu.csie.selab.inventorysystem.model.Token;
import ntu.csie.selab.inventorysystem.model.User;
import ntu.csie.selab.inventorysystem.repository.TokenRepository;
import ntu.csie.selab.inventorysystem.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;

    public User authenticate(String username, String password) {
        List<User> list = userRepository.findByUsernameAndPassword(username, password);
        if (list.isEmpty())
            throw new NotFoundException("Username or password not matched.");
        return list.get(0);
    }

    public void expireToken(Token token) {
        if (token.getExpire().compareTo(new Date()) > 0) {
            token.setExpire(new Date());
            tokenRepository.save(token);
        }
    }

    public Token assignNewToken(User user) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString().replace("-", ""));
        token.setExpire(DateUtils.addDays(new Date(), 1));
        return tokenRepository.save(token);
    }

    public void isLogin(Integer uid, String token) {
        User user = new User();
        user.setId(uid);
        List<Token> list = tokenRepository.CheckAvailableToken(user, token);
        if (list.isEmpty())
            throw new UnauthorizedException("User not logged in.");
    }
}
