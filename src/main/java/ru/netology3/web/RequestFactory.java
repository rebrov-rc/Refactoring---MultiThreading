package ru.netology3.web;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestFactory {

    private static final int limit = 4096;

    private static Map<String, String> getOtherHeaders(BufferedReader in){
        Map<String, String> others = new HashMap<>();

        while(true){

            String item = "";

            try {
                item = in.readLine();
                if (item == null || item.length() == 0) {
                    break;
                }
                String [] kv = item.split(": ");
                if (kv.length != 0 && kv.length > 1){
                    others.put(kv[0], kv[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (NullPointerException e){
                e.printStackTrace();
                break;
            }

        }
        return others;
    }

    private static List<NameValuePair> getQuery(String qu){
        List<NameValuePair> query = URLEncodedUtils.parse(qu, Charset.defaultCharset());
        return query;
    }

    private static boolean checkLimit(String line){

        try{
            line.charAt(limit);
        } catch (IndexOutOfBoundsException ex){
            return true;
        }
        return false;
    }

    public static Request getRequest(BufferedReader in){
        Request request = null;
        String method = null, path = null;
        List<NameValuePair> query = null;
        Map<String, String> otherHeaders = null;

        try {

            final String requestLine = in.readLine();
            if (requestLine != null && checkLimit(requestLine)){
                final String [] parts = requestLine.split(" ");
                if (parts.length != 3){
                    return null;
                }else{
                    method = parts[0];
                    String [] queryParts = parts[1].split("\\?");
                    path = queryParts[0];
                    if (queryParts.length > 1){
                        query = getQuery(queryParts[1]);
                    }

                }
                otherHeaders = getOtherHeaders(in);

                request = new Request(method, path, query, otherHeaders);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }


        return request;
    }

}
