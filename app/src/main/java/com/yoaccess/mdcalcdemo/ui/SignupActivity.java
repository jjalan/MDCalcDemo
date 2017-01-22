package com.yoaccess.mdcalcdemo.ui;

import com.yoaccess.mdcalcdemo.R;
import com.yoaccess.mdcalcdemo.User;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity implements User.AuthenticationCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onSignUpButtonClicked(View v) {

        String username = ((TextView) findViewById(R.id.activity_signup_username)).getText()
                .toString();

        String password = ((TextView) findViewById(R.id.activity_signup_password)).getText()
                .toString();

        String confirmPassword = ((TextView) findViewById(R.id.activity_signup_confirm_password))
                .getText()
                .toString();

        String firstName = ((TextView) findViewById(R.id.activity_signup_first_name)).getText()
                .toString();

        String lastName = ((TextView) findViewById(R.id.activity_signup_last_name)).getText()
                .toString();

        disableInputControls();

        User.signUp(username,
                password,
                confirmPassword,
                firstName,
                lastName,
                this);
    }

    @Override
    public void onUserSignUpSuccess() {

        String username = ((TextView) findViewById(R.id.activity_signup_username)).getText()
                .toString();
        String password = ((TextView) findViewById(R.id.activity_signup_password)).getText()
                .toString();

        User.login(username, password, this);
    }

    @Override
    public void onUserSignUpError(Exception error) {

        enableInputControls();

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(error.getMessage())
                .show();
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
        findViewById(R.id.activity_signup_username).setEnabled(false);
        findViewById(R.id.activity_signup_password).setEnabled(false);
        findViewById(R.id.activity_signup_confirm_password).setEnabled(false);
        findViewById(R.id.activity_signup_first_name).setEnabled(false);
        findViewById(R.id.activity_signup_last_name).setEnabled(false);
        findViewById(R.id.activity_signup_submit_button).setEnabled(false);
    }

    private void enableInputControls() {
        findViewById(R.id.activity_signup_username).setEnabled(true);
        findViewById(R.id.activity_signup_password).setEnabled(true);
        findViewById(R.id.activity_signup_confirm_password).setEnabled(true);
        findViewById(R.id.activity_signup_first_name).setEnabled(true);
        findViewById(R.id.activity_signup_last_name).setEnabled(true);
        findViewById(R.id.activity_signup_submit_button).setEnabled(true);
    }
}
