package com.divya.demo.core.service.impl;

import com.divya.demo.core.service.RandomUserService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RandomUserServiceImpl implements RandomUserService {



    @Override
    public String getUsers() {

        HttpURLConnection conn = null;
        BufferedReader reader = null;
        JsonObject jsonObject = null;
        StringBuilder responseStrBuilder = null;
        try{
            URL url = new URL("https://randomuser.me/api/");
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP GET Request Failed with Error code : "
                        + conn.getResponseCode());
            }

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = reader.readLine()) != null)
                responseStrBuilder.append(inputStr);


        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(reader!=null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                conn.disconnect();
            }
        }
        return responseStrBuilder.toString();
    }
}
