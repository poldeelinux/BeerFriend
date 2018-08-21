package beer.sru.ac.th.beerfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Register Controller
        registerController();
//        Login Controller
        loginController();


    }//Main Method

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

                String userString = userEditText.getText().toString().trim();
                String passwordString = passwordEditText.getText().toString().trim();

                MyAlert myAlert = new MyAlert(getActivity());


                if (userString.isEmpty() || passwordString.isEmpty()) {
                    myAlert.normalDialog("Have Space",
                            "Please Fill All Blank");
                } else {
                     checkAuthen(userString, passwordString);
                }
            }
        });
    }

    private void checkAuthen(String userString, String passwordString) {
        MyConstant myConstant = new MyConstant();
        MyAlert myAlert = new MyAlert(getActivity());
        String tag = "21AugV3";

        try {
            ReadAllData readAllData = new ReadAllData(getActivity());
            readAllData.execute(myConstant.getUrlGetAllUserString());
            String jsonString = readAllData.get();
            Log.d(tag, "json==>" + jsonString);

            String[] columnStrings = myConstant.getColumnUserStrings();
            String[] loginStrings = new String[columnStrings.length];
            boolean userABool = true;


            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i=0;i<jsonArray.length();i+=1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (userString.equals(jsonObject.getString("User"))) {
                    userABool = false;
                    for (int i1=0;i1<columnStrings.length;i1+=1) {
                        loginStrings[i1] = jsonObject.getString(columnStrings[i1]);
                    }//for
                }//if
            }//for

            if (userABool) {
                myAlert.normalDialog("User False",
                        "No" + userString + "in my Database");
            } else if (passwordString.equals(loginStrings[3])) {
                Toast.makeText(getActivity(),"Welcome "+ loginStrings[1],
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),ServiceActivity.class);
                intent.putExtra("Login",loginStrings);
                startActivity(intent);
                getActivity().finish();

            } else {
                myAlert.normalDialog("Password False",
                        "Please Try Again Password False");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void registerController() {
            TextView textView = getView().findViewById(R.id.txtRegister);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Replace Fragment
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contentFragmentMain, new RegisterFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }
}   //Main Class


