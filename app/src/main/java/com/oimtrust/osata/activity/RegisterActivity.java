package com.oimtrust.osata.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.oimtrust.osata.R;
import com.oimtrust.osata.utils.Constants;
import com.oimtrust.osata.utils.ValidateUserInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edit_name, edit_email, edit_password, edit_phone, edit_city;
    Spinner edit_gender;
    TextView edit_dob, txt_alreadyHave;
    Button btn_register;
    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat dateFormatter;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        String list[]={"-Pilih Gender-", "Laki-laki","Perempuan"};

        String email;
        if (savedInstanceState == null){
            Bundle extras   = getIntent().getExtras();
            email           = extras == null ? "" : extras.getString(Constants.TAG_EMAIL);
        }else {
            email           = savedInstanceState.getString(Constants.TAG_EMAIL);
        }

        edit_name           = (EditText) findViewById(R.id.et_nameRegister);
        edit_email          = (EditText) findViewById(R.id.et_emailRegister);
        edit_email.setText(email);

        edit_password       = (EditText) findViewById(R.id.et_passwordRegister);
        edit_phone          = (EditText) findViewById(R.id.et_phoneRegister);
        edit_dob            = (TextView) findViewById(R.id.tv_dobRegister);
        edit_city           = (EditText) findViewById(R.id.et_cityRegister);
        edit_gender         = (Spinner) findViewById(R.id.et_genderRegister);

        ArrayAdapter<String> AdapterList    = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        AdapterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_gender.setAdapter(AdapterList);

        txt_alreadyHave = (TextView) findViewById(R.id.tv_already_have);
        txt_alreadyHave.setOnClickListener(this);

        btn_register = (Button) findViewById(R.id.btn_registerRegister);
        btn_register.setOnClickListener(this);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void setDateTimeField() {
        edit_dob.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edit_dob.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptCreate() {
        // Store values at the time of the login attempt.
        String name = edit_name.getText().toString();
        String email = edit_email.getText().toString();
        String password = edit_password.getText().toString();
        String phone = edit_phone.getText().toString();
        String dob = edit_dob.getText().toString();
        String city = edit_city.getText().toString();
        String gender = edit_gender.getSelectedItem().toString();

        boolean cancel = false;
        View focusView = null;

        ValidateUserInfo validate = new ValidateUserInfo();

        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            edit_name.setError(getString(R.string.error_field_required));
            focusView = edit_name;
            cancel = true;
        } else if (TextUtils.isEmpty(email)) {
            edit_email.setError(getString(R.string.error_field_required));
            focusView = edit_email;
            cancel = true;
        } else if (!validate.isEmailValid(email)) {
            edit_email.setError(getString(R.string.error_invalid_email));
            focusView = edit_email;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            edit_password.setError(getString(R.string.error_field_required));
            focusView = edit_password;
            cancel = true;
        } else if (!validate.isPasswordValid(password)) {
            edit_password.setError(getString(R.string.error_invalid_password));
            focusView = edit_password;
            cancel = true;
        } else if (TextUtils.isEmpty(phone)) {
            edit_phone.setError(getString(R.string.error_field_required));
            focusView = edit_phone;
            cancel = true;
        } else if (TextUtils.isEmpty(dob)) {
            edit_dob.setError(getString(R.string.error_field_required));
            focusView = edit_dob;
            cancel = true;
        } else if (TextUtils.isEmpty(city)) {
            edit_city.setError(getString(R.string.error_field_required));
            focusView = edit_city;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //TODO Create account logic
            // Show a progress spinner, and kick off a background task to
            // perform the user registration attempt.
            //showProgress(true);
            registerUser();
        }
    }

    private void registerUser() {
        Intent intent   = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registerRegister:
                attemptCreate();
                break;

            case R.id.tv_already_have:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;

            case R.id.tv_dobRegister:
                mDatePickerDialog.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
