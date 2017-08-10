package hello.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SessionController {

    @RequestMapping("/set")
    public String setSession(HttpServletRequest request) {
        request.getSession().setAttribute("name", "phpor");
        return "ok";
    }

    @RequestMapping("/get")
    public String getSession(HttpServletRequest request) {

        return (String)request.getSession().getAttribute("name");
    }
}
