package org.hse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class BookLibraryClass implements BookLibrary {
    private final Map<Long, Book> library = new HashMap<>();
    private int counter = 0;


    @Override
    public Book saveOne(Book book) {
        if (book.id() == null) {
            Book bookForSaving = new Book((long) ++counter, book.title(),
                    book.author());
            library.put(bookForSaving.id(), bookForSaving);
            return bookForSaving;
        } else {
            library.replace(book.id(), library.get(book.id()), book);
            return library.get(book.id());
        }
    }

    @Override
    public Set<Book> findAll() {
        Set<Book> bookSet = new HashSet<>(library.values());
        bookSet = Collections.unmodifiableSet(bookSet);
        // TODO: 2/5/23 Можно так: return Set.copyOf(library.values());
        return bookSet;
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(library.get(id));
    }

    @Override
    public List<Book> findByAuthorSortedByTitle(String author) {
        TreeSet<Book> bookSet = new TreeSet<>((b1, b2) -> b1.title().compareTo(b2.title()));

        for (var b : library.entrySet()) {
            if (b.getValue().author().equals(author)) {
                bookSet.add(b.getValue());
            }
        }

        List<Book> bookList = new ArrayList<>(bookSet);
        return bookList;
    }

    @Override
    public Set<Book> findByTitleContaining(String titleSubstring) {
        Set<Book> bookSet = new HashSet<>();
        for (var b : library.entrySet()) {
            if (b.getValue().title().contains(titleSubstring)) {
                bookSet.add(b.getValue());
            }
        }
        return bookSet;
    }

    @Override
    public Map<String, Set<Book>> findBookByAuthor() {
        Map<String, Set<Book>> booksByAuthor = new HashMap<>();
        for (var b : library.entrySet()) {
            if (booksByAuthor.containsKey(b.getValue().author())) {
                booksByAuthor.get(b.getValue().author()).add(b.getValue());
            } else {
                Set<Book> bookSetForMap = new HashSet<>();
                bookSetForMap.add(b.getValue());
                booksByAuthor.put(b.getValue().author(), bookSetForMap);
            }
        }
        return booksByAuthor;
    }

    @Override
    public void deleteById(long id) {
        library.keySet().removeIf(key -> key == id);
    }

    @Override
    public void deleteAll() {
        // TODO: 2/5/23 library.clear()
        library.keySet().removeIf(key -> key > 0);
    }

    @Override
    public long count() {
        return library.size();
    }
}

