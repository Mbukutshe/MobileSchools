package com.payghost.mobileschools.Globals;

/**
 * Created by Payghost on 2/13/2018.
 */

public class Config {
    public static String fragment;
    public static final int VIEW_TYPE_MESSAGE = 1;
    public static final int VIEW_TYPE_DOCUMENT = 2;
    public static final int VIEW_TYPE_IMAGE= 3;
    public static final String TAG_JSON_ARRAY = "result";

    ////////// Messages ////////////
    public static final String TAG_MESSAGE_SENDER = "sender";
    public static final String TAG_MESSAGE_TIME = "date";
    public static final String TAG_MESSAGE_TITLE = "title";
    public static final String TAG_MESSAGE_MESSAGE = "message";
    public static final String URL_GET_ALL_MESSAGES = " http://mydm.co.za/schools/RetrieveMessages.php";

    ////////// Messages ////////////
    public static final String TAG_RESOURCE_LINK = "message";
    public static final String TAG_RESOURCE_TYPE = "message";
    public static final String TAG_RESOURCE_GRADE = "message";
    public static final String TAG_RESOURCE_SUBJECT = "message";
    public static final String URL_GET_ALL_RESOURCES = " http://mydm.co.za/schools/RetrieveRecources.php";


}
