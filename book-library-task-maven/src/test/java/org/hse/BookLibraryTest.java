package org.hse;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookLibraryTest {
    @Test
    void noBooksInitially() {
        BookLibrary bookLibrary = new BookLibraryClass();

        assertEquals(0, bookLibrary.count());
    }

    @Test
    void bookSaved() {
        BookLibrary bookLibrary = new BookLibraryClass();

        Book newBook = bookLibrary.saveOne(new Book(null, "Effective Modern C++", "Scott Meyers"));

        assertNotNull(newBook.id());
    }

    @Test
    void findAllTest() {
        BookLibrary bookLibrary = new BookLibraryClass();

        Book newBook1 = bookLibrary.saveOne(new Book(null, "Effective Modern C++", "Scott Meyers"));
        Book newBook2 = bookLibrary.saveOne(new Book(null, "книга1", "автор1"));
        Book newBook3 = bookLibrary.saveOne(new Book(null, "книга2", "автор3"));
        Book newBook4 = bookLibrary.saveOne(new Book(null, "книга3", "автор4"));

        assertEquals(4, bookLibrary.count());
        assertNotNull(bookLibrary.findById(newBook1.id()));
        assertNotNull(bookLibrary.findById(newBook2.id()));
        assertNotNull(bookLibrary.findById(newBook3.id()));
        assertNotNull(bookLibrary.findById(newBook4.id()));
    }

    @Test
    void findByIdTest() {
        BookLibrary bookLibrary = new BookLibraryClass();
        Book newBook1 = bookLibrary.saveOne(new Book(null, "Effective Modern C++", "Scott Meyers"));

        assertEquals(Optional.of(newBook1), bookLibrary.findById(newBook1.id()));
    }

    @Test
    void findByAuthorSortedByTitleTest() {
        BookLibrary bookLibrary = new BookLibraryClass();
        Book newBook7 = bookLibrary.saveOne(new Book(null, "книга7", "автор4"));
        Book newBook2 = bookLibrary.saveOne(new Book(null, "книга2_1", "автор1"));
        Book newBook1 = bookLibrary.saveOne(new Book(null, "книга1_1", "автор1"));
        Book newBook3 = bookLibrary.saveOne(new Book(null, "книга3_3", "автор3"));
        Book newBook4 = bookLibrary.saveOne(new Book(null, "книга4", "автор4"));
        Book newBook5 = bookLibrary.saveOne(new Book(null, "книга5", "автор1"));
        Book newBook6 = bookLibrary.saveOne(new Book(null, "книга6", "автор3"));

        List<Book> bookAuthor1 = new ArrayList<>();
        bookAuthor1.add(newBook1);
        bookAuthor1.add(newBook2);
        bookAuthor1.add(newBook5);

        assertEquals(bookAuthor1, bookLibrary.findByAuthorSortedByTitle("автор1"));

        List<Book> bookAuthor3 = new ArrayList<>();
        bookAuthor3.add(newBook3);
        bookAuthor3.add(newBook6);

        assertEquals(bookAuthor3, bookLibrary.findByAuthorSortedByTitle("автор3"));

        List<Book> bookAuthor4 = new ArrayList<>();
        bookAuthor4.add(newBook4);
        bookAuthor4.add(newBook7);

        assertEquals(bookAuthor4, bookLibrary.findByAuthorSortedByTitle("автор4"));
    }

    @Test
    void findByTitleContainingTest() {
        BookLibrary bookLibrary = new BookLibraryClass();
        Book newBook7 = bookLibrary.saveOne(new Book(null, "книга7", "автор4"));
        Book newBook2 = bookLibrary.saveOne(new Book(null, "книга2_1", "автор1"));
        Book newBook1 = bookLibrary.saveOne(new Book(null, "книга1_1", "автор1"));
        Book newBook3 = bookLibrary.saveOne(new Book(null, "книга3", "автор3"));
        Book newBook4 = bookLibrary.saveOne(new Book(null, "книга4", "автор4"));
        Book newBook5 = bookLibrary.saveOne(new Book(null, "книга5", "автор1"));
        Book newBook6 = bookLibrary.saveOne(new Book(null, "книга6", "автор3"));

        Set<Book> bookTitle1 = new HashSet<>();
        bookTitle1.add(newBook1);
        bookTitle1.add(newBook2);
        bookTitle1.add(newBook3);
        bookTitle1.add(newBook4);
        bookTitle1.add(newBook5);
        bookTitle1.add(newBook6);
        bookTitle1.add(newBook7);

        assertEquals(bookTitle1, bookLibrary.findByTitleContaining("книга"));

        Set<Book> bookTitle2 = new HashSet<>();
        bookTitle2.add(newBook1);
        bookTitle2.add(newBook2);

        assertEquals(bookTitle2, bookLibrary.findByTitleContaining("_1"));

        Set<Book> bookTitle3 = new HashSet<>();
        bookTitle3.add(newBook3);

        assertEquals(bookTitle3, bookLibrary.findByTitleContaining("3"));

        Set<Book> bookTitle4 = new HashSet<>();

        assertEquals(bookTitle4, bookLibrary.findByTitleContaining("8"));
    }

    @Test
    void findBookByAuthorTest() {
        BookLibrary bookLibrary = new BookLibraryClass();
        Book newBook7 = bookLibrary.saveOne(new Book(null, "книга7", "автор4"));
        Book newBook2 = bookLibrary.saveOne(new Book(null, "книга2_1", "автор1"));
        Book newBook1 = bookLibrary.saveOne(new Book(null, "книга1_1", "автор1"));
        Book newBook3 = bookLibrary.saveOne(new Book(null, "книга3", "автор3"));
        Book newBook4 = bookLibrary.saveOne(new Book(null, "книга4", "автор4"));
        Book newBook5 = bookLibrary.saveOne(new Book(null, "книга5", "автор1"));
        Book newBook6 = bookLibrary.saveOne(new Book(null, "книга6", "автор3"));

        Map<String, Set<Book>> bookAuthor = new HashMap<>();

        Set<Book> bookTitle1 = new HashSet<>();
        bookTitle1.add(newBook2);
        bookTitle1.add(newBook1);
        bookTitle1.add(newBook5);
        bookAuthor.put("автор1", bookTitle1);

        Set<Book> bookTitle3 = new HashSet<>();
        bookTitle3.add(newBook3);
        bookTitle3.add(newBook6);
        bookAuthor.put("автор3", bookTitle3);

        Set<Book> bookTitle4 = new HashSet<>();
        bookTitle4.add(newBook4);
        bookTitle4.add(newBook7);
        bookAuthor.put("автор4", bookTitle4);

        assertEquals(bookAuthor, bookLibrary.findBookByAuthor());
    }

    @Test
    void deleteByIdTest() {
        BookLibrary bookLibrary = new BookLibraryClass();
        Book newBook1 = bookLibrary.saveOne(new Book(null, "книга1_1", "автор1"));
        bookLibrary.deleteById(newBook1.id());
        assertEquals(0, bookLibrary.count());

        Book newBook3 = bookLibrary.saveOne(new Book(null, "книга3", "автор3"));
        Book newBook4 = bookLibrary.saveOne(new Book(null, "книга4", "автор4"));
        Book newBook5 = bookLibrary.saveOne(new Book(null, "книга5", "автор1"));
        bookLibrary.deleteById(newBook3.id());
        assertEquals(2, bookLibrary.count());
    }

    @Test
    void deleteAllTest() {
        BookLibrary bookLibrary = new BookLibraryClass();
        Book newBook1 = bookLibrary.saveOne(new Book(null, "книга1_1", "автор1"));
        bookLibrary.deleteAll();
        assertEquals(0, bookLibrary.count());

        Book newBook3 = bookLibrary.saveOne(new Book(null, "книга3", "автор3"));
        Book newBook4 = bookLibrary.saveOne(new Book(null, "книга4", "автор4"));
        Book newBook5 = bookLibrary.saveOne(new Book(null, "книга5", "автор1"));
        bookLibrary.deleteAll();
        assertEquals(0, bookLibrary.count());
    }
}