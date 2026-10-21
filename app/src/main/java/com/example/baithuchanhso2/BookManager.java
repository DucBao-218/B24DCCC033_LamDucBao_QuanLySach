package com.example.baithuchanhso2;

import java.util.ArrayList;

public class BookManager {
    private static BookManager instance;
    private final ArrayList<Book> bookList;
    private int nextId = 1;

    private BookManager() {
        bookList = new ArrayList<>();
        initSampleData();
    }

    public static synchronized BookManager getInstance() {
        if (instance == null) {
            instance = new BookManager();
        }
        return instance;
    }

    private void initSampleData() {
        addBookInternal(new Book(nextId++, "Lập trình Android cơ bản", "Nguyễn Văn A", "Công nghệ", "Đang đọc", 4.0f, "Sách dành cho người mới bắt đầu"));
        addBookInternal(new Book(nextId++, "Đắc nhân tâm", "Dale Carnegie", "Kỹ năng sống", "Đã đọc", 5.0f, "Cuốn sách rất hay về kỹ năng sống"));
        addBookInternal(new Book(nextId++, "Dế Mèn phiêu lưu ký", "Tô Hoài", "Văn học", "Chưa đọc", 0.0f, "Truyện thiếu nhi kinh điển"));
    }

    private void addBookInternal(Book book) {
        bookList.add(book);
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void addBook(String title, String author, String category, String status, float rating, String notes) {
        Book newBook = new Book(nextId++, title, author, category, status, rating, notes);
        bookList.add(newBook);
    }

    public Book getBookById(int id) {
        for (Book book : bookList) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public boolean updateBookStatus(int id, String newStatus) {
        Book book = getBookById(id);
        if (book != null) {
            book.setStatus(newStatus);
            return true;
        }
        return false;
    }

    public boolean updateBookRating(int id, float newRating) {
        Book book = getBookById(id);
        if (book != null) {
            book.setRating(newRating);
            return true;
        }
        return false;
    }

    public boolean deleteBook(int id) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getId() == id) {
                bookList.remove(i);
                return true;
            }
        }
        return false;
    }

    public int getTotalCount() {
        return bookList.size();
    }

    public int getReadCount() {
        int count = 0;
        for (Book book : bookList) {
            if ("Đã đọc".equals(book.getStatus())) {
                count++;
            }
        }
        return count;
    }

    public int getUnreadCount() {
        int count = 0;
        for (Book book : bookList) {
            if ("Chưa đọc".equals(book.getStatus()) || "Đang đọc".equals(book.getStatus())) {
                count++;
            }
        }
        return count;
    }
}
