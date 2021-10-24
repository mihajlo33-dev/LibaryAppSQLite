package com.example.libaryappsqlite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username,password,cnf_password;
    Button login,register;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        databaseHelper = new DatabaseHelper(this);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setTitle("Login");
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
                    Toast.makeText(MainActivity.this,"Please enter your credentials!",Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkNamePass = databaseHelper.checkUsernamePassword(user,pass);
                    if(checkNamePass){
                        Toast.makeText(MainActivity.this,"You're logged!",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(MainActivity.this,"You're not logged! Please make sure you have an account!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}