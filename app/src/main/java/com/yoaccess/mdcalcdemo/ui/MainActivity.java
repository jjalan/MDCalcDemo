package com.yoaccess.mdcalcdemo.ui;

import com.yoaccess.mdcalcdemo.R;
import com.yoaccess.mdcalcdemo.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If user is not signed in, show the login activity
        if (User.getCurrentUser() == null) {
            startActivity(new Intent(this, OOBEActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                User.logout();
                startActivity(new Intent(this, OOBEActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // set the name
        if (User.getCurrentUser() != null) {
            ((TextView) findViewById(R.id.activity_main_text_view))
                    .setText("Welcome " + User.getCurrentUser().getFirstName());
        }
    }
}
