package ru.netology2.web;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;

public class ServerThread implements Runnable {


    private final Socket socket;
    private final BufferedReader in;
    private final BufferedOutputStream out;


    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.out = new BufferedOutputStream(this.socket.getOutputStream());

    }


    private void close() throws IOException {
        this.socket.close();
        this.in.close();
        this.out.close();
    }

    @Override
    public void run() {
        try {


            final Request request = RequestFactory.getRequest(in);
            Handler handler = null;
            if (request == null){

            }else{
                Map<String, Handler> inner = Server.getHandlers().get(request.getPath());

                if (inner == null){
                    handler = Server.getHandlers().get("publicFiles").get(request.getMETHOD());

                }else{
                    handler = inner.get(request.getMETHOD());
                }

                if (handler == null) {
                    out.write(Response.requestError(Response.badRequestError));
                    out.flush();
                }else{
                    handler.handle(request, out);
                }

            }


        } catch (IOException e) {
            e.getStackTrace();
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                this.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
