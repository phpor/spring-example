package hello.controllers;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public String setSession(Exception ex) {
        return ex.toString();
    }

    @RequestMapping("/make/error/{msg}")
    public String getSession(@PathVariable String msg) {
        if (!msg.equals("ok")) throw new RuntimeException(msg);
        return msg;
    }
}
