package hello.beans;


import hello.interfaces.IHttp;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class HttpClientImpl implements IHttp {

    @Override
    public String request(String url) throws IOException, URISyntaxException {
        HttpGet httpGet = new HttpGet(new URI(url));
        CloseableHttpClient hc = HttpClients.createDefault();
        HttpResponse result = hc.execute(httpGet);
        return EntityUtils.toString(result.getEntity());
    }
}
