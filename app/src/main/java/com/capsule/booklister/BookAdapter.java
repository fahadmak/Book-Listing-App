package com.capsule.booklister;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {

    List<Book> books;

    public BookAdapter(List<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder bookHolder, int position) {
        Book book = books.get(position);
        View itemView = bookHolder.itemView;
        TextView bookTitle = itemView.findViewById(R.id.book_title);
        bookTitle.setText(book.getTitle());

        ImageView thumbnail = itemView.findViewById(R.id.thumb_nail);
        Picasso.get().load(book.getImageUrl()).into(thumbnail);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder {
        public BookHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
