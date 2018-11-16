package will.bookmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import will.bookmanager.model.Book;

public interface BookService {
    Page<Book> findAll(Pageable pageable);

    Book findById(Long id);

    void save(Book book);

    void remove(Long id);

    Page<Book> findAllByNameContaining(String name, Pageable pageable);

}
