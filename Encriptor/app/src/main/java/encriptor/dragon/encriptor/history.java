package encriptor.dragon.encriptor;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class history extends Activity {

    TextView textView;
    String history = "HISTORY :";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        textView = findViewById(R.id.historytext);




        textView.setText(history);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {

                edbhelper helper = new edbhelper(history.this);
                SQLiteDatabase db = helper.getReadableDatabase();
                Cursor cursor = helper.readhistory(db);
                int i = 1;

                cursor.moveToLast();

                do{
                    String ot = cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.org_text_h));
                    String mt = cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.mod_text_h));
                    String et = cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.e_path_h));
                    history = history + "\n\n" +i +". ORG_TEXT :" + ot + "\nMOD_TEXT :" + mt + "\n E_PATH :" + et;
                    i++;
                }while (cursor.moveToPrevious());
                Log.d("reading history :" , " ended");
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(history);
                    }
                });
            }
        }).start();

       // textView.setText(history);
       // Log.d("printing history :" , " ended");

    }
}
