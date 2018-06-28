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
import android.widget.Toast;


public class settings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;
    private ActionBarDrawerToggle mtogle;


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
        DrawerLayout drawerLayout = findViewById(R.id.s_drawer);
        mtogle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mtogle);
        mtogle.syncState();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.noun_ic);

        navigationView =(NavigationView) findViewById(R.id.settings_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int n = item.getItemId() ;
        switch (n)
        {
            case R.id.home:
            {
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.settings:
            {

                Toast.makeText(settings.this , "You are in settings" ,Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.history:
            {


                break;
            }
            case R.id.save:
            {

                break;
            }
            case R.id.saved:
            {

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
}
