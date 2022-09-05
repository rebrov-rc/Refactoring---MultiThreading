package ru.netology.web;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WebResponsive {

    private WebResponsive(){}

    public static Servlet getServlet(){
        return new ServletImpl();
    }

    public static byte[] response (String reqLine, String mimeType,String contentLength){
        String response = "HTTP/1.1 " + reqLine + "\r\n" +
                "Content-Type: " + mimeType + "\r\n" +
                "Content-Length: " + contentLength + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";

        return response.getBytes();
    }


    public static void responseWithFile (String path, BufferedOutputStream out) {
        try {
            final var filePath = Path.of(".", "public", path);
            final var mimeType = Files.probeContentType(filePath);
            final var length = Files.size(filePath);

            out.write(response("200 OK", mimeType, Long.toString(length)));

            Files.copy(filePath, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
