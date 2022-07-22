package vip.oicp.z3204757i4.dorm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class RouterController {
    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/")
    public String index(HttpSession session) {
        session.setAttribute("token", UUID.randomUUID());
        return "index";
    }

    @RequestMapping("/student-list")
    public String studentList() {
        return "student-list";
    }
}
