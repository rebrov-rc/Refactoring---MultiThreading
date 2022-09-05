package ru.netology.web;

public class Request {

    private final String METHOD;
    private final String QUERY;

    public Request(String method, String query){
        this.METHOD = method;
        this.QUERY = query;
    }

    public String getMETHOD() {
        return METHOD;
    }

    public String getQUERY() {
        return QUERY;
    }
}
