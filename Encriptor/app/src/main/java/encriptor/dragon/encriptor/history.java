package encriptor.dragon.encriptor;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class history  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , View.OnLongClickListener{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public recycerAdapter adapter ;
    boolean inaction = false;
    TextView toolbar_counter ;
    Toolbar myToolbar;
    ArrayList <data> datalist = new ArrayList<>();
    ArrayList <data> selection_datalist = new ArrayList<>();
    int counter=0;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mtogle;
    private DrawerLayout drawerLayout ;


    String history = "HISTORY :";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* if(mtogle.onOptionsItemSelected(item))
        {

            return true;
        }*/

        if(item.getItemId()== R.id.deleat)
        {

            recycerAdapter adapter = (recycerAdapter)this.adapter;
            adapter.update_adapter(selection_datalist);

            edbhelper helper = new edbhelper(history.this);
            SQLiteDatabase database = helper.getWritableDatabase();
            helper.deleat(selection_datalist,database);
            database.close();


            clearaction();
        }else if(item.getItemId()== android.R.id.home)
        {
            clearaction();
            adapter.notifyDataSetChanged();
        }
        return true;
    }

    public void clearaction()
    {
            inaction=false;
            myToolbar.getMenu().clear();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toolbar_counter.setVisibility(View.GONE);
            toolbar_counter.setText("  0 items selected");
            counter = 0;
            selection_datalist.clear();
            if(datalist != null)
            {
                for (int i = 0 ; i < datalist.size() ; i++ ) {

                    datalist.get(i).setSelected(false);
                }
            }
    }

    @Override
    public void onBackPressed() {
        if(inaction)
        {
            clearaction();
            adapter.notifyDataSetChanged();
        }else
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        myToolbar = (Toolbar) findViewById(R.id.history_toolbar);
        setSupportActionBar(myToolbar);
        drawerLayout = findViewById(R.id.h_drawer);
        mtogle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mtogle);
       // mtogle.syncState();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.noun_ic);
        toolbar_counter = findViewById(R.id.H_T_item_count);
        toolbar_counter.setVisibility(View.GONE);

        navigationView =(NavigationView) findViewById(R.id.history_nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        recyclerView = findViewById(R.id.recycler_history);
       // recyclerView.addItemDecoration(new divider(history.this,LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setHasFixedSize(false);
       // recyclerView.setFocusable(true);



    }


    @Override
    protected void onPause() {
        super.onPause();
        drawerLayout.closeDrawers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        datalist.clear();

        new Thread(new Runnable() {
            @Override
            public void run() {

                edbhelper helper = new edbhelper(history.this);
                SQLiteDatabase db = helper.getReadableDatabase();
                Cursor cursor = helper.readhistory(db);
               /* int position = cursor.getCount()-1;
                for (int i  = 0 ; i < cursor.getCount();i++)
                {
                    cursor.moveToPosition(position-i);
                    data d = new data(  cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.org_text_h)),
                                        cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.mod_text_h)),
                                        cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.e_path_h)) );

                    datalist.add(d);

                }*/
                if(cursor.moveToFirst()) {

                    cursor.moveToLast();
                    do {
                        data d = new data(  cursor.getLong(cursor.getColumnIndex(Econtract.Econtract_entry.id_h)),
                                            cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.time)),
                                            cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.org_text_h)),
                                            cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.mod_text_h)),
                                            cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.e_path_h)));

                        datalist.add(d);

                    } while (cursor.moveToPrevious());

                    adapter = new recycerAdapter(datalist, history.this);
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }
            }
        }).start();

              // MainActivity.backgroundThread.mHandler.po


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //getMenuInflater().inflate(R.menu.history_menu,menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int n = item.getItemId() ;
        switch (n)
        {
            case R.id.home:
            {
                Intent intent= new Intent(history.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(intent, 0);

                break;
            }
            case R.id.settings:
            {
                /*try{
                    Intent intent = new Intent(this,settings.class);
                    startActivity(intent);
                }catch (Exception ex)
                {
                    Log.e("error on navigation",ex.getMessage());
                }*/
                Intent intent = new Intent(this,settings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(intent, 0);



                break;
            }
            case R.id.history:
            {


                Toast.makeText(history.this , "YOU ARE SEEING HISTORY" ,Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.save:
            {
                Toast.makeText(history.this , "Coming soon" ,Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.saved:
            {
                Toast.makeText(history.this , "Coming soon" ,Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.info1:
            {
                Intent intent = new Intent(this,info.class);
                startActivity(intent);
                break;
            }


        }
        return false;
    }

    public void prepareselection ( View v , int position)
    {

        if(((CheckBox)v).isChecked())
        {
                selection_datalist.add(datalist.get(position));
                counter++;
                updatecounter(counter);
        }else
        {
            selection_datalist.remove(datalist.get(position));
            counter--;
            updatecounter(counter);
        }

    }

    public void updatecounter(int counter)
    {
        if(counter==0)
        {
            toolbar_counter.setText("  0 items selected");
        }else
        {
            toolbar_counter.setText("  "+counter+" items selected");
        }
    }

    @Override
    public boolean onLongClick(View v) {

        myToolbar.getMenu().clear();
        myToolbar.inflateMenu(R.menu.history_action_menu);
        toolbar_counter.setVisibility(View.VISIBLE);
        inaction = true;
        adapter.notifyDataSetChanged();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return true;
    }

    class readingdatabase implements Runnable
    {

        @Override
        public void run() {
            datalist.clear();

            edbhelper helper = new edbhelper(history.this);
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = helper.readhistory(db);

            if(cursor.moveToFirst()) {

                cursor.moveToLast();
                do {
                    data d = new data(  cursor.getLong(cursor.getColumnIndex(Econtract.Econtract_entry.id_h)),
                            cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.time)),
                            cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.org_text_h)),
                            cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.mod_text_h)),
                            cursor.getString(cursor.getColumnIndex(Econtract.Econtract_entry.e_path_h)));

                    datalist.add(d);

                } while (cursor.moveToPrevious());

                adapter = new recycerAdapter(datalist, history.this);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                    }
                });

                Log.d("backgrond thread","printing history");
            }
        }
    }
}
