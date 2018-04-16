package com.payghost.mobileschools.Objects;

/**
 * Created by Wiseman on 2018-02-15.
 */

public class SubjectAndGrade {
    public String name;
    public String gradeId;
    public String subjectId;
    public String subjectName;
    public SubjectAndGrade(String name)
    {
        this.name = name;
    }
    public SubjectAndGrade(String name,String gradeId,String subjectId,String subjectName)
    {
        this.name = name;
        this.gradeId = gradeId;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }
    public SubjectAndGrade(String subjectName,String gradeId)
    {
        this.gradeId = gradeId;
        this.subjectName = subjectName;
    }
}
