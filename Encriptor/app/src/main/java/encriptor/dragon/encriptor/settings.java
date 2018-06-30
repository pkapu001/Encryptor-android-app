package encriptor.dragon.encriptor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class settings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,CompoundButton.OnCheckedChangeListener{

    private NavigationView navigationView;
    private ActionBarDrawerToggle mtogle;
    private Switch adv_switch ,path_switch ;
    private DrawerLayout drawerLayout ;


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
        setContentView(R.layout.activity_settings);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        drawerLayout = findViewById(R.id.s_drawer);
        mtogle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mtogle);
        mtogle.syncState();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.noun_ic);

        navigationView =(NavigationView) findViewById(R.id.settings_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        adv_switch = findViewById(R.id.adv_switch);
        adv_switch.setOnCheckedChangeListener(this);
        adv_switch.setChecked(MainActivity.advance_mode);

        path_switch = findViewById(R.id.encryptin_path_switch);
        path_switch.setOnCheckedChangeListener(this);
        path_switch.setChecked(MainActivity.encription_path_setting);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int n = item.getItemId() ;
        switch (n)
        {
            case R.id.home:
            {
                Intent intent= new Intent(settings.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(intent, 0);


                break;
            }
            case R.id.settings:
            {

                Toast.makeText(settings.this , "You are in settings" ,Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.history:
            {

                Toast.makeText(settings.this , "Coming soon" ,Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.save:
            {
                Toast.makeText(settings.this , "Coming soon" ,Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.saved:
            {
                Toast.makeText(settings.this , "Coming soon" ,Toast.LENGTH_LONG).show();
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

    @Override
    protected void onPause() {
        super.onPause();
        drawerLayout.closeDrawers();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int n = buttonView.getId();
       // Toast.makeText(settings.this,"advance mode updated ", Toast.LENGTH_SHORT).show();
        switch (n)
        {
            case R.id.adv_switch:
            {
                MainActivity.advance_mode = isChecked;
                MainActivity.saved_settings.write_adv_setting(MainActivity.advance_mode);
                Toast.makeText(settings.this,"advance mode updated ", Toast.LENGTH_SHORT).show();
                break ;
            }

            case R.id.encryptin_path_switch:
            {
                MainActivity.encription_path_setting = isChecked;
                MainActivity.saved_settings.write_show_path_setting(MainActivity.encription_path_setting);
                Toast.makeText(settings.this,"advance mode updated ", Toast.LENGTH_SHORT).show();
                break ;
            }
        }
    }
}
