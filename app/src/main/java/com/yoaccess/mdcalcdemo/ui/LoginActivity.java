package com.yoaccess.mdcalcdemo.ui;

import com.yoaccess.mdcalcdemo.R;
import com.yoaccess.mdcalcdemo.User;
import com.yoaccess.mdcalcdemo.User.AuthenticationCallback;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements AuthenticationCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginButtonClicked(View v) {
        String username = ((TextView) findViewById(R.id.activity_login_username)).getText()
                .toString();
        String password = ((TextView) findViewById(R.id.activity_login_password)).getText()
                .toString();

        disableInputControls();
        User.login(username, password, this);
    }

    @Override
    public void onUserSignUpSuccess() {

    }

    @Override
    public void onUserSignUpError(Exception error) {

    }

    @Override
    public void onUserLoginSuccess() {

        enableInputControls();

        setResult(RESULT_OK);
        this.finish();
    }

    @Override
    public void onUserLoginError(Exception error) {

        enableInputControls();

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(error.getMessage())
                .show();
    }

    private void disableInputControls() {
        findViewById(R.id.activity_login_username).setEnabled(false);
        findViewById(R.id.activity_login_password).setEnabled(false);
        findViewById(R.id.activity_login_submit_button).setEnabled(false);
    }

    private void enableInputControls() {
        findViewById(R.id.activity_login_username).setEnabled(true);
        findViewById(R.id.activity_login_password).setEnabled(true);
        findViewById(R.id.activity_login_submit_button).setEnabled(true);
    }
}
