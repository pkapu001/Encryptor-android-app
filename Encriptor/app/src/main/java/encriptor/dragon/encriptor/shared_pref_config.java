package encriptor.dragon.encriptor;

import android.content.Context;
import android.content.SharedPreferences;

public class shared_pref_config {

    SharedPreferences sharedPreferences;
    private Context context;

    public shared_pref_config(Context c)
    {
        context = c;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.settings),Context.MODE_PRIVATE);
    }


    public void write_adv_setting(boolean b)
    {
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.saved_advanced_mode),b);
        editor.commit();
    }

    public void write_show_path_setting(boolean b)
    {
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.saved_show_path),b);
        editor.commit();
    }

    public boolean read_adv_setting()
    {
        return sharedPreferences.getBoolean(context.getResources().getString(R.string.saved_advanced_mode),false);
    }

    public boolean read_show_path_setting()
    {
        return sharedPreferences.getBoolean(context.getResources().getString(R.string.saved_show_path),true);
    }
}


