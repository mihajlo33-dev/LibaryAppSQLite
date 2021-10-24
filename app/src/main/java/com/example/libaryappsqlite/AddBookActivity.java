package com.example.libaryappsqlite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddBookActivity extends AppCompatActivity {

    EditText bookName,bookAuthor,bookPrice,bookDesc;
    Button addBtn;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        bookName = findViewById(R.id.book_name);
        bookAuthor = findViewById(R.id.book_author);
        bookPrice = findViewById(R.id.book_price);
        bookDesc = findViewById(R.id.book_desc);
        addBtn = findViewById(R.id.add_book);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setTitle("Libary");
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(AddBookActivity.this);

                String name = bookName.getText().toString();
                String author = bookAuthor.getText().toString();
                String price = bookPrice.getText().toString();
                String desc = bookDesc.getText().toString();

                db.addBook(name,author,price,desc);

                Intent i = new Intent(AddBookActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });

    }
}