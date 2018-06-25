package encriptor.dragon.encriptor;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Mod_interface,Org_interface  {

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







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.noun_ic);



        setContentView(R.layout.activity_main);


        fragmentManager = getFragmentManager();
          org = new Org_text();
          mod = new Mod_text();


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
        }
        mod_msg= encription.atbash(mod_msg);
       // fragmentManager.beginTransaction().replace(R.id.frag_contaner, mod, null).commit();
        if(notstarted)
        {
            toggle.setChecked(true);
        }else{
            mod.update_mod_text(mod_msg);
        }

        notstarted = false;


    }

    public void e_nl_b(View view)
    {

        if(notstarted)
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
        }
        mod_msg= encription.n_l(mod_msg);
       // fragmentManager.beginTransaction().replace(R.id.frag_contaner, mod, null).commit();

        if(notstarted)
        {
            toggle.setChecked(true);
        }else{
            mod.update_mod_text(mod_msg);
        }

        notstarted = false;
    }

    public void e_m_b(View view)
    {
        if(notstarted)
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
        }
        mod_msg= morse.e_morse(mod_msg,mkey);
        if(notstarted)
        {

            toggle.setChecked(true);
        }else{
            mod.update_mod_text(mod_msg);
        }

        notstarted = false;
    }

    public void d_at_b(View view)
    {
        if(notstarted)
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
        }
        mod_msg= encription.atbash(mod_msg);
        if(notstarted)
        {
            toggle.setChecked(true);
        }else{
            mod.update_mod_text(mod_msg);
        }

        notstarted = false;
    }

    public void d_nl_b(View view)
    {
        if(notstarted)
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
        }
        mod_msg= decription.n_l(mod_msg);
        if(notstarted)
        {
            toggle.setChecked(true);
        }else{
            mod.update_mod_text(mod_msg);
        }

        notstarted = false;
    }

    public void d_m_b(View view)
    {
        if(notstarted)
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
        }

        mod_msg= morse.d_morse(mod_msg,mkey);
        if(notstarted)
        {
            toggle.setChecked(true);
        }else{
            mod.update_mod_text(mod_msg);
        }

        notstarted = false;
    }






    public void e_c_b(View view) {
        int key=0;
        if(notstarted)
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
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
            notstarted = false;
        }else{
            Toast.makeText(MainActivity.this,"key should be between 0 and 27", Toast.LENGTH_SHORT ).show();
        }

    }

    public void d_c_b(View view) {
        int key=0;
        if(notstarted)
        {
            org_msg = org.get_orgmsg();
            mod_msg = org_msg;
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
            notstarted = false;
        }else{
            Toast.makeText(MainActivity.this,"key should be between 0 and 27", Toast.LENGTH_SHORT ).show();
        }
    }

    public void reset_b(View view)
    {

        if(!notstarted){
            RadioButton rb = findViewById(R.id.encript_rb);

            org.reset();
            mod.reset();
            org_msg="";
            mod_msg="";
            notstarted = true;
            mod.update_mod_text(mod_msg);

            toggle.setChecked(false);


            org.reset();
            ek.setText("");
            dk.setText("");
        }else
        {
            org.reset();
        }

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
}
