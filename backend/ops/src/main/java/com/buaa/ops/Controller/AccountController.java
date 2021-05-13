package com.buaa.ops.Controller;

import com.buaa.ops.Service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    private CheckService checkService;
}
