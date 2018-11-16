package will.bookmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import will.bookmanager.model.Book;
import will.bookmanager.service.BookService;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public ModelAndView showListBook(@RequestParam("search") Optional<String> search, @PageableDefault(size = 10) Pageable pageable) {
        Page<Book> books;
        if (search.isPresent()) {
            books = bookService.findAllByNameContaining(search.get(), pageable);
        } else {
            books = bookService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("book", new Book());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createNewBook(@ModelAttribute("book") Book book) {
        ModelAndView modelAndView = new ModelAndView("create");
        bookService.save(book);
        modelAndView.addObject("message", "New book created successful");
        modelAndView.addObject("book", new Book());
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView showViewPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("book", bookService.findById(id));
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("book", bookService.findById(id));
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editBook(Book book) {
        ModelAndView modelAndView = new ModelAndView("edit");
        bookService.save(book);
        modelAndView.addObject("message", "Book was update succesfull");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeletePage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("delete");
        modelAndView.addObject("book",bookService.findById(id));
        return modelAndView;
    }

    @PostMapping("/delete")
    public ModelAndView deleteBook(@RequestParam Long id,Book book){
        book = bookService.findById(id);
        if (book!=null){
            bookService.remove(id);
            ModelAndView modelAndView = new ModelAndView("delete");
            modelAndView.addObject("message","Delete successful");
            return modelAndView;
        }
        else {
            ModelAndView modelAndView = new ModelAndView("delete");
            modelAndView.addObject("message","Delete failed");
            return modelAndView;
        }
    }


}
