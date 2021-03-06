package encriptor.dragon.encriptor;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static encriptor.dragon.encriptor.MainActivity.notstarted;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtogle;
    public NavigationView navigationView  ;
    public static background_thread backgroundThread = new background_thread();



    public static boolean advance_mode=false , encription_path_setting = true;
    public static String encription_path = "";

    public static FragmentManager fragmentManager;
    public static InputStream is;
    public ToggleButton toggle ;
    public RadioGroup radioGroup ;
    public static boolean notstarted=true;
    public static String org_msg = "" , mod_msg = "";
    public static ArrayList<morse> mkey = new ArrayList<>();
    public static ClipboardManager clipboardManager;
    public static ClipData clipData ;

    Org_text org ;
    Mod_text mod ;

    Button e_c_b , d_c_b;
    EditText ek , dk;
    TextView path_text;

    public static shared_pref_config saved_settings;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtogle.onOptionsItemSelected(item))
        {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backgroundThread.start();

        saved_settings = new shared_pref_config(getApplicationContext());
        advance_mode = saved_settings.read_adv_setting();
        encription_path_setting = saved_settings.read_show_path_setting();
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        drawerLayout = findViewById(R.id.root);
        mtogle = new ActionBarDrawerToggle(this , drawerLayout, R.string.open , R.string.close);
        drawerLayout.addDrawerListener(mtogle);
        mtogle.syncState();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.noun_ic);

        navigationView =(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getFragmentManager();
        org = new Org_text();
        mod = new Mod_text();
        path_text = findViewById(R.id.encription_path);
        if(encription_path_setting){ path_text.setText(encription_path);
        }else { path_text.setText("");}

        if(encription_path_setting)
        {
            path_text.setVisibility(View.VISIBLE);
        }else
        {
            path_text.setVisibility(View.INVISIBLE);
        }


        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        // ADDING FRAGMENT
         if (findViewById(R.id.frag_contaner) != null)
         {
            if (savedInstanceState != null) {
                return;
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
             //Org_frag org = new Org_frag();

            fragmentTransaction.add(R.id.frag_contaner, org, null);
            fragmentTransaction.commit();
           // fragmentManager.beginTransaction().replace(R.id.frag_contaner,org , null).commit();
        }

        // TOGGLE BUTTON ACTION
        toggle = (ToggleButton) findViewById(R.id.toggleButton);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fragmentManager.beginTransaction().replace(R.id.frag_contaner, mod, null).commit();
                } else {
                    fragmentManager.beginTransaction().replace(R.id.frag_contaner, org, null).commit();
                }
            }
        });

        { /// reading morse key to program;
            {
                try {
                    is = getAssets().open("morsekey.txt");
                    //Toast.makeText(MainActivity.this,"morse key .txt loaded",Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                    //Toast.makeText(MainActivity.this,"FAILED TO LOAD morse key .txt",Toast.LENGTH_SHORT).show();
                }
            }

            try {
                morse.load();
                //Toast.makeText(MainActivity.this, " mkey load sucess" , Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }



        /// RADIOBUTTON ACTION
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View v = findViewById(R.id.radioGroup);
                hideKeyboard(v);
                RadioButton radioButton = (RadioButton)findViewById(checkedId);
                Toast.makeText(MainActivity.this,"select your choice to ->" + radioButton.getText(),Toast.LENGTH_SHORT).show();
                LinearLayout el = (LinearLayout)findViewById(R.id.e_layout);
                LinearLayout dl = (LinearLayout)findViewById(R.id.d_layout);

                if(R.id.encript_rb == checkedId){
                    el.setEnabled(true);
                    el.setVisibility(View.VISIBLE);
                    dl.setEnabled(false);
                    dl.setVisibility(View.INVISIBLE);
                } else if(R.id.decript_rd == checkedId){
                    el.setEnabled(false);
                    el.setVisibility(View.INVISIBLE);
                    dl.setEnabled(true);
                    dl.setVisibility(View.VISIBLE);
                }
                else {
                    el.setEnabled(false);
                    el.setVisibility(View.INVISIBLE);
                    dl.setEnabled(false);
                    dl.setVisibility(View.INVISIBLE);
                }
            }
        });

        e_c_b = findViewById(R.id.e_caesar);
        d_c_b = findViewById(R.id.d_caesar);


        ek = findViewById(R.id.e_c_key);
        dk = findViewById(R.id.d_c_key);

        ek.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus)
                {
                    hideKeyboard(v);
                }
            }
        });

        ek.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {

                        case KeyEvent.KEYCODE_ENTER:
                            e_c_b(v);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });


        dk.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus)
                {
                    hideKeyboard(v);
                }

            }
        });

        dk.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {

                        case KeyEvent.KEYCODE_ENTER:
                            d_c_b(v);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

       // navigationView =(NavigationView) findViewById(R.id.nav_view);
       // navigationView.setNavigationItemSelectedListener(this);


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Intent intent =getIntent();
        finish();
        startActivity(intent);

    }

    public void e_at_b(View view) {
        if(notstarted)
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
        }else if(advance_mode)
        {
            mod_msg = mod.getmod_text();
        }
        mod_msg= encription.atbash(mod_msg);
       // fragmentManager.beginTransaction().replace(R.id.frag_contaner, mod, null).commit();
        if(notstarted)
        {
            toggle.setChecked(true);
        }else{
            mod.update_mod_text(mod_msg);
        }
       // if(encription_path_setting){
            encription_path += "E_AT-Bash -> ";
        if(encription_path_setting){ path_text.setText(encription_path);
        }else { path_text.setText("");}



        //}

        notstarted = false;
        hideKeyboard(view);


    }

    public void e_nl_b(View view)
    {

        if(notstarted )
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
        }else if(advance_mode)
        {
            mod_msg = mod.getmod_text();
        }
        mod_msg= encription.n_l(mod_msg);
       // fragmentManager.beginTransaction().replace(R.id.frag_contaner, mod, null).commit();

        if(notstarted)
        {
            toggle.setChecked(true);
        }else{
            mod.update_mod_text(mod_msg);
        }

        //if(encription_path_setting) {
            encription_path += "E_Number-Letter -> ";
        if(encription_path_setting){ path_text.setText(encription_path);
        }else { path_text.setText("");}



        hideKeyboard(view);
        notstarted = false;

    }

    public void e_m_b(View view)
    {
        if(notstarted)
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
        }else if(advance_mode)
        {
            mod_msg = mod.getmod_text();
        }
        mod_msg= morse.e_morse(mod_msg,mkey);
        if(notstarted)
        {

            toggle.setChecked(true);
        }else{
            mod.update_mod_text(mod_msg);
        }

        //if(encription_path_setting) {
            encription_path += "E_Morse -> ";
        if(encription_path_setting){ path_text.setText(encription_path);
        }else { path_text.setText("");}

        hideKeyboard(view);
        notstarted = false;
    }

    public void d_at_b(View view)
    {
        if(notstarted)
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
        }else if(advance_mode)
        {
            mod_msg = mod.getmod_text();
        }
        mod_msg= encription.atbash(mod_msg);
        if(notstarted)
        {
            toggle.setChecked(true);
        }else{
            mod.update_mod_text(mod_msg);
        }

       // if(encription_path_setting) {
            encription_path += "D_AT-Bash -> ";
            if(encription_path_setting){ path_text.setText(encription_path);
        }else { path_text.setText("");}

        hideKeyboard(view);
        notstarted = false;
    }

    public void d_nl_b(View view)
    {
        if(notstarted)
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
        }else if(advance_mode)
        {
            mod_msg = mod.getmod_text();
        }
        mod_msg= decription.n_l(mod_msg);
        if(notstarted)
        {
            toggle.setChecked(true);
        }else{
            mod.update_mod_text(mod_msg);
        }
        //if(encription_path_setting) {
            encription_path += "D_Number-Letter -> ";
            if(encription_path_setting){ path_text.setText(encription_path);
        }else { path_text.setText("");}

        hideKeyboard(view);
        notstarted = false;
    }

    public void d_m_b(View view)
    {
        if(notstarted)
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
        }else if(advance_mode)
        {
            mod_msg = mod.getmod_text();
        }

        mod_msg= morse.d_morse(mod_msg,mkey);
        if(notstarted)
        {
            toggle.setChecked(true);
        }else{
            mod.update_mod_text(mod_msg);
        }

       // if(encription_path_setting) {
            encription_path += "D_Morse -> ";
            if(encription_path_setting){ path_text.setText(encription_path);
        }else { path_text.setText("");}

        hideKeyboard(view);
        notstarted = false;
    }

    public void e_c_b(View view) {
        int key=0;
        if(notstarted)
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
        }else if(advance_mode)
        {
            mod_msg = mod.getmod_text();
        }
        int temp;
        if(!ek.getText().toString().equals("")){
             temp = Integer.parseInt(ek.getText().toString());
        }else{
            ek.setText("0");
            temp = 0;
        }
        key = temp;

        if(key<27 && key >0  ) {
            mod_msg = encription.ceasar(key, mod_msg);

            if (notstarted) {
                toggle.setChecked(true);
            } else {
                mod.update_mod_text(mod_msg);
            }
           // if(encription_path_setting) {
                encription_path += "E_Caeser key:" + key+ " -> ";
                if(encription_path_setting){ path_text.setText(encription_path);
        }else { path_text.setText("");}

            notstarted = false;
        }else{
            Toast.makeText(MainActivity.this,"key should be between 0 and 27", Toast.LENGTH_SHORT ).show();
        }
        hideKeyboard(view);
    }

    public void d_c_b(View view) {
        int key=0;
        if(notstarted)
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
        }else if(advance_mode)
        {
            mod_msg = mod.getmod_text();
        }
        int temp;
        if(!dk.getText().toString().equals("")){
            temp = Integer.parseInt(dk.getText().toString());
            //Toast.makeText(MainActivity.this,"key = " + temp , Toast.LENGTH_SHORT ).show();

        }else{
            dk.setText("0");
            temp = 0;
        }

        key = temp;
       // Toast.makeText(MainActivity.this,"key = " +key, Toast.LENGTH_SHORT ).show();

        if(key<27 && key >0  ) {
            mod_msg = decription.ceasar(key, mod_msg);

            if (notstarted) {
                toggle.setChecked(true);
            } else {
                mod.update_mod_text(mod_msg);
            }


                encription_path += "D_Caeser key:" + key + " -> ";
                if(encription_path_setting){ path_text.setText(encription_path);
        }else { path_text.setText("");}



            notstarted = false;
        }else{
            Toast.makeText(MainActivity.this,"key should be between 0 and 27", Toast.LENGTH_SHORT ).show();
        }
        hideKeyboard(view);
    }

    public void reset_b(View view)
    {

        final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);

        final String s = ""+year+""+month+""+day+""+hour+""+minute+""+second;
        //final String s = ""+hour+"-"+minute+""+second+""+day+""+month+""+year;
        final Long id = Long.parseLong(s);


        if(!notstarted){
            final String o , m ,e ;
            o= org_msg;
            m = mod_msg;
            e=encription_path;

             new Thread(new Runnable() {
                @Override
                public void run() {

                    edbhelper helper = new edbhelper(MainActivity.this);

                    SQLiteDatabase database = helper.getWritableDatabase();
                    helper.addhistory(id,currentDateTimeString,o,m,e,database);


                    database.close();

                    Log.d("ID TIME :",s);
                }
            }).start();
/*
            edbhelper helper = new edbhelper(MainActivity.this);
            SQLiteDatabase database = helper.getWritableDatabase();
            helper.addhistory(org_msg,mod_msg,encription_path,database);
            database.close();
*/
        // MainActivity.backgroundThread.mHandler.post(new addtodatabase(MainActivity.this));
         //backgroundThread.start();

        RadioButton rb = findViewById(R.id.encript_rb);
            org.reset();
            mod.reset();
            org_msg="";
            mod_msg="";
            notstarted = true;
            mod.update_mod_text(mod_msg);

            toggle.setChecked(false);
            encription_path = "";
            if(encription_path_setting){ path_text.setText(encription_path);
        }else { path_text.setText(" ");}

            org.reset();
            ek.setText("");
            dk.setText("");
        }else
        {
            org.reset();
        }
        hideKeyboard(view);
        Toast.makeText(MainActivity.this , "RESET SUCESSFUL" ,Toast.LENGTH_SHORT).show();
    }

    public void COPY(View view) {
        if(toggle.isChecked())
        {
            clipData = ClipData.newPlainText("text",mod_msg);
            Toast.makeText(MainActivity.this , "Encrypted text copied to clipboard" ,Toast.LENGTH_SHORT).show();

        }else
        {
            clipData = ClipData.newPlainText("text",org.get_orgmsg());
            Toast.makeText(MainActivity.this , "Original text copied to clipboard" ,Toast.LENGTH_SHORT).show();
        }
        clipboardManager.setPrimaryClip(clipData);
    }

    public void showinfo(View view) {

        Intent intent = new Intent(this,info.class);
        startActivity(intent);
    }



    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void paste(View view) {
        if(notstarted)
        {
            ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
            org.update_org_txt(item.getText().toString());

        }else{
            Toast.makeText(MainActivity.this , "cant paste after action started, please reset" ,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        drawerLayout.closeDrawers();
        org_msg = org.get_orgmsg();
        if(!notstarted && mod != null)
        mod_msg = mod.getmod_text();

        //drawerLayout.closeDrawers();

       // encription_path = path_text.getText().toString();
    }


    @Override
    protected void onStop() {
        super.onStop();
        org_msg = org.get_orgmsg();
        if(!notstarted)
        mod_msg = mod.getmod_text();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(!notstarted  ){
            if( mod !=null )
            {
                mod.update_advance_mod(advance_mode);
            }

            if( org !=null )
            {
                org.setorgtext_enable(false);
            }
        }





        if(encription_path_setting)
        {
            path_text.setVisibility(View.VISIBLE);
            path_text.setText(encription_path);
        }else
        {
            path_text.setVisibility(View.INVISIBLE);
            path_text.setText(" ");
        }


       // Log.d("main rsme advmode", " " + advance_mode);
       // Log.d("main rsme advmode", " "+mod.get_edittext_isenabled());


    }

    @Override
    protected void onStart() {
        super.onStart();

            if(!notstarted && mod !=null)
                mod.update_advance_mod(advance_mode);




    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int n = item.getItemId() ;
        switch (n)
        {
            case R.id.home:
            {
                Toast.makeText(MainActivity.this , "You are in home" ,Toast.LENGTH_SHORT).show();

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

                Intent intent = new Intent(this,history.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(intent, 0);
               // Toast.makeText(MainActivity.this , "Coming soon" ,Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.save:
            {
                Toast.makeText(MainActivity.this , "Coming soon" ,Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.saved:
            {
                Toast.makeText(MainActivity.this , "Coming soon" ,Toast.LENGTH_SHORT).show();
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

    class addtodatabase implements Runnable
    {
        Context context;

        addtodatabase(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            Log.d("background process: ", " adding info to data base");
            final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
            int day = now.get(Calendar.DAY_OF_MONTH);
            int hour = now.get(Calendar.HOUR_OF_DAY);
            int minute = now.get(Calendar.MINUTE);
            int second = now.get(Calendar.SECOND);

            final String s = ""+year+""+month+""+day+""+hour+""+minute+""+second;
            //final String s = ""+hour+"-"+minute+""+second+""+day+""+month+""+year;
            final Long id = Long.parseLong(s);

            if(!MainActivity.notstarted) {
                final String o, m, e;
                o = MainActivity.org_msg;
                m = MainActivity.mod_msg;
                e = MainActivity.encription_path;


                edbhelper helper = new edbhelper(context);

                SQLiteDatabase database = helper.getWritableDatabase();
                helper.addhistory(id,currentDateTimeString,o,m,e,database);


                database.close();

                Log.d("ID TIME :",s);
            }

        }
    }
}
