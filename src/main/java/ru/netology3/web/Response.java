package ru.netology3.web;

public class Response {

    public static final String BAD_REQUEST_ERROR = "400 Bad Request";
    public static final String NOT_FOUND_ERROR = "404 Not Found";
    public static final String OK = "200 OK";

    private Response(){}

    public static byte[] response (String reqLine, String mimeType, String contentLength){
        String response = "HTTP/1.1 " + reqLine + "\r\n" +
                "Content-Type: " + mimeType + "\r\n" +
                "Content-Length: " + contentLength + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";

        return response.getBytes();
    }

    public static byte [] requestError(String err){
        return (
                "HTTP/1.1 " + err + "\r\n" +
                        "Content-Length: 0\r\n" +
                        "Connection: close\r\n" +
                        "\r\n"
        ).getBytes();
    }
}
