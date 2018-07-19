package encriptor.dragon.encriptor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class edbhelper extends SQLiteOpenHelper{

    public static final String database_name = "History";
    public static final int database_version = 1;



    public static final String CREATE_TABLE = "create table "+ Econtract.Econtract_entry.table_name +
            " ( "+Econtract.Econtract_entry.id_h+ " real,"
            +Econtract.Econtract_entry.time+ " text,"
            +Econtract.Econtract_entry.org_text_h+ " text,"
            +Econtract.Econtract_entry.mod_text_h+ " text,"
            +Econtract.Econtract_entry.e_path_h+ " text);";

    public static final String DROP_TABLE ="drop table if exists "+ Econtract.Econtract_entry.table_name;

    public edbhelper(Context context)
    {
        super(context,database_name ,null,database_version);
        Log.d("database msgs", "database created");
    }

    public void addhistory(Long id , String t, String ot , String mt , String ep , SQLiteDatabase db)
    {

        long count = DatabaseUtils.queryNumEntries(db, Econtract.Econtract_entry.table_name);


        final String s ="SELECT MIN("+ Econtract.Econtract_entry.id_h + ")\n" +
                "FROM "+Econtract.Econtract_entry.table_name;
        if(count >=10)
        {
            Cursor cursor = db.rawQuery(s,null);
            if ( cursor!= null)
            {
                cursor.moveToFirst();
                String seletion = Econtract.Econtract_entry.id_h+"="+ cursor.getLong(0);
                db.delete(Econtract.Econtract_entry.table_name, seletion ,null);
            }

        }
        db.close();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Econtract.Econtract_entry.id_h,id);
        contentValues.put(Econtract.Econtract_entry.time,t);
        contentValues.put(Econtract.Econtract_entry.org_text_h,ot);
        Log.d("database msgs", "inserted a org m");
        contentValues.put(Econtract.Econtract_entry.mod_text_h,mt);
        Log.d("database msgs", "inserted a mod m");
        contentValues.put(Econtract.Econtract_entry.e_path_h,ep);
        Log.d("database msgs", "inserted a e_path");
        db.insert(Econtract.Econtract_entry.table_name,null,contentValues);
        Log.d("database msgs", "inserted a row...");
        Log.d("table size", " size :  " +        count);



    }

    public Cursor readhistory( SQLiteDatabase db)
    {
        String[] projections = {Econtract.Econtract_entry.id_h,
                                Econtract.Econtract_entry.time,
                                Econtract.Econtract_entry.org_text_h,
                                Econtract.Econtract_entry.mod_text_h,
                                Econtract.Econtract_entry.e_path_h};

        Cursor cursor = db.query(Econtract.Econtract_entry.table_name,projections,null , null, null,null, null);

        return cursor;

    }

    public void deleat(ArrayList <data> deleatlist , SQLiteDatabase db)
    {
        for( data d : deleatlist)
        {
            String seletion = Econtract.Econtract_entry.id_h+"="+d.getId();
            db.delete(Econtract.Econtract_entry.table_name, seletion ,null);
            Log.d("DELEATING", " item :  "+d.getId() + "___"  );

        }
        db.close();
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
