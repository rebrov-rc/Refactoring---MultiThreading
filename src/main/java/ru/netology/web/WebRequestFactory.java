package ru.netology.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class WebRequestFactory {

    private static final int limit = 4096;

    private static Map<String, String>  getOtherHeaders(BufferedReader in){
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

    private static Map<String, String> getQuery(String qu){
        Map<String, String> query = new HashMap<>();

        for (String item : qu.split("&")){
            String [] kv = item.split("=");
            query.put(kv[0], kv[1]);
        }
        return query;
    }

    private static boolean checkLimit(String line){
        int res = line.indexOf(line, limit);
        return res != -1? false : true;
    }

    public static WebRequest getRequest(BufferedReader in){
        WebRequest request = null;
        String method = null, path = null;
        Map<String, String> query = null, otherHeaders = null;

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

                request = new WebRequest(method, path, query, otherHeaders);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }


        return request;
    }

}
