package com.example.microponenciaandroidvolley;

import android.nfc.Tag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ImageInfo {
    String Url;
    String Description;
    String TheUserName;
    ArrayList<String> Tags = new ArrayList<>();

    public ImageInfo(JSONObject object) {
        try {
            Url = object.getJSONObject("urls").getString("regular");
            TheUserName = object.getJSONObject("user").getString("username");
            Description = object.getString("description");

            JSONArray ListaTags = object.getJSONArray("tags");
            for (int cnt = 0; cnt < ListaTags.length(); cnt++) {
            Tags.add(ListaTags.getJSONObject(cnt).getString("title"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String GetAlertFormat()
    {
        DecimalFormat format = new DecimalFormat("##.00");
        StringBuilder str = new StringBuilder();

        str.append("<Strong>");
        str.append("Descripción: ");
        str.append("</Strong>");
        str.append(this.Description);
        str.append("<br>");

        str.append("<Strong>");
        str.append("Autor: ");
        str.append("</Strong>");
        str.append(this.TheUserName);
        str.append("<br>");

        str.append("<Strong>");
        str.append("Tags: ");
        str.append("</Strong>");
        str.append("<ul>");
        for(String s: Tags)
        {
            str.append("<li>");
            str.append(s.replaceFirst(String.valueOf(s.charAt(0)),String.valueOf(s.charAt(0)).toUpperCase()));
            str.append("</li>");
        }
        str.append("</ul>");
        return str.toString();

    }
}
