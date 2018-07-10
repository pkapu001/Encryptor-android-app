package encriptor.dragon.encriptor;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;



/**
 * A simple {@link Fragment} subclass.
 */
public class Mod_text extends Fragment {


    EditText editText;
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

          editText = view.findViewById(R.id.mod_tv);
          update_mod_text(MainActivity.mod_msg);
          editText.setEnabled(MainActivity.advance_mode);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }else {
                    showKeybord(v);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        editText.setText(MainActivity.mod_msg);
        editText.setEnabled(MainActivity.advance_mode);
    }

    public void update_mod_text(String s){
        editText.setText(s);
        editText.setSelection(editText.getText().length());
    }

    public String getmod_text()
    {
        return editText.getText().toString();
    }

    public void update_advance_mod(boolean b){ editText.setEnabled(b);}

    public boolean get_edittext_isenabled()
    { return editText.isEnabled();}

    public void reset()
    {
        editText.setText("");
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void showKeybord(View view)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }




}
