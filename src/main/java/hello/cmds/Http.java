package hello.cmds;


import hello.interfaces.ICmd;
import hello.interfaces.IHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component("httpCmd")
public class Http implements ICmd{

    @Autowired
    IHttp http;

    @Override
    public void run(String[] args) {
        try {
            System.out.println(http.request("http://baidu.com"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
