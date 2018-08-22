package beer.sru.ac.th.beerfriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ServiceActivity extends AppCompatActivity {

    private String[] loginStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        loginStrings = getIntent().getStringArrayExtra("Login");

//        Create Toolbar
        createToolbar();

        if (savedInstanceState==null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentFragmentServiceButton, PostMessageFragment.postMessageInstance(
                            loginStrings[4],
                            loginStrings[0],
                            loginStrings[5]))
                    .commit();
        }


    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarService);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(loginStrings[1] + "online");
    }
}
