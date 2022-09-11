package ru.netology;

import ru.netology.web.Server;


public class Main {

  private static int webPort = 9999;

  public static void main(String[] args) {
    Server servlet = new Server();
    servlet.listen(webPort);
  }
}


