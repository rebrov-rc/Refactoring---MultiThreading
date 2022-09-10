package ru.netology.web;

import ru.netology.web.webhandlers.WebHandler;

public interface Servlet {
    void listen(int port);
    void addHandler(String reqMethod, String path, WebHandler handler);
}
