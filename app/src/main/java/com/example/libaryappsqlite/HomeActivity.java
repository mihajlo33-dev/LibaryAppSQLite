package com.example.libaryappsqlite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    DatabaseHelper databaseHelper;
    ArrayList<String> book_id, book_name, book_author, book_price, book_desc;
    BookAdapter bookAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_btn);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Libary");
        }

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, AddBookActivity.class);
                startActivity(i);
            }
        });

        databaseHelper = new DatabaseHelper(HomeActivity.this);
        book_id = new ArrayList<>();
        book_name = new ArrayList<>();
        book_author = new ArrayList<>();
        book_price = new ArrayList<>();
        book_desc = new ArrayList<>();

        displayBooks();

        bookAdapter = new BookAdapter(HomeActivity.this, this, book_id, book_name, book_author, book_price, book_desc);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void displayBooks() {
        Cursor cursor = databaseHelper.getBooks();
        if (cursor.getCount() == 0) {
            Toast.makeText(HomeActivity.this, "No books!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                book_id.add(cursor.getString(0));
                book_name.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_price.add(cursor.getString(3));
                book_desc.add(cursor.getString(4));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.idLogOut:
                Toast.makeText(this, "User Logged Out!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
                this.finish();
                return true;
            case R.id.delete_all:
                confirmDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all?");
        builder.setMessage("Are you sure you want to delete all books?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper db = new DatabaseHelper(HomeActivity.this);
                db.deleteAll();
                Intent i = new Intent(HomeActivity.this,HomeActivity.class);
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