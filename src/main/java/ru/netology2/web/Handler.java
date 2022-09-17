package ru.netology2.web;

import java.io.BufferedOutputStream;

public interface Handler {
    void handle(Request request, BufferedOutputStream out);
}
