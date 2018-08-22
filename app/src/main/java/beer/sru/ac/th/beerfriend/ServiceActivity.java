package beer.sru.ac.th.beerfriend;

import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ServiceActivity extends AppCompatActivity {

    private String[] loginStrings;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        loginStrings = getIntent().getStringArrayExtra("Login");

//        Create Toolbar
        createToolbar();

        AddFragment(savedInstanceState);

//        Show Title
        showTitle();

//        Exit Controller
        exitController();


    }//Main Method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    private void exitController() {
        TextView textView = findViewById(R.id.txtExit);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void showTitle() {
        TextView textView = findViewById(R.id.txtTitle);
        textView.setText(loginStrings[1] + " OnLine");
    }

    private void AddFragment(Bundle savedInstanceState) {
        if (savedInstanceState==null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentFragmentServiceButton, PostMessageFragment.postMessageInstance(
                            loginStrings[4],
                            loginStrings[0],
                            loginStrings[5]))
                    .commit();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentFragmentServiceTop,new ListFriendFragment())
                    .commit();

        }
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarService);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(loginStrings[1] + "online");

        drawerLayout = findViewById(R.id.drawerLayoutService);
        actionBarDrawerToggle = new ActionBarDrawerToggle(ServiceActivity.this,
                drawerLayout,R.string.open,R.string.close);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_action_hamberger);

    }
}
