package ru.netology3;

import ru.netology3.web.Handler;
import ru.netology3.web.Request;
import ru.netology3.web.Response;
import ru.netology3.web.Server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;


public class Main {

    private static final int webPort = 9999;
    private static final String GET = "GET";
    private static final String POST = "POST";

    public static void main(String[] args) {
        Server server = new Server();

        server.addHandler(GET, "/classic.html", new Handler() {

            @Override
            public synchronized void handle(Request request, BufferedOutputStream out) {
                try {

                    final var filePath = Path.of(".", "public", request.getPath());
                    final var mimeType = Files.probeContentType(filePath);
                    final var template = Files.readString(filePath);
                    final var content = template.replace(
                            "{time}",
                            LocalDateTime.now().toString()
                    ).getBytes();
                    out.write(Response.response(Response.OK, mimeType, String.valueOf(content.length)));
                    out.write(content);

                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        server.addHandler(GET, "publicFiles", new Handler() {
            @Override
            public synchronized void handle(Request request, BufferedOutputStream out) {
                try {

                    final var filePath = Path.of(".", "public", request.getPath());

                    if (Files.exists(filePath)) {
                        final var length = Files.size(filePath);
                        final var mimeType = Files.probeContentType(filePath);
                        out.write(Response.response(Response.OK, mimeType, String.valueOf(length)));

                        Files.copy(filePath, out);
                    } else {
                        out.write(Response.requestError(Response.NOT_FOUND_ERROR));
                    }

                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        server.addHandler(GET, "/query", new Handler() {

            @Override
            public void handle(Request request, BufferedOutputStream out) {
                System.out.println(request.getQueryParams().toString());
            }
        });

        server.listen(webPort);
    }
}


