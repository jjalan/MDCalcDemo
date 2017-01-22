package com.yoaccess.mdcalcdemo.ui;

import com.yoaccess.mdcalcdemo.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class OOBEActivity extends AppCompatActivity {

    private static final int REQUEST_LOGIN_ACTIVITY = 1001;

    private static final int REQUEST_SIGNUP_ACTIVITY = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oobe);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LOGIN_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                this.finish();
            }
        } else if (requestCode == REQUEST_SIGNUP_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable back button
    }

    public void onLoginButtonClicked(View v) {
        startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN_ACTIVITY);
    }

    public void onSignupButtonClicked(View v) {
        startActivityForResult(new Intent(this, SignupActivity.class), REQUEST_SIGNUP_ACTIVITY);
    }
}
