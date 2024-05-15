import model.HttpRequestMethod;
import model.User;
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
            String method = request.getMethod();

            if("/users/create".equals(path) && HttpRequestMethod.POST.equals(method)) {
                User user = new User(request.getParameter("userId"),
                        request.getParameter("password"),
                        request.getParameter("name"),
                        request.getParameter("email"));
                String result = user.toString();

                response.sendResponse(result);
            }

            else if(path.startsWith("/users") && HttpRequestMethod.GET.equals(method)) {
                int wannaSize = Integer.parseInt(request.getParameter("size"));
                String result = wannaSize + "만큼의 유저 정보를 읽기 원합니다.";

                response.sendResponse(result);
            }

            else {
                response.sendResponse("안녕하세요!");
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
