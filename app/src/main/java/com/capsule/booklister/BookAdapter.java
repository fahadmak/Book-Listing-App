package com.capsule.booklister;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {

    List<Book> books;
    Context mContext;


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
        Book book = books.get(bookHolder.getAdapterPosition());
        View itemView = bookHolder.itemView;

        TextView bookTitle = itemView.findViewById(R.id.book_title);
        bookTitle.setText(book.getTitle());

        TextView bookAuthor = itemView.findViewById(R.id.author);
        bookAuthor.setText(book.getAuthor());

        ImageView imageView = itemView.findViewById(R.id.thumb_nail);
        String url = addChar(book.getImageUrl(), 's', 4);
        Glide.with(itemView.getContext()).load(url).into(imageView);
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

    private String addChar(String str, char ch, int position) {
        StringBuilder sb = new StringBuilder(str);
        sb.insert(position, ch);
        return sb.toString();
    }
}
