import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.request.HttpRequest;
import view.response.HttpResponse;

import java.io.*;
import java.net.Socket;

public class RequestHandler extends Thread{
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connect;

    public RequestHandler(final Socket connect) {
        this.connect = connect;
    }

    public void run() {
        try(InputStream in = connect.getInputStream();
            OutputStream out = connect.getOutputStream()) {

            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);

            String path = request.getPath();
            log.info(path);
            Controller controller = RequestMapping.getController(path);
            if(controller == null) {
                response.sendResponse("안녕하세요!");
            }
            else {
                controller.service(request, response);
            }


        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
