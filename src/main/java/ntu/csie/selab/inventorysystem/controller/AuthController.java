package ntu.csie.selab.inventorysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ntu.csie.selab.inventorysystem.exception.NotFoundException;
import ntu.csie.selab.inventorysystem.model.Token;
import ntu.csie.selab.inventorysystem.model.User;
import ntu.csie.selab.inventorysystem.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/login", produces = "application/json")
    public Map<String, Object> login(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        // Login Process
        User user = userService.authenticate(
                (String) map.getOrDefault("username", ""),
                (String) map.getOrDefault("password", "")
        );
        for (Token token : user.getTokens())
            userService.expireToken(token);
        Token token = userService.assignNewToken(user);

        // Set Cookie
        Cookie cookie;
        cookie = new Cookie("uid", token.getUser().getId().toString());
        cookie.setPath("/");
        response.addCookie(cookie);
        cookie = new Cookie("token", token.getToken());
        cookie.setPath("/");
        response.addCookie(cookie);

        // Response Body
        Map<String, Object> body = new HashMap<>();
        body.put("privilege", token.getUser().getPrivilege());
        return body;
    }
}
