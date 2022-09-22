package ru.netology3.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {


    private final int threadCount = 64;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
    private static Map<String, Map<String, Handler>> handlers = new ConcurrentHashMap<>();

    public static Map<String, Map<String, Handler>> getHandlers(){return handlers;}


    public void addHandler(String method, String path, Handler handler){

        Map<String, Handler> inner = new ConcurrentHashMap<>();
        inner.putIfAbsent(method, handler);
        handlers.putIfAbsent(path, inner);

    }

    public void listen(final int PORT){

        try (final var serverSocket = new ServerSocket(PORT);) {

            while (true) {
                final Socket socket = serverSocket.accept();
                ServerThread thread = new ServerThread(socket);
                threadPool.submit(thread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
