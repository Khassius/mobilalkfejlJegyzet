package com.example.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = RegisterActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 112;

    EditText userEmailEditText;
    EditText userPasswordEditText;
    EditText userPasswordConfirmEditText;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        userEmailEditText = findViewById(R.id.registerUserEmailEditText);
        userPasswordEditText = findViewById(R.id.registerUserPasswordEditText);
        userPasswordConfirmEditText = findViewById(R.id.registerUserConfirmPasswordEditText);

//        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
//        String userEmail = preferences.getString("userEmail", "");
//        String userPassword = preferences.getString("userPassword", "");
//
//        userEmailEditText.setText(userEmail);
//        userPasswordEditText.setText(userPassword);
//        userPasswordConfirmEditText.setText(userPassword);

    }

    public void register(View view){
        String email = userEmailEditText.getText().toString();
        String password = userPasswordEditText.getText().toString();
        String passwordConfirm = userPasswordConfirmEditText.getText().toString();

        if(!email.equals("") && !password.equals("") && !passwordConfirm.equals("") ) {
            if (!password.equals(passwordConfirm)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                return;
            }

            if(password.length() < 6){
                Toast.makeText(RegisterActivity.this, "Password needs to be at least 6 characters long", Toast.LENGTH_LONG).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startNotes();
                    } else {
                        Toast.makeText(RegisterActivity.this, "User creation failed:", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void startNotes(){
        Intent intent = new Intent(this, NotesActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }
}
