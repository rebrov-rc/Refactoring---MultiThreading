package ru.netology.web;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ServletImpl implements Servlet{

    private Map<String, RequestHandlerContainer> reqHandlersStore = new HashMap<>();


    @Override
    public void listen(int port){
        final ExecutorService threadPool = Executors.newFixedThreadPool(64);


        try (final var serverSocket = new ServerSocket(port)) {
            System.out.println("server: " + serverSocket.getLocalSocketAddress());

            while (true) {
                try {

                    final var socket = serverSocket.accept();
                    final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    final var out = new BufferedOutputStream(socket.getOutputStream());



                    // ============================================================

                    // read only request line for simplicity
                    // must be in form GET /path HTTP/1.1
                    final var requestLine = in.readLine();
                    Request request = null;

                    String [] parts = null;
                    try{
                        parts = requestLine.split(" ");
                        request = new Request(parts[0], parts[1]);

                    }catch(NullPointerException ex){
                        continue;
                    }

                    if (parts.length != 3) {
                        // just close socket
                        continue;
                    }

                    final var path = parts[1];
                    if (!reqHandlersStore.containsKey(path)) {
                        out.write(WebResponsive.response("404 Not Found", "", "0"));
                        out.flush();
                        continue;
                    }
                    // ============================================================

                    final Request finalRequest = request;
                    WebHandler handler = reqHandlersStore.get(finalRequest.getQUERY()).getHandler(finalRequest.getMETHOD());

                    threadPool.submit(new Runnable() {
                        @Override
                        public void run() {
                            handler.handle(finalRequest, out);
                            try {
                                socket.close();
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                    // ============================================================


                } catch (IOException exception) {
                    exception.printStackTrace();
                } catch (NullPointerException exception){
                    exception.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void addHandler(String reqMethod, String path, WebHandler handler) {
        if (this.reqHandlersStore.containsKey(path)) {
            this.reqHandlersStore.get(path).add(reqMethod, handler);
        }else{

            this.reqHandlersStore.put(path, new RequestHandlerContainer() {
                private final Map<String, WebHandler> handlerList = Map.of(reqMethod, handler);

                @Override
                public void add(String method, WebHandler handler) {
                    handlerList.put(method, handler);
                }

                @Override
                public WebHandler getHandler(String method) {
                    return handlerList.get(method);
                }
            });
        }
    }

}