package ru.netology2.web;

import java.util.Map;

public class Request {


    private final String METHOD;
    private final String PATH;
    private final Map<String, String> QUERY;
    private final Map<String, String> otherHeaders;


    public Request(String method, String path, Map<String, String> query, Map<String, String> otherHeaders){
        this.METHOD = method;
        this.PATH = path;
        this.QUERY = query;
        this.otherHeaders = otherHeaders;
    }

    public String getMETHOD() {
        return METHOD;
    }

    public String getPath(){return PATH;}

    public Map<String, String> getQUERY() {
        return QUERY;
    }

    public String getValue(String key){
        return otherHeaders.get(key);
    }

    @Override
    public String toString() {
        return "WebRequest{" +
                "METHOD='" + METHOD + '\'' +
                ", PATH='" + PATH + '\'' +
                ", QUERY=" + QUERY +
                ", otherHeaders=" + otherHeaders +
                '}';
    }
}
