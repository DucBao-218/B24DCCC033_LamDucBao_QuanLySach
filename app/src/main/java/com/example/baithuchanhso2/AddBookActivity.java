package com.example.baithuchanhso2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baithuchanhso2.databinding.ActivityAddBookBinding;

public class AddBookActivity extends AppCompatActivity {

    private ActivityAddBookBinding binding;
    private final String[] categories = {"Văn học", "Khoa học", "Công nghệ", "Kỹ năng sống", "Khác"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackHeader.setOnClickListener(v -> finish());

        // Setup Category Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spCategory.setAdapter(spinnerAdapter);

        // RatingBar listener
        binding.rbRating.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            int stars = Math.round(rating);
            String ratingText = stars + " / 5 sao";
            binding.tvRatingValue.setText(ratingText);
        });

        // Save Book button
        binding.btnSaveBook.setOnClickListener(v -> saveBook());
    }

    private void saveBook() {
        String title = binding.etBookTitle.getText().toString().trim();
        String author = binding.etBookAuthor.getText().toString().trim();
        String category = binding.spCategory.getSelectedItem() != null ?
                binding.spCategory.getSelectedItem().toString() : categories[0];

        if (title.isEmpty()) {
            binding.etBookTitle.setError(getString(R.string.error_empty_title));
            binding.etBookTitle.requestFocus();
            Toast.makeText(this, R.string.error_empty_title, Toast.LENGTH_SHORT).show();
            return;
        }

        if (author.isEmpty()) {
            binding.etBookAuthor.setError(getString(R.string.error_empty_author));
            binding.etBookAuthor.requestFocus();
            Toast.makeText(this, R.string.error_empty_author, Toast.LENGTH_SHORT).show();
            return;
        }

        String status = "Chưa đọc";
        if (binding.rbRead.isChecked()) {
            status = "Đã đọc";
        } else if (binding.rbReading.isChecked()) {
            status = "Đang đọc";
        }

        float rating = binding.rbRating.getRating();
        String notes = binding.etNotes.getText().toString().trim();

        BookManager.getInstance().addBook(title, author, category, status, rating, notes);
        Toast.makeText(this, "Thêm sách thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
