package com.buaa.ops.Controller;

import com.buaa.ops.Entity.Account;
import com.buaa.ops.Entity.Author;
import com.buaa.ops.Service.AccountService;
import com.buaa.ops.Service.AuthorService;
import com.buaa.ops.Service.Exc.IllegalAuthorityException;
import com.buaa.ops.Service.Exc.LoginVerificationException;
import com.buaa.ops.Service.Exc.ParameterFormatException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthorController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorService authorService;

    @PostMapping("/author/certify")
    public Map<String, Object> certify(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> map = new HashMap<>();
        try {
/*
            String email;
            String password;
            Account account;
            try {
                HttpSession session = request.getSession();
                email = (String) session.getAttribute("email");
                password = (String) session.getAttribute("password");
                account = accountService.getAccount(email);
                if (!password.equals(account.getPassword()))
                    throw new Exception();
            }
            catch (Exception e) {
                throw new LoginVerificationException();
            }
*/
            Account account = accountService.getAccountBySession(request.getSession());
            String institution;
            String researchInterests;
            try {
                institution = (String) requestMap.get("institution");
                researchInterests = (String) requestMap.get("researchInterests");
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            Integer accountId = account.getAccountId();
            if (authorService.getAuthorByAccountId(accountId) != null)
                throw new RepetitiveOperationException();
            Author author = new Author(accountId, institution, researchInterests);
            authorService.addAuthor(author);
            map.put("success", true);
        }
        catch (LoginVerificationException | ParameterFormatException | RepetitiveOperationException exc) {
            map.put("success", false);
            map.put("message", exc.toString());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            map.put("success", false);
            map.put("message", "操作失败");
        }
        return map;
    }

    @PostMapping("/cancel")
    public Map<String, Object> cancel() {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(request.getSession());
            Integer accountId = account.getAccountId();
            Author author = authorService.getAuthorByAccountId(accountId);
            if (author == null)
                throw new IllegalAuthorityException();
            authorService.removeAuthor(author.getAuthorId());
            map.put("success", true);
        }
        catch (LoginVerificationException | IllegalAuthorityException exc) {
            map.put("success", false);
            map.put("message", exc.toString());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            map.put("success", false);
            map.put("message", "操作失败");
        }
        return map;
    }

}
