package ru.netology.web;

import java.io.BufferedReader;
import java.io.IOException;

public class WebFactory {

    private WebFactory(){}

    public static Servlet getServlet(){
        return new ServletImpl();
    }


    public static WebRequest getRequest(BufferedReader in){

        final WebRequest request = null;

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return request;
    }


    public static byte[] response (String reqLine, String mimeType,String contentLength){
        String response = "HTTP/1.1 " + reqLine + "\r\n" +
                "Content-Type: " + mimeType + "\r\n" +
                "Content-Length: " + contentLength + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";

        return response.getBytes();
    }
}
