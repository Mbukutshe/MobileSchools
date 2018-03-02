package com.payghost.mobileschools.Globals;

import com.payghost.mobileschools.Objects.DeleteOptions;
import com.payghost.mobileschools.Objects.SubjectAndGrade;

import java.util.List;

/**
 * Created by Payghost on 2/13/2018.
 */

public class Config {
    public static List<DeleteOptions> grades;
    public static List<SubjectAndGrade> grade_and_subjects;
    public static int grade_position;
    public static int size;
    public static String grade_name;
    public static String fragment;
    public static  int IS_GRADES_VISIBLE = 0;
    public static final int VIEW_TYPE_MESSAGE = 1;
    public static final int VIEW_TYPE_DOCUMENT = 2;
    public static final int VIEW_TYPE_IMAGE= 3;
    public static final int VIEW_TYPE_VIDEOS= 4;
    public static final int VIEW_TYPE_SCHOOLS= 5;
    public static final int VIEW_TYPE_GRADES= 1;
    public static final int VIEW_TYPE_GRADE_LIST= 6;
    public static final int VIEW_TYPE_SUBJECTS= 2;
    public static final String TAG_JSON_ARRAY = "result";
    public static final int RESULT_LOAD_IMAGE=1;
    public static final int CAMERA_REQUEST = 1888;
    public static final int RESULT_LOAD_VIDEO=2;

    public static final int VIEW_TYPE_DELETE = 1;
    public static final int VIEW_TYPE_OPTIONS = 2;
    public static String whichType;
    public static String which_one="null";
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
    public static final String URL_SCHOOL_REGISTRATION = " http://mydm.co.za/schools/school_registration.php";

    public static String REGISTER_DEVICE_URL = "http://mydm.co.za/bikes/scripts/register.php";

    public static String LOGIN_URL = "http://mydm.co.za/Autodealer/scripts/getIn.php";
    public static String RETRIEVE_URL = "http://mydm.co.za/Autodealer/scripts/retrieve.php";
    public static String RETRIEVE_VEHICLES_URL = "http://mydm.co.za/Autodealer/scripts/vehicle_retrieve.php";
    public static String ADD_NEW_USER_URL = "http://mydm.co.za/Autodealer/scripts/newUser.php";
    public static String FILTERING_VEHICLES_URL = "http://mydm.co.za/Autodealer/scripts/filtering_vehicles.php";
    public static String RETRIEVE_MAP_COORDINATES_URL = "http://mydm.co.za/Autodealer/scripts/retrieve_coordinates.php";
    public static String UPDATE_MAP_COORDINATES_URL = "http://mydm.co.za/Autodealer/scripts/insert_coordinates.php";
    public static String INSERT_POST_URL = "http://mydm.co.za/Autodealer/scripts/upload.php";
    public static String INSERT_VEHICLE_URL = "http://mydm.co.za/Autodealer/scripts/vehicle.php";
    public static String GROUPS_SELECTION= "http://mydm.co.za/Autodealer/scripts/groups_selection.php";
    public static String GROUP_INSERTION= "http://mydm.co.za/Autodealer/scripts/group_insertion.php";
    public static String USER_SUBSCRIPTION_URL= "http://mydm.co.za/Autodealer/scripts/subscribe.php";
    public static String USERS_DEVICES_URL= "http://mydm.co.za/Autodealer/scripts/devices.php";
    public static String TRASH_VEHICLE_URL= "http://mydm.co.za/Autodealer/scripts/devices.php";
    public static String RETRIEVAL_MESSAGE_CONDITION = "no";
    public static String RETRIEVAL_DOCUMENT_CONDITION = "yes";
    public static String RETRIEVAL_EMPTY_CONDITION = "nodata";
    public static String RETRIEVAL_TYPE = "type";
    public static String GROUP_NAME = "group_name";
    public static String SOURCE = "device";
    public static String GROUP_USERS = "group_users";
    public static String GROUP_CREATION_SUCCESS = "success";
    public static String GROUP_CREATION_FAIL = "fail";
    public static String TOKEN = "Token";
    public static String LATITUDE = "latitude";
    public static String LONGITUDE = "longitude";
    public static String UPLOAD_OPERATION = "upload";


}
