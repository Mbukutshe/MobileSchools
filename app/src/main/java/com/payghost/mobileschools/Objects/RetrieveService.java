package com.payghost.mobileschools.Objects;

/**
 * Created by Wiseman on 2018-02-13.
 */

public class RetrieveService {
    public String title;
    public String message;
    public String date;
    public String link;
    public String school_name;
    public String grade_name;
    public RetrieveService()
    {

    }
    public RetrieveService(String title, String message, String date, String link) {
        this.title = title;
        this.message = message;
        this.date = date;
        this.link = link;
    }
    public RetrieveService(String school_name)
    {
        this.school_name = school_name;
    }
    public void setGrade(String grade_name)
    {
        this.grade_name = grade_name;
    }
}
