package com.example.baithuchanhso2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.baithuchanhso2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK_ID = "EXTRA_BOOK_ID";

    private ActivityMainBinding binding;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvBooks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(BookManager.getInstance().getBookList(), book -> {
            Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
            intent.putExtra(EXTRA_BOOK_ID, book.getId());
            startActivity(intent);
        });
        binding.rvBooks.setAdapter(adapter);

        binding.btnAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
        });

        refreshData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        BookManager manager = BookManager.getInstance();
        binding.tvStatTotal.setText(String.valueOf(manager.getTotalCount()));
        binding.tvStatRead.setText(String.valueOf(manager.getReadCount()));
        binding.tvStatUnread.setText(String.valueOf(manager.getUnreadCount()));

        if (adapter != null) {
            adapter.updateData(manager.getBookList());
        }
    }
}
