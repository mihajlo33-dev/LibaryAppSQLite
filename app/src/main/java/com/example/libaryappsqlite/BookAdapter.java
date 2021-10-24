package com.example.libaryappsqlite;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private Context context;
    private ArrayList book_id,book_name,book_author,book_price,book_desc;
    Activity activity;



    BookAdapter(Activity activity,
                Context context,
                ArrayList book_id,
                ArrayList book_name,
                ArrayList book_author,
                ArrayList book_price,
                ArrayList book_desc){
        this.activity = activity;
        this.context = context;
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_author = book_author;
        this.book_price = book_price;
        this.book_desc = book_desc;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row,parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.book_id_txt.setText(String.valueOf(book_id.get(position)));
        holder.book_name_txt.setText(String.valueOf(book_name.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        holder.book_price_txt.setText(String.valueOf(book_price.get(position)) + "$");
        holder.book_desc_txt.setText(String.valueOf(book_desc.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,UpdateBookActivity.class);
                i.putExtra("id",String.valueOf(book_id.get(position)));
                i.putExtra("name",String.valueOf(book_name.get(position)));
                i.putExtra("author",String.valueOf(book_author.get(position)));
                i.putExtra("price",String.valueOf(book_price.get(position)));
                i.putExtra("description",String.valueOf(book_desc.get(position)));

                activity.startActivityForResult(i,1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView book_id_txt,book_name_txt,book_author_txt,book_price_txt,book_desc_txt;
        CardView mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.book_id_txt);
            book_name_txt = itemView.findViewById(R.id.book_name_txt);
            book_author_txt = itemView.findViewById(R.id.book_author_txt);
            book_price_txt = itemView.findViewById(R.id.book_price_txt);
            book_desc_txt = itemView.findViewById(R.id.book_desc_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
