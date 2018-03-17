package com.payghost.mobileschools.Globals;

import android.graphics.Bitmap;

import com.payghost.mobileschools.Objects.DeleteOptions;
import com.payghost.mobileschools.Objects.SubjectAndGrade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Payghost on 2/13/2018.
 */

public class Config {
    public static List<DeleteOptions> grades;
    public static ArrayList<String> prefSubjects;
    public static List<SubjectAndGrade> grade_and_subjects;
    public static int grade_position;
    public static int size;
    public static boolean granted=false;
    public static String school_id;
    public static String grade_name;
    public static String grade_id;
    public static String fragment;
    public static Bitmap bitmap;
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
    public static final String URL_GET_ALL_SCHOOLS = " http://mydm.co.za/schools/get_schools.php";
    public static final String URL_GET_ALL_GRADES = " http://mydm.co.za/schools/get_grades.php";
    public static final String URL_GET_ALL_SUBJECTS = " http://mydm.co.za/schools/get_subjects.php";
    public static final String URL_SCHOOL_REGISTRATION = " http://mydm.co.za/schools/school_registration.php";
    public static final String URL_USER_REGISTRATION = " http://mydm.co.za/schools/user_registration.php";
    public static final String URL_STAFF_REGISTRATION = " http://mydm.co.za/schools/Adduser.php";
    public static final String URL_SEND_MESSAGE = "http://mydm.co.za/schools/send.php";
    public static final String URL_SEND_DOCUMENT = "https://mydm.co.za/schools/send_doc.php";
    public static final String URL_SEND_MEDIA = "https://mydm.co.za/schools/send_media.php";

    public static String REGISTER_DEVICE_URL = "http://mydm.co.za/bikes/scripts/register.php";

    public static String LOGIN_URL = "http://mydm.co.za/schools/getIn.php";
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

    //SCHOOL FIELDS
    public static final String TAG_SCHOOL_ID = "id";
    public static final String TAG_SCHOOL_LOGO = "logo";
    public static final String TAG_SCHOOL_NAME = "name";
    ///////////////////////////////////////////////////

    //GRADE FIELDS
    public static final String TAG_GRADE_ID = "id";
    public static final String TAG_GRADE_NAME = "grade";
    ////////////////////////////////////////////////////

    //SUBJECT FIELDS
    public static final String TAG_SUBJECT_ID = "id";
    public static final String TAG_SUBJECT_NAME = "subject";
    ////////////////////////////////////////////////////////

    //USER REGISTRATION
    public static String TAG_FIRST_NAME;
    public static String TAG_SURNAME;
    public static String TAG_DOB;
    public static String TAG_GENDER;
    public static String TAG_DEVICE;
    public static String TAG_EMAIL;
    public static String TAG_TITLE;
    public static String TAG_TYPE;
    public static String TAG_SUBJECT="";
    //////////////////////////////////////////////////////////

    //STAFF REGISTRATION
    public static String TAG_PASSWORD;
    public  static String TAG_USERNAME;
}
