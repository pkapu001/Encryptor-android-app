package encriptor.dragon.encriptor;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;



public class background_thread extends Thread
{
    public Handler mHandler;

    public void run() {
        Looper.prepare();

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                // process incoming messages here
            }
        };

        Looper.loop();
    }

}
