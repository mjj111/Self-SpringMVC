import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private static final int DEFAULT_PORT = 8080;
    public static void main(String[] args) throws IOException {
        int port;

        if(args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        try(ServerSocket listenSocket = new ServerSocket(port)) {
            Socket connection;
            while((connection = listenSocket.accept()) != null) {
                RequestHandler requestHandler = new RequestHandler(connection);
                requestHandler.start();
            };
        }
    }
}
