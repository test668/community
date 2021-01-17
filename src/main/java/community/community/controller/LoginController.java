package community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author by wyc
 * @Date 2021/1/15.
 */
@Controller
public class LoginController {


    @GetMapping("/login")
    public String login(){
        return "/";
    }
}
