package com.oimtrust.osata.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oimtrust.osata.MainActivity;
import com.oimtrust.osata.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtRegister;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtRegister     = (TextView) findViewById(R.id.tv_register);
        txtRegister.setOnClickListener(this);

        btnLogin        = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog  = new AlertDialog.Builder(this);
        dialog.setTitle("Exit From Osata?");
        dialog.setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    /**
     * untuk callback ke Class Login
     *
     */
    private void loginButton(){
       startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    /**
     * untuk callback ke Class Register
     *
     */

    private void registerButton(){
       startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_register:
                registerButton();
                break;

            case R.id.btn_login:
                loginButton();
                break;
        }
    }
}
