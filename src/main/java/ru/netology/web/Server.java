package ru.netology.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {


    private final int threadCount = 64;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);


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
