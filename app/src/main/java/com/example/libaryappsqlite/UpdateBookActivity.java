package com.example.libaryappsqlite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBookActivity extends AppCompatActivity {

    EditText book_name_input,book_author_input,book_price_input,book_desc_input;
    Button update_button,delete_button;
    String id,name,author,price,desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        book_name_input = findViewById(R.id.book_name_2);
        book_author_input = findViewById(R.id.book_author_2);
        book_price_input = findViewById(R.id.book_price_2);
        book_desc_input = findViewById(R.id.book_desc_2);
        update_button = findViewById(R.id.update_book);
        delete_button = findViewById(R.id.delete_book);
        getBooksData();

        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setTitle(name);
        }
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(UpdateBookActivity.this);
                name = book_name_input.getText().toString();
                author = book_author_input.getText().toString();
                price = book_price_input.getText().toString();
                desc = book_desc_input.getText().toString();
                db.updateBook(id,name,author,price,desc);
                Intent i = new Intent(UpdateBookActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

    }

    void getBooksData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("author") && getIntent().hasExtra("price") && getIntent().hasExtra("description")){
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            author = getIntent().getStringExtra("author");
            price = getIntent().getStringExtra("price");
            desc = getIntent().getStringExtra("description");

            book_name_input.setText(name);
            book_author_input.setText(author);
            book_price_input.setText(price);
            book_desc_input.setText(desc);
            Log.d("stev",name+" "+author+" "+price+" "+desc);

        }else{
            Toast.makeText(UpdateBookActivity.this,"No data",Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ name +" ?");
        builder.setMessage("Are you sure you want to delete this book?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper db = new DatabaseHelper(UpdateBookActivity.this);
                db.deleteBook(id);
                Intent i = new Intent(UpdateBookActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }


}