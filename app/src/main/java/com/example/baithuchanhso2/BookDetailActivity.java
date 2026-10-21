package com.example.baithuchanhso2;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.baithuchanhso2.databinding.ActivityBookDetailBinding;

public class BookDetailActivity extends AppCompatActivity {

    private ActivityBookDetailBinding binding;
    private int bookId = -1;
    private Book currentBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bookId = getIntent().getIntExtra(MainActivity.EXTRA_BOOK_ID, -1);
        currentBook = BookManager.getInstance().getBookById(bookId);

        if (currentBook == null) {
            Toast.makeText(this, "Không tìm thấy thông tin sách!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupUI();
        setupListeners();
    }

    private void setupUI() {
        binding.tvDetailTitle.setText(currentBook.getTitle());
        binding.tvDetailAuthorCategory.setText(currentBook.getAuthor() + "  ·  " + currentBook.getCategory());

        updateStatusTag(currentBook.getStatus());

        float rating = currentBook.getRating();
        binding.rbDetailRating.setRating(rating);
        binding.tvDetailRatingValue.setText(Math.round(rating) + " / 5 sao");

        String notes = currentBook.getNotes();
        if (notes == null || notes.trim().isEmpty()) {
            binding.tvDetailNotes.setText(R.string.no_notes);
            binding.tvDetailNotes.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
        } else {
            binding.tvDetailNotes.setText(notes);
            binding.tvDetailNotes.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        }
    }

    private void updateStatusTag(String status) {
        binding.tvDetailStatusTag.setText(status);
        if ("Đã đọc".equals(status)) {
            binding.tvDetailStatusTag.setTextColor(ContextCompat.getColor(this, R.color.green_read_status));
            binding.tvDetailStatusTag.setBackgroundResource(R.drawable.bg_tag_status_read);
            // Hide or disable "Đánh dấu đã đọc" button when status is already "Đã đọc"
            binding.btnMarkRead.setVisibility(View.GONE);
        } else if ("Đang đọc".equals(status)) {
            binding.tvDetailStatusTag.setTextColor(ContextCompat.getColor(this, R.color.orange_status));
            binding.tvDetailStatusTag.setBackgroundResource(R.drawable.bg_tag_status_reading);
            binding.btnMarkRead.setVisibility(View.VISIBLE);
        } else {
            binding.tvDetailStatusTag.setTextColor(ContextCompat.getColor(this, R.color.gray_status));
            binding.tvDetailStatusTag.setBackgroundResource(R.drawable.bg_tag_status_not_read);
            binding.btnMarkRead.setVisibility(View.VISIBLE);
        }
    }

    private void setupListeners() {
        binding.btnBackHeader.setOnClickListener(v -> finish());
        binding.btnBack.setOnClickListener(v -> finish());

        // Change Rating listener
        binding.rbDetailRating.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                int stars = Math.round(rating);
                BookManager.getInstance().updateBookRating(bookId, stars);
                currentBook.setRating(stars);
                binding.tvDetailRatingValue.setText(stars + " / 5 sao");
                Toast.makeText(this, "Đã cập nhật đánh giá", Toast.LENGTH_SHORT).show();
            }
        });

        // Mark as Read button
        binding.btnMarkRead.setOnClickListener(v -> {
            BookManager.getInstance().updateBookStatus(bookId, "Đã đọc");
            currentBook.setStatus("Đã đọc");
            updateStatusTag("Đã đọc");
            Toast.makeText(this, "Đã đánh dấu là đã đọc", Toast.LENGTH_SHORT).show();
        });

        // Delete button
        binding.btnDeleteBook.setOnClickListener(v -> {
            BookManager.getInstance().deleteBook(bookId);
            Toast.makeText(this, "Đã xóa sách", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
