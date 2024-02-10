package edu.utem.ftmk.sqlite.sqliteDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import edu.utem.ftmk.sqlite.Student;

public class DatabaseAttendance extends SQLiteOpenHelper {
    public static final String dbName = "dbAttendance";
    public static final String tblStudents = "students";
    public static final String colStdName = "stud_name";
    public static final String colStdNo = "stud_no";
    public static final String colStdGender = "stud_gender";
    public static final String colStdState = "stud_state";
    public static final String colEmail = "stud_email";

    public static final int intVrsn = 1;

    public static final String strCrtTblStd = "CREATE TABLE " + tblStudents + "( " + colStdNo + " TEXT PRIMARY KEY, " + colStdName + " TEXT, " + colStdGender + " TEXT, " + colStdState + " TEXT, " + colEmail + " TEXT)";

    public static final String strDrpTblStd = "Drop table if exists students";

    public DatabaseAttendance(@Nullable Context context) {
        super(context, dbName, null, intVrsn);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(strCrtTblStd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(strDrpTblStd);
    }

    public int fnInsertStudent(Student student){
        int resCode = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colStdNo, student.getStrStudNo());
        values.put(colEmail, student.getStrEmail());
        values.put(colStdGender, student.getStrGender());
        values.put(colStdName, student.getStrFullname());
        values.put(colStdState, student.getStrState());

        resCode = (int) db.insert(tblStudents,null,values);
        return resCode;
    }

    @SuppressLint("Range")
    public List<Student> fnGetAllStudents()
    {
        List<Student> studLists = new ArrayList<Student>();
        String strSelectAll = "Select * from " + tblStudents;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(strSelectAll,null);
        if(cursor.moveToFirst())
        {
            do {
                String strStudName = cursor.getString(cursor.getColumnIndex(colStdName));
                String strStudNo = cursor.getString(cursor.getColumnIndex(colStdNo));
                String strEmail = cursor.getString(cursor.getColumnIndex(colEmail));
                String strState = cursor.getString(cursor.getColumnIndex(colStdState));
                String strGender = cursor.getString(cursor.getColumnIndex(colStdGender));
                Student student = new Student(strStudName,strStudNo,strEmail,strGender,
                        "",strState);
                studLists.add(student);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studLists;
    }

    public int fnUpdateExpenses(Student student)
    {
        int retResult = 0;
        ContentValues values = new ContentValues();
        values.put(colStdName, student.getStrFullname());
        values.put(colStdGender, student.getStrGender());
        values.put(colEmail, student.getStrEmail());
        values.put(colStdState, student.getStrState());

        String [] argg = {String.valueOf(student.getStrStudNo()) };
        this.getWritableDatabase().update(tblStudents,values,colStdNo + " =?", argg);
        return retResult;
    }

    public int fnDeleteExpenses(Student student)
    {
        int retResult = 0;
        String [] argg = {String.valueOf(student.getStrStudNo()) };
        this.getWritableDatabase().delete(tblStudents,colStdNo + " =?", argg);
        return retResult;
    }
}
