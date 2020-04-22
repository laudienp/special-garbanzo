package com.example.birdstagram.inscription;

import com.example.birdstagram.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    private EditText pseudo;
    private EditText email;
    private EditText password;
    private EditText checkPassword;
    private Button signupButton;

    private TextView pseudoLabel;
    private TextView emailLabel;
    private TextView passwordLabel;
    private TextView checkPasswprdLabel;

    public SignUpActivity() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeAttributes();
        setAllOnFocusListener();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Drawable boarder = getDrawable(R.drawable.boarder);
        Drawable square = getDrawable(R.drawable.square);
        switch (v.getId()) {
            case R.id.pseudo:
                if (hasFocus) {
                    pseudoLabel.setVisibility(View.VISIBLE);
                    pseudo.setBackground(boarder);
                    pseudo.setHint(" ");
                } else  if (pseudo.getText().length() != 0) {
                    pseudo.setBackground(square);
                } else {
                    pseudoLabel.setVisibility(View.INVISIBLE);
                    pseudo.setBackground(square);
                    pseudo.setHint("Pseudo");
                }
                break;
            case R.id.email:
                if (hasFocus) {
                    emailLabel.setVisibility(View.VISIBLE);
                    email.setBackground(boarder);
                    email.setHint(" ");
                } else if (email.getText().length() != 0) {
                    email.setBackground(square);
                } else {
                    emailLabel.setVisibility(View.INVISIBLE);
                    email.setBackground(square);
                    email.setHint("Email");

                }

                break;
            case R.id.password:
                if (hasFocus) {
                    passwordLabel.setVisibility(View.VISIBLE);
                    password.setBackground(boarder);
                    password.setHint(" ");

                } else if (password.getText().length() != 0) {
                    password.setBackground(square);

                } else {
                    passwordLabel.setVisibility(View.INVISIBLE);
                    password.setBackground(square);
                    password.setHint("Mot de passe");

                }

                break;
            case R.id.confirmPassword:
                if (hasFocus) {
                    checkPasswprdLabel.setVisibility(View.VISIBLE);
                    checkPassword.setBackground(boarder);
                    checkPassword.setHint(" ");

                } else if (checkPassword.getText().length() != 0) {
                    checkPassword.setBackground(square);
                } else {
                    checkPasswprdLabel.setVisibility(View.INVISIBLE);
                    checkPassword.setBackground(square);
                    checkPassword.setHint("Cofirmer le mot de passe");

                }
                break;
        }
        if (v.getId() == R.id.signup && hasFocus){
            InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    void initializeAttributes() {
        pseudo = findViewById(R.id.pseudo);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        checkPassword = findViewById(R.id.confirmPassword);
        signupButton = findViewById(R.id.signUpButton);

        pseudoLabel = findViewById(R.id.pseudo_textView);
        emailLabel = findViewById(R.id.email_textView);
        passwordLabel = findViewById(R.id.password_textView);
        checkPasswprdLabel = findViewById(R.id.confirmPassword_textView);

        makeLabelsInvisible();
    }

    void makeLabelsInvisible() {
        pseudoLabel.setVisibility(View.INVISIBLE);
        emailLabel.setVisibility(View.INVISIBLE);
        passwordLabel.setVisibility(View.INVISIBLE);
        checkPasswprdLabel.setVisibility(View.INVISIBLE);
    }

    void setAllOnFocusListener(){
        pseudo.setOnFocusChangeListener(this);
        email.setOnFocusChangeListener(this);
        password.setOnFocusChangeListener(this);
        checkPassword.setOnFocusChangeListener(this);
        findViewById(R.id.signup).setOnFocusChangeListener(this);
    }

}
