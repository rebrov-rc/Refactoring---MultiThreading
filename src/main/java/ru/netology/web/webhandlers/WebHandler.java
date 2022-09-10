package ru.netology.web.webhandlers;

import ru.netology.web.WebRequest;

import java.io.BufferedOutputStream;

public interface WebHandler {
    void handle(WebRequest request, BufferedOutputStream out);
}
