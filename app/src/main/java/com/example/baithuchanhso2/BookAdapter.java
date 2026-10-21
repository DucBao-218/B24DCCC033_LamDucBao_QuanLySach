package com.example.baithuchanhso2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    private ArrayList<Book> bookList;
    private final OnBookClickListener listener;

    public BookAdapter(ArrayList<Book> bookList, OnBookClickListener listener) {
        this.bookList = bookList;
        this.listener = listener;
    }

    public void updateData(ArrayList<Book> newBookList) {
        this.bookList = newBookList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        Context context = holder.itemView.getContext();

        holder.tvBookTitle.setText(book.getTitle());
        holder.tvBookAuthor.setText("Tác giả: " + book.getAuthor());
        holder.tvCategoryTag.setText(book.getCategory());

        // Set status text, color, and background drawable
        String status = book.getStatus();
        holder.tvStatusTag.setText(status);
        if ("Đang đọc".equals(status)) {
            holder.tvStatusTag.setText("Đang đọc");
            holder.tvStatusTag.setTextColor(ContextCompat.getColor(context, R.color.orange_status));
            holder.tvStatusTag.setBackgroundResource(R.drawable.bg_tag_status_reading);
        } else if ("Đã đọc".equals(status)) {
            holder.tvStatusTag.setText("Đã đọc");
            holder.tvStatusTag.setTextColor(ContextCompat.getColor(context, R.color.green_read_status));
            holder.tvStatusTag.setBackgroundResource(R.drawable.bg_tag_status_read);
        } else {
            holder.tvStatusTag.setText("Chưa đọc");
            holder.tvStatusTag.setTextColor(ContextCompat.getColor(context, R.color.gray_status));
            holder.tvStatusTag.setBackgroundResource(R.drawable.bg_tag_status_not_read);
        }

        holder.rbItemRating.setRating(book.getRating());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBookClick(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList != null ? bookList.size() : 0;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookTitle;
        TextView tvBookAuthor;
        TextView tvCategoryTag;
        TextView tvStatusTag;
        RatingBar rbItemRating;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
            tvCategoryTag = itemView.findViewById(R.id.tvCategoryTag);
            tvStatusTag = itemView.findViewById(R.id.tvStatusTag);
            rbItemRating = itemView.findViewById(R.id.rbItemRating);
        }
    }
}
