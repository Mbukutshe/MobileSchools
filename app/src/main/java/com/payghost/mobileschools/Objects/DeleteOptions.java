package com.payghost.mobileschools.Objects;

/**
 * Created by Wiseman on 2018-02-17.
 */

public class DeleteOptions {
    public int id;
    public String name;
    public String time;
    public String sender;
    public String subject;
    public String message;
    public String url;
    public String type;
    public DeleteOptions(String name)
    {
        this.name = name;
    }
    public DeleteOptions(int id,String name,String time,String subject,String message)
    {
        this.id = id;
        this.name = name;
        this.time = time;
        this.subject = subject;
        this.message = message;
    }
    public DeleteOptions(int id,String name,String time,String subject,String message,String url)
    {
        this.id = id;
        this.name = name;
        this.time = time;
        this.subject = subject;
        this.message = message;
        this.url = url;
    }

}
