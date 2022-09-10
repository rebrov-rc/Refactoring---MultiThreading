package ru.netology;


import ru.netology.web.Servlet;
import ru.netology.web.WebFactory;

public class Main {


  private static int webPort = 9999;
  private static final String GET = "GET";
  private static final String POST = "POST";


  public static void main(String[] args) {
    Servlet servlet = WebFactory.getServlet();
    servlet.listen(webPort);
  }
}


