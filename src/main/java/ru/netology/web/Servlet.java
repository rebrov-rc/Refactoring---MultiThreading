package ru.netology.web;

public interface Servlet {
    void listen(int port);
    void addHandler(String reqMethod, String path, WebHandler handler);
}
