package com.example.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "college";  //DB name
    private static final int VERSION = 1;

    private static final String TABLE_NAME = "students";   //table name
    private static final String NAME = "name";
    private static final String ROLL = "roll";

    private static final String QUERY = "create table "+TABLE_NAME+" ( "+ROLL+" int primary key, "+NAME+" varchar(30) );";

    public Database(@Nullable Context context)
    {
        super(context,DATABASE_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        //use to create new database
         sqLiteDatabase.execSQL(QUERY);
    }

    @Override
    //int old ver & int new version of DB.
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_NAME+" "); //del existing table
        sqLiteDatabase.execSQL(QUERY);
    }


    public long insert(Security security)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ROLL,security.getRoll());
        cv.put(NAME,security.getName());

        return sqLiteDatabase.insert(TABLE_NAME,null,cv);
        //return 0;
    }


    public List<Security> fetch()
    {
        List<Security> list = new ArrayList();

        Cursor cursor= getReadableDatabase().query(TABLE_NAME,
                null,null,null,null,null,null);

        while (cursor.moveToNext())
        {
            Security security = new Security();
            int roll = cursor.getInt(0);
            String name = cursor.getString(1);

           // String data = roll+ "\t" +name;
            security.setRoll(roll);
            security.setName(name);

            list.add(security);
           // Log.e("error", roll + "\t" +name);
        }
        return list;
    }



    public long delete(int roll)
    {
        //where clause = column name, value of = column value
     return getWritableDatabase().delete(TABLE_NAME,""+ROLL+"=?",new String[]{String.valueOf(roll)});

    }


    public long update(Security security)
    {
        ContentValues cv = new ContentValues();
        cv.put(ROLL,security.getRoll()); //(optional)
        cv.put(NAME,security.getName());

      return getWritableDatabase().update(TABLE_NAME,cv,""+ROLL+"=?",
              new String[]{String.valueOf(security.getRoll())});
    }


    public Security getParticular(int roll)
    {
        Security security = null;
        Cursor c = getReadableDatabase().query(TABLE_NAME,null,""+ROLL+"=?",
                new String[]{String.valueOf(roll)},
                null,null,null);

        if (c.moveToNext())
        {
            security = new Security();

            int rollP = c.getInt(0);
            String name = c.getString(1);

            security.setRoll(rollP);
            security.setName(name);

        }
        return security;
    }

}
