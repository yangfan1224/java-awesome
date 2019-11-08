package github.com.yangfan1224.awesome.webservice;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class WebServiceClient {

    public static void main(String [] args) {
        String url = "http://foxth.com:8091";
        Response response = ClientBuilder.newClient()
                .target(url)
                .path("/v1/keepalive/provisioning")
                .request().get();

        System.out.println(response.getStatusInfo());
    }
}
