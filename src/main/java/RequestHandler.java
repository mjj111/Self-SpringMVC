import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler extends Thread{
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connect;

    public RequestHandler(Socket connect) {
        this.connect = connect;
    }

    public void run() {
        log.debug("사용자가 연결되었습니다. Connected IP : {}, Port : {}", connect.getInetAddress(), connect.getPort());

        try(InputStream in = connect.getInputStream();
            OutputStream out = connect.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "안녕하세요!".getBytes(StandardCharsets.UTF_8);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }


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
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type : text/html; charset=utf-8\r\n");
            dos.writeBytes("Content-Length" + length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
