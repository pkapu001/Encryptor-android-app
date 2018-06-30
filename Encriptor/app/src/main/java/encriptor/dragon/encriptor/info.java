package encriptor.dragon.encriptor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;

public class info extends Activity {

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_info);
            Slidr.attach(this);
        }catch (Exception ex)
        {
            Log.e("on info",ex.getMessage());
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
