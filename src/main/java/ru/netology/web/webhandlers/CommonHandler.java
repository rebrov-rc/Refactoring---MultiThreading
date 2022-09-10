package ru.netology.web.webhandlers;

import ru.netology.web.WebFactory;
import ru.netology.web.WebRequest;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class CommonHandler implements WebHandler{

    public CommonHandler(){}

    @Override
    public void handle(WebRequest request, BufferedOutputStream out) {
        try {

            final var filePath = Path.of(".", "public", request.getPath());
            final var mimeType = Files.probeContentType(filePath);

            // special case for classic
            if (request.getPath().equals("/classic.html")) {
                final var template = Files.readString(filePath);
                final var content = template.replace(
                        "{time}",
                        LocalDateTime.now().toString()
                );

                out.write(WebFactory.response("200 OK", mimeType, Integer.toString( content.length() )));

                out.write(content.getBytes());
                out.flush();
            }else {

                final var length = Files.size(filePath);

                out.write(WebFactory.response("200 OK", mimeType, Long.toString(length)));

                Files.copy(filePath, out);
                out.flush();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
