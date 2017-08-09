package hello.interfaces;


import java.io.IOException;
import java.net.URISyntaxException;

public interface IHttp {
    String request(String url) throws IOException, URISyntaxException;
}
