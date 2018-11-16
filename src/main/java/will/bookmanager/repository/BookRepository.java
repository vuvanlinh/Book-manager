package will.bookmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import will.bookmanager.model.Book;

public interface BookRepository extends PagingAndSortingRepository<Book,Long> {
    Page<Book> findAllByNameContaining(String name, Pageable pageable);
}
