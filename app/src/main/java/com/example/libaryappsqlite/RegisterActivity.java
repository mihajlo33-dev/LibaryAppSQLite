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

public class RegisterActivity extends AppCompatActivity {

    EditText username,password,cnf_password;
    Button login,register;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        cnf_password = findViewById(R.id.cnf_password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        databaseHelper = new DatabaseHelper(this);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setTitle("Register");
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String pass = password.getText().toString();
                String cnfPwd = cnf_password.getText().toString();

                if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(cnfPwd)){
                    Toast.makeText(RegisterActivity.this,"Please enter your credentials!",Toast.LENGTH_SHORT).show();
                }else{
                    if(pass.equals(cnfPwd)){
                        Boolean checkUserName = databaseHelper.checkUsername(userName);
                        if(checkUserName == false){
                            Boolean insert = databaseHelper.addUser(userName,pass);
                            if(insert == true){
                                Toast.makeText(RegisterActivity.this, "You're registered!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(RegisterActivity.this,"You're not registred!",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this,"User already exists in database!",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"Your passwords aren't matching!",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }
}