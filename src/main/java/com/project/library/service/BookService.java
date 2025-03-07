package com.project.library.service;

import com.project.library.model.Book;
import com.project.library.repository.BookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public void addNewBook(Book book) {
        bookRepository.save(book);
    }
    
    public Book getBook(int id) {
        return bookRepository.findById(id).get();
    }
    
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}
