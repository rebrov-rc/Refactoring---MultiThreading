package ru.netology;


import ru.netology.web.Request;
import ru.netology.web.Servlet;
import ru.netology.web.WebResponsive;
import ru.netology.web.WebHandler;

import java.io.BufferedOutputStream;

public class Main {

  private static int webPort = 9999;

  public static void main(String[] args) {
    Servlet servlet = WebResponsive.getServlet();

    servlet.addHandler("GET", "/index.html", new WebHandler() {
      @Override
      public void handle(Request request, BufferedOutputStream responseStream) {
        WebResponsive.responseWithFile(request.getQUERY(),responseStream);
      }
    });

    servlet.addHandler("GET", "/spring.svg", new WebHandler() {
      @Override
      public void handle(Request request, BufferedOutputStream responseStream) {
        WebResponsive.responseWithFile(request.getQUERY(),responseStream);
      }
    });

    servlet.addHandler("GET", "/spring.png", new WebHandler() {
      @Override
      public void handle(Request request, BufferedOutputStream responseStream) {
        WebResponsive.responseWithFile(request.getQUERY(),responseStream);
      }
    });

    servlet.addHandler("GET", "/resources.html", new WebHandler() {
      @Override
      public void handle(Request request, BufferedOutputStream responseStream) {
        WebResponsive.responseWithFile(request.getQUERY(),responseStream);
      }
    });

    servlet.addHandler("GET", "/styles.css", new WebHandler() {
      @Override
      public void handle(Request request, BufferedOutputStream responseStream) {
        WebResponsive.responseWithFile(request.getQUERY(),responseStream);
      }
    });

    servlet.addHandler("GET", "/app.js", new WebHandler() {
      @Override
      public void handle(Request request, BufferedOutputStream responseStream) {
        WebResponsive.responseWithFile(request.getQUERY(),responseStream);
      }
    });

    servlet.addHandler("GET", "/links.html", new WebHandler() {
      @Override
      public void handle(Request request, BufferedOutputStream responseStream) {
        WebResponsive.responseWithFile(request.getQUERY(),responseStream);
      }
    });

    servlet.addHandler("GET", "/forms.html", new WebHandler() {
      @Override
      public void handle(Request request, BufferedOutputStream responseStream) {
        WebResponsive.responseWithFile(request.getQUERY(),responseStream);
      }
    });

    servlet.addHandler("GET", "/classic.html", new WebHandler() {
      @Override
      public void handle(Request request, BufferedOutputStream responseStream) {
        WebResponsive.responseWithFile(request.getQUERY(),responseStream);
      }
    });

    servlet.addHandler("GET", "/events.html", new WebHandler() {
      @Override
      public void handle(Request request, BufferedOutputStream responseStream) {
        WebResponsive.responseWithFile(request.getQUERY(),responseStream);
      }
    });

    servlet.addHandler("GET", "/events.js", new WebHandler() {
      @Override
      public void handle(Request request, BufferedOutputStream responseStream) {
        WebResponsive.responseWithFile(request.getQUERY(),responseStream);
      }
    });


    servlet.listen(webPort);
  }
}


