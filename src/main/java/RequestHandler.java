import model.HttpRequestMethod;
import model.HttpRequestUtils;
import model.IOUtils;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RequestHandler extends Thread{
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connect;

    public RequestHandler(final Socket connect) {
        this.connect = connect;
    }

    public void run() {
        log.debug("사용자가 연결되었습니다. Connected IP : {}, Port : {}", connect.getInetAddress(), connect.getPort());

        try(InputStream in = connect.getInputStream();
            OutputStream out = connect.getOutputStream()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            log.info("request Line : {}", line);

            if(line == null) {
                return;
            }

            String[] tokens = line.split(" ");

            int contentLength = 0;
            while(!line.equals("")) {
                log.debug("header : {}", line);
                line = br.readLine();
                if(line.contains("Content-Length")) {
                    contentLength = getContentLength(line);
                }
            }

            String method = tokens[0];
            String url = tokens[1];
            DataOutputStream dos = new DataOutputStream(out);

            //POST
            if(HttpRequestMethod.POST.equals(method) && url.equals("/users/create")) {
                String requestBody = IOUtils.readData(br, contentLength);
                Map<String, String> params = HttpRequestUtils.parseQueryString(requestBody);

                User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
                String response = user.toString();

                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                response200Header(dos, responseBytes.length);
                responseBody(dos, responseBytes);
            }

            //GET
            else if(HttpRequestMethod.GET.equals(method) && url.startsWith("/users")) {
                int index = url.indexOf("?");
                String query = url.substring(index + 1);

                Map<String, String> params = HttpRequestUtils.parseQueryString(query);

                int wannaSize = Integer.parseInt(params.get("size"));
                String response = wannaSize + "만큼의 유저 정보를 읽기 원합니다.";

                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                response200Header(dos, responseBytes.length);
                responseBody(dos, responseBytes);
            }

            else {
                byte[] body = "안녕하세요!".getBytes(StandardCharsets.UTF_8);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private int getContentLength(final String line) {
        String[] headerTokens = line.split(":");
        return Integer.parseInt(headerTokens[1].trim());
    }

    private void responseBody(final DataOutputStream dos, final byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(final DataOutputStream dos, final int length) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK\r\n");
            dos.writeBytes("Content-Type: text/html; charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
