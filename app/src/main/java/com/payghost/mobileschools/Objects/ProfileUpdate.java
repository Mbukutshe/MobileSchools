package com.payghost.mobileschools.Objects;
/**
 * Created by Wiseman on 2018-04-09.
 */
public class ProfileUpdate {
    public String gradeId;
    public String gradeName;
    public String subjectId;
    public String subjectName;
    public ProfileUpdate( String gradeId,String gradeName,String subjectId,String subjectName)
    {
        this.gradeId = gradeId;
        this.gradeName = gradeName;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }
}