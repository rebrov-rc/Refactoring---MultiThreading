package ru.netology.web;

import ru.netology.web.webhandlers.WebHandler;

public interface RequestHandlerContainer {
    void add(String method, WebHandler handler);
    WebHandler getHandler(String method);
}
