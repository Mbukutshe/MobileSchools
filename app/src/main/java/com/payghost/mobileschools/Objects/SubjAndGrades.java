package com.payghost.mobileschools.Objects;

/**
 * Created by Wiseman on 2018-02-22.
 */

public class SubjAndGrades {
    public static String Grader="null";
    public static String Grade1="null";
    public static String Grade2="null";
    public static String Grade3="null";
    public static String Grade4="null";
    public static String Grade5="null";
    public static String Grade6="null";
    public static String Grade7="null";
    public static String Grade8="null";
    public static String Grade9="null";
    public static String Grade10="null";
    public static String Grade11="null";
    public static String Grade12="null";

    public String GradeId;
    public String GradeName;
    public String SubjectId;
    public String SubjectName;
    public String SubjectAction;
    public String GradeAction;
    public SubjAndGrades(String GradeId,String GradeName,String SubjectId,String SubjectName,String SubjectAction,String GradeAction)
    {
        this.GradeId = GradeId;
        this.GradeName = GradeName;
        this.SubjectId = SubjectId;
        this.SubjectName = SubjectName;
        this.SubjectAction = SubjectAction;
        this.GradeAction = GradeAction;
    }
}
