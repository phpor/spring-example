package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GreetingController {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {

        model.addAttribute("name", name);
        return "Hello, World!";
    }

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
