package encriptor.dragon.encriptor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class edbhelper extends SQLiteOpenHelper{

    public static final String database_name = "History";
    public static final int database_version = 1;

    public static final String CREATE_TABLE = "create table "+ Econtract.Econtract_entry.table_name +
            " ( "+Econtract.Econtract_entry.org_text_h+ " text,"
            +Econtract.Econtract_entry.mod_text_h+ " text,"
            +Econtract.Econtract_entry.e_path_h+ " text);";

    public static final String DROP_TABLE ="drop table if exists "+ Econtract.Econtract_entry.table_name;

    public edbhelper(Context context)
    {
        super(context,database_name ,null,database_version);
        Log.d("database msgs", "database created");
    }

    public void addhistory( String ot , String mt , String ep , SQLiteDatabase db)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Econtract.Econtract_entry.org_text_h,ot);
        Log.d("database msgs", "inserted a org m");
        contentValues.put(Econtract.Econtract_entry.mod_text_h,mt);
        Log.d("database msgs", "inserted a mod m");
        contentValues.put(Econtract.Econtract_entry.e_path_h,ep);
        Log.d("database msgs", "inserted a e_path");

        db.insert(Econtract.Econtract_entry.table_name,null,contentValues);
        Log.d("database msgs", "inserted a row...");

    }

    public Cursor readhistory( SQLiteDatabase db)
    {
        String[] projections = {Econtract.Econtract_entry.org_text_h,Econtract.Econtract_entry.mod_text_h,Econtract.Econtract_entry.e_path_h};

        Cursor cursor = db.query(Econtract.Econtract_entry.table_name,projections,null , null, null,null, null);
        return cursor;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        Log.d("database msgs", "table is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }
}
