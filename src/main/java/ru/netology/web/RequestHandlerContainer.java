package ru.netology.web;

public interface RequestHandlerContainer {
    void add(String method, WebHandler handler);
    WebHandler getHandler(String method);
}
