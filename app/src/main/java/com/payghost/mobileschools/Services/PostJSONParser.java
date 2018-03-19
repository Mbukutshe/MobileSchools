package com.payghost.mobileschools.Services;


import com.payghost.mobileschools.Objects.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wiseman on 2017-10-15.
 */

public class PostJSONParser {

    static List<Item> itemList;

    public static List<Item> parseData(String content) {

        JSONArray items_arry = null;
        Item items = null;
        try {

            items_arry = new JSONArray(content);
            itemList = new ArrayList<>();

            for (int i = 0; i < items_arry.length(); i++) {

                JSONObject obj = items_arry.getJSONObject(i);
                items = new Item(obj.getString("subject").toString(),obj.getString("time").toString(),
                        obj.getString("description").toString(),obj.getString("which_one").toString(),
                        "",obj.getString("uploader").toString(),
                        obj.getString("url").toString(),"");
                itemList.add(items);
            }

        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
        return itemList;
    }

}
