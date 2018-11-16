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


}
