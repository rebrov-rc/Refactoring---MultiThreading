package ru.netology.web;

import java.io.BufferedOutputStream;

public interface WebHandler {
    void handle(Request request, BufferedOutputStream responseStream);
}
