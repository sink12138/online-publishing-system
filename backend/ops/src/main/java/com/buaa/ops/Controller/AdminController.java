package com.buaa.ops.Controller;

import com.buaa.ops.Service.Exc.LoginVerificationException;
import com.buaa.ops.Service.Exc.ParameterFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Value("${admin.username}")
    private String USERNAME;

    @Value("${admin.password}")
    private String PASSWORD;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            String username;
            String password;
            try {
                username = (String) request.get("username");
                password = (String) request.get("password");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            if (!username.equals(USERNAME) || !password.equals(PASSWORD)) {
                throw new LoginVerificationException();
            }
            map.put("success", true);
            session.setAttribute("username", username);
            session.setAttribute("password", password);
        } catch (ParameterFormatException | LoginVerificationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        }
        return map;
    }
}
