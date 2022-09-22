package ru.netology3.web;

import org.apache.http.NameValuePair;

import java.util.List;
import java.util.Map;

public class Request {


    private final String METHOD;
    private final String PATH;
    private final List<NameValuePair> QUERY;
    private final Map<String, String> otherHeaders;


    public Request(String method, String path, List<NameValuePair> query, Map<String, String> otherHeaders){
        this.METHOD = method;
        this.PATH = path;
        this.QUERY = query;
        this.otherHeaders = otherHeaders;
    }

    public String getMETHOD() {
        return METHOD;
    }

    public String getPath(){return PATH;}

    public List<NameValuePair> getQueryParams() {
        return QUERY;
    }

    public NameValuePair getQueryParam(String name){
        for (NameValuePair pair : QUERY){
            if (pair.getName().equals(name)){
                return pair;
            }
        }
        return null;
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
