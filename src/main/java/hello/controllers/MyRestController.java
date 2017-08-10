package hello.controllers;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/rest")
public class MyRestController {

    @RequestMapping("/ping/{info}")
    public String Ping(@PathVariable("info") String info) {
        return info;
    }

    @RequestMapping("/pong/{info}")
    public String Pong(@PathVariable String info) { // 变量名称一致时，可以不写
        return info;
    }



}
