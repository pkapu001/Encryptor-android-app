package encriptor.dragon.encriptor;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class Org_text extends Fragment {


    EditText editText;


    public Org_text() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_org_text, container, false);

                editText = view.findViewById(R.id.org_tv);
                editText.setEnabled(MainActivity.notstarted);
               // editText.setText(MainActivity.org_msg);


        /*editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(editText.isEnabled())
                {
                    MainActivity.org_msg = editText.getText().toString();

                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
/*
        ConstraintLayout cy = (ConstraintLayout)view.findViewById(R.id.ff);
        cy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
*/






        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        MainActivity.org_msg = editText.getText().toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        editText.setText(MainActivity.org_msg);

    }

    public void update_org_txt(String s)
    {

        editText.setText(s);


    }


    public String get_orgmsg() {
        return editText.getText().toString();
    }

    public  void  reset()
    {
        editText.setEnabled(true);
        editText.setText("");

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }




}
