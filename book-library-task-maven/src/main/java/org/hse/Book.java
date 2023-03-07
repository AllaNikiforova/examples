package org.hse;

public record Book(
        Long id,
        String title,
        String author
) {
    public Book {
        if (title == null) {
            throw new IllegalArgumentException("title == null");
        }
        if (author == null) {
            throw new IllegalArgumentException("author == null");
        }
    }
}
