package org.hse;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface BookLibrary {
    /**
     * Сохранить данную книгу в библиотеке.
     * <p>
     * Если {@link Book#id()} возвращает {@code null} у параметра, то эта книга прежде не была сохранена в библиотеке.
     * Тогда нужно запомнить книгу и вернуть {@code new Book(...)}, у которого будет проставлен {@code id}.
     * <p>
     * Иначе - обновить информацию о книге в библиотеке.
     * Например, мы могли поменять название у книги
     *
     * @param book книга для сохранения
     * @return сохранённая книга
     */
    Book saveOne(Book book);

    /**
     * Найти все книги, которые сохранены в библиотеке.
     * <p>
     * Возвращает неизменяемое множество
     *
     * @return множество всех книг в библиотеке
     */
    Set<Book> findAll();

    /**
     * Найти книгу по {@code id}
     *
     * @param id идентификатор книги
     * @return {@link Optional} с книгой, если книга с таким {@code id} существует, иначе - {@link Optional#empty()}
     */
    Optional<Book> findById(long id);

    /**
     * Найти все книги автора
     *
     * @param author для поиска
     * @return список всех книг автора, отсортированный по названию книги
     */
    List<Book> findByAuthorSortedByTitle(String author);


    /**
     * Найти все книги, у которых название содержит {@code titleSubstring}
     *
     * @param titleSubstring подстрока для поиска
     * @return множество найденных книг
     */
    Set<Book> findByTitleContaining(String titleSubstring);

    /**
     * Найти все книги по авторам.
     * <p>
     * Вернуть {@link Map} из {@link Book#author()} во множество книг данного автора.
     * <p>
     * Пример:
     * <p>
     * "Joshua Bloch" -> ["Effective Java", "Java puzzlers"]
     * "Дарья Донцова" -> [бесконечный список из книг]
     *
     * @return {@link Map} книг по авторам
     */
    Map<String, Set<Book>> findBookByAuthor();

    /**
     * Удалить книгу по идентификатору
     *
     * @param id идентификатор для удаления
     */
    void deleteById(long id);

    /**
     * Удалить все книги из библиотеки
     */
    void deleteAll();

    /**
     * Получить количество книг сохранённых в библиотеке
     *
     * @return количество книг
     */
    long count();
}

