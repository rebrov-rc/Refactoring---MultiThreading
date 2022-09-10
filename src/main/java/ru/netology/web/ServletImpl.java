package ru.netology.web;

import ru.netology.web.webhandlers.CommonHandler;
import ru.netology.web.webhandlers.WebHandler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ServletImpl implements Servlet{

    private Map<String, RequestHandlerContainer> reqHandlersStore = new HashMap<>();

    private final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png",
            "/resources.html", "/styles.css", "/app.js", "/links.html", "/forms.html",
            "/classic.html", "/events.html", "/events.js");


    @Override
    public void listen(int webPort){

        final ExecutorService threadPool = Executors.newFixedThreadPool(64);


        try (final var serverSocket = new ServerSocket(webPort)) {
            while (true) {

                System.out.println(Thread.currentThread().getName());

                try {
                    final Socket socket = serverSocket.accept();
                    final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    final WebRequest request = WebRequestFactory.getRequest(in);
                    final var out = new BufferedOutputStream(socket.getOutputStream());

                    if (request != null && validPaths.contains(request.getPath())) {

                        threadPool.submit(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(Thread.currentThread().getName());
                                new CommonHandler().handle(request, out);
                                try {
                                    socket.close();
                                    in.close();
                                    out.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }else {
                        out.write(WebFactory.response("404 Not Found", "", "0"));
                        out.flush();
                        out.close();
                        socket.close();
                        in.close();
                    }

                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void addHandler(String reqMethod, String path, WebHandler handler) {

    }
}
