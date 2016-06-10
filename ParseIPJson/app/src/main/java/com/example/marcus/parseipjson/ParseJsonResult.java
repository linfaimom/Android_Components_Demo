package com.example.marcus.parseipjson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by marcus on 16/6/9.
 */
public class ParseJsonResult {
    private static final String IPURL = "http://ip.taobao.com/service/getIpInfo.php";
    private static final int DEF_CONN_TIMEOUT = 30000;
    private static final String DEF_CHARSET = "UTF-8";

    public static String fetchResult(String ip) {
        return fetchResult(IPURL,ip,"GET");
    }

    private static String fetchResult(String ipUrl, String ip, String method) {
        String queryUrl = ipUrl + "?ip=" + ip;
        String result;
        //append each line and finally as a result
        StringBuilder allRead = new StringBuilder();

        HttpURLConnection connection;
        BufferedReader reader = null;
        URL url;
        try {
            //open the connection
            url = new URL(queryUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(DEF_CONN_TIMEOUT);
            connection.setReadTimeout(DEF_CONN_TIMEOUT);
            connection.setRequestMethod(method);
            connection.connect();
            //read from the url
            InputStream in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in,DEF_CHARSET));
            String aLine;
            while ((aLine = reader.readLine()) != null) {
                allRead.append(aLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //move content to result
        result = allRead.toString();
        return result;
    }

}
