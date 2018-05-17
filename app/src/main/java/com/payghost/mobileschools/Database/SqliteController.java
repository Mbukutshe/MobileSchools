package com.payghost.mobileschools.Database;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Wiseman on 10/7/2016.
 */
public class SqliteController extends SQLiteOpenHelper
{
    private static final String LOGCAT = null;
    public SqliteController(Context applicationcontext)
    {
        super(applicationcontext, "androidsqlite.db", null, 1);
        Log.d(LOGCAT,"Created");
    }
    @Override
    public void onCreate(SQLiteDatabase database)
    {
        String query;
        query = "CREATE TABLE Profile ( SchoolId TEXT, SchoolName TEXT,SchoolLogo TEXT,GradeId TEXT,GradeName TEXT,SubjectId TEXT,SubjectName TEXT,SubjectAction TEXT,GradeAction TEXT)";
        database.execSQL(query);

        String queri;
        queri = "CREATE TABLE IF NOT EXISTS User  (id TEXT,title TEXT,name TEXT,surname TEXT,dob TEXT,gender TEXT,email TEXT,pic TEXT)";
        database.execSQL(queri);
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version)
    {
        String query;
        query = "DELETE FROM Student";
        database.execSQL(query);
        onCreate(database);
    }
    public void dropTable()
    {
        String query;
        SQLiteDatabase database = this.getWritableDatabase();
        query = "DROP TABLE IF EXISTS Profile";
        database.execSQL(query);
        database.close();
    }
    public void insertProfile(String SchoolId,String SchoolName,String SchoolLogo,String GradeId,String GradeName,String SubjectId,String SubjectName,String SubjectAction,String GradeAction)
    {
        Log.d(LOGCAT,"insert");
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "INSERT INTO Profile (SchoolId,SchoolName,SchoolLogo,GradeId,GradeName,SubjectId,SubjectName,SubjectAction,GradeAction) VALUES('"+SchoolId+"','"+SchoolName+"','"+SchoolLogo+"', '"+GradeId+"','"+GradeName+"','"+SubjectId+"','"+SubjectName+"','"+SubjectAction+"','"+GradeAction+"');";
        Log.d("query",query);
        database.execSQL(query);
        database.close();
    }
    public void newSubject(String SchoolId,String gradeId,String gradeName,String subjectName,String subjectAction,String gradeAction)
    {
        Log.d(LOGCAT,"insert");
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "INSERT INTO Profile (SchoolId,SchoolName,SchoolLogo,GradeId,GradeName,SubjectId,SubjectName,SubjectAction,GradeAction) VALUES('"+SchoolId+"',' ', ' ', '"+gradeId+"','"+gradeName+"','00','"+subjectName+"','"+subjectAction+"','"+gradeAction+"');";
        Log.d("query",query);
        database.execSQL(query);
        database.close();
    }
    public void updateSchoolName(String schoolId,String schoolName)
    {
        Log.d(LOGCAT,"delete");
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "UPDATE Profile SET SchoolName='"+schoolName+"' where SchoolId='"+ schoolId +"'";
        Log.d("query",deleteQuery);
        database.execSQL(deleteQuery);
    }
    public void updateGrade(String gradeId,String gradeName)
    {
        Log.d(LOGCAT,"delete");
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "UPDATE Profile SET GradeName='"+gradeName+"' where GradeId='"+ gradeId +"'";
        Log.d("query",deleteQuery);
        database.execSQL(deleteQuery);
    }
    public void updateSubject(String gradeId,String subjectId,String SubjectName)
    {
        Log.d(LOGCAT,"delete");
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "UPDATE Profile SET SubjectName='"+SubjectName+"' where GradeId='"+ gradeId +"' AND SubjectId='"+subjectId+"'";
        Log.d("query",deleteQuery);
        database.execSQL(deleteQuery);
    }
    public void deleteGrade(String gradeId)
    {
        Log.d(LOGCAT,"delete");
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "UPDATE Profile SET GradeAction='DELETE' where GradeId='"+ gradeId +"'";
        Log.d("query",deleteQuery);
        database.execSQL(deleteQuery);
    }
    public void deleteSubject(String subjectId,String subjectName,String Action)
    {
        Log.d(LOGCAT,"delete");
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "UPDATE Profile SET SubjectAction='"+Action+"' where SubjectId='"+ subjectId +"'";
        Log.d("query",deleteQuery);
        database.execSQL(deleteQuery);
    }
    public Cursor getAllRecords()
    {
        String selectQuery = "SELECT * FROM Profile";
        String []columns = {"SchoolId","SchoolName","SchoolLogo","GradeId","GradeName","SubjectId","SubjectName","SubjectAction","GradeAction"};
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query("Profile",columns, null,null,null,null,null);

        return cursor;
    }
    public Cursor getAllMessage()
    {
        String selectQuery = "SELECT * FROM Message";
        String []columns = {"messageId","message","author","date","subject","urgent"};
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query("Message",columns, null,null,null,null,"date DESC");
        return cursor;
    }
    public void insertUser(String id,String title,String name,String surname,String dob,String gender,String email,String pic)
    {
        Log.d(LOGCAT,"insert");
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "INSERT INTO User (id,title,name,surname,dob,gender,email,pic) VALUES('"+id+"','"+title+"','"+name+"', '"+surname+"','"+dob+"','"+gender+"','"+email+"','"+pic+"');";
        Log.d("query",query);
        database.execSQL(query);
        database.close();
    }
    public Cursor getUser()
    {
        String selectQuery = "SELECT * FROM User";
        String []columns = {"id","title","name","surname","dob","gender","email","pic"};
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query("User",columns, null,null,null,null,"id DESC");
        return cursor;
    }
}
