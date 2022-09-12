package ru.netology.web;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class ServerThread implements Runnable{

    private final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png",
            "/resources.html", "/styles.css", "/app.js", "/links.html", "/forms.html",
            "/classic.html", "/events.html", "/events.js");

    private final Socket socket;


    public ServerThread(Socket socket){
        this.socket = socket;
    }

    private void close(BufferedReader in, BufferedOutputStream out) throws IOException {
        this.socket.close();
        in.close();
        out.close();
    }

    @Override
    public void run(){
        try {
            final var in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            final var out = new BufferedOutputStream(this.socket.getOutputStream());

            // read only request line for simplicity
            // must be in form GET /path HTTP/1.1
            final var requestLine = in.readLine();
            String [] parts = new String[0];
            try {
                parts = requestLine.split(" ");

            } catch (NullPointerException e){
                e.getStackTrace();
                this.close(in, out);
                throw new IOException();
            }

            if (parts.length != 3) {
                // just close socket
                this.close(in, out);
                throw new IOException();
            }

            String[] finalParts = parts;

            final var path = finalParts[1];
                if (!validPaths.contains(path)) {
                    out.write((
                            "HTTP/1.1 404 Not Found\r\n" +
                                    "Content-Length: 0\r\n" +
                                    "Connection: close\r\n" +
                                    "\r\n"
                    ).getBytes());
                    out.flush();
                }else {

                    final var filePath = Path.of(".", "public", path);
                    final var mimeType = Files.probeContentType(filePath);
                    // special case for classic
                    if (path.equals("/classic.html")) {
                        final var template = Files.readString(filePath);
                        final var content = template.replace(
                                "{time}",
                                LocalDateTime.now().toString()
                        ).getBytes();
                        out.write((
                                "HTTP/1.1 200 OK\r\n" +
                                        "Content-Type: " + mimeType + "\r\n" +
                                        "Content-Length: " + content.length + "\r\n" +
                                        "Connection: close\r\n" +
                                        "\r\n"
                        ).getBytes());
                        out.write(content);
                        out.flush();
                    } else {
                        final var length = Files.size(filePath);
                        out.write((
                                "HTTP/1.1 200 OK\r\n" +
                                        "Content-Type: " + mimeType + "\r\n" +
                                        "Content-Length: " + length + "\r\n" +
                                        "Connection: close\r\n" +
                                        "\r\n"
                        ).getBytes());
                        Files.copy(filePath, out);
                        out.flush();
                    }
                }

            this.close(in, out);


        }catch (IOException e){
            e.getStackTrace();
        }
    }
}
