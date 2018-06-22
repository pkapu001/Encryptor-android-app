package encriptor.dragon.encriptor;


import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mod_text extends Fragment {


    TextView textView;
    //Mod_interface mc;
    public Mod_text() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       // mc = (Mod_interface) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view =inflater.inflate(R.layout.fragment_mod_text, container, false);

          textView = view.findViewById(R.id.mod_tv);
          update_mod_text(MainActivity.mod_msg);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        textView.setText(MainActivity.mod_msg);
    }

    public void update_mod_text(String s){
        textView.setText(s);
    }

    public void reset()
    {
        textView.setText("");
    }


}
