package com.payghost.mobileschools.Globals;

/**
 * Created by Payghost on 2/13/2018.
 */

public class Config {
    public static String fragment;
    public static  int IS_GRADES_VISIBLE = 0;
    public static final int VIEW_TYPE_MESSAGE = 1;
    public static final int VIEW_TYPE_DOCUMENT = 2;
    public static final int VIEW_TYPE_IMAGE= 3;
    public static final int VIEW_TYPE_VIDEOS= 4;
    public static final int VIEW_TYPE_SCHOOLS= 5;
    public static final int VIEW_TYPE_GRADES= 1;
    public static final int VIEW_TYPE_SUBJECTS= 2;
    public static final String TAG_JSON_ARRAY = "result";
    public static final int RESULT_LOAD_IMAGE=1;
    public static final int CAMERA_REQUEST = 1888;
    public static final int RESULT_LOAD_VIDEO=2;

    ////////// Messages ////////////
    public static final String TAG_MESSAGE_SENDER = "sender";
    public static final String TAG_MESSAGE_TIME = "date";
    public static final String TAG_MESSAGE_TITLE = "title";
    public static final String TAG_MESSAGE_MESSAGE = "message";
    public static final String URL_GET_ALL_MESSAGES = " http://mydm.co.za/schools/RetrieveMessages.php";

    ////////// Messages ////////////
    public static final String TAG_RESOURCE_LINK = "link";
    public static final String TAG_RESOURCE_TYPE = "type";
    public static final String TAG_RESOURCE_GRADE = "grade";
    public static final String TAG_RESOURCE_SUBJECT = "subject";
    public static final String TAG_RESOURCE_DESCRIPTION= "description";
    public static final String URL_GET_ALL_RESOURCES = " http://mydm.co.za/schools/Retrieveresources.php";


}
