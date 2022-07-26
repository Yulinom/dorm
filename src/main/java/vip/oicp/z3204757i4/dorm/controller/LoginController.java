package vip.oicp.z3204757i4.dorm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String longin( String user, String password,HttpSession session) {
        session.setAttribute("token", UUID.randomUUID());
        if (user.equals("admin") && password.equals("admin")) {
            return "index";
        } else return "login";

    }

    @RequestMapping("/ini")
    public String index() {
        return "login";
    }
}
