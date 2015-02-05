
import java.net.URI;
import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class MyServer {

    static final String BASE_URI = "http://127.0.0.1:8081/";

    public static void main(String[] args) throws Exception {
        URI endpoint = new URI(BASE_URI);
        ResourceConfig rc = new ResourceConfig(MyRestApi.class);
        HttpServer server = JdkHttpServerFactory.createHttpServer(endpoint,rc);
		
        System.out.println("Starting the server ...");
    }
}
