package com.project.library.controller;

import com.project.library.model.Admin;
import com.project.library.model.Book;
import com.project.library.model.Request;
import com.project.library.model.Reserve;
import com.project.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.project.library.service.AdminService;
import com.project.library.service.BookService;
import com.project.library.service.RequestService;
import com.project.library.service.ReserveService;
import com.project.library.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private ReserveService reserveService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RequestService requestService;
    
    private String loggedEmail;
    
    @GetMapping("/admin")
    public String loadLoginPage(Model model) {
        model.addAttribute("admin", new Admin());
        
        return "admin/admin-login";
    }
    
    @GetMapping("/add-new-book")
    public String addBookPage(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("loggedEmail", loggedEmail);
        
        return "admin/add-new-book";
    }
    
    @GetMapping("/admin/admin-dashboard")
    public String loadAdminDashboard(Model model) {
        List<Book> bookList = bookService.getAllBooks();
        List<Reserve> reserveList = reserveService.getAllReservations();
        List<User> usersList = userService.getAllUsers();
        List<Request> requestList = requestService.getAllRequests();
        
        List<String> booksNameList = new ArrayList<>();
        List<String> usersNameList = new ArrayList<>();
        
        for (Reserve r : reserveList) {
            for (Book b : bookList) {
                if (r.getBid() == b.getId()) {
                    booksNameList.add(b.getName());
                }
            }
            
            for (User u : usersList) {
                if (r.getUid() == u.getId()) {
                    usersNameList.add(u.getFirstname() + " " + u.getLastname());
                }
            }
        }
            
        model.addAttribute("bookList", bookList);
        model.addAttribute("booksNameList", booksNameList);
        model.addAttribute("usersNameList", usersNameList);
        model.addAttribute("requestList", requestList);
        model.addAttribute("loggedEmail", loggedEmail);
            
        return "admin/admin-dashboard";
    }
    
    @PostMapping("/admin-login")
    public String login(@ModelAttribute("admin") Admin admin) {
        Admin ad = adminService.login(admin.getEmail(), admin.getPassword());
        
        if (ad != null) {
            loggedEmail = admin.getEmail();
            return "redirect:/admin/admin-dashboard";
        }else {
            return "redirect:/admin";
        }
    }
    
    @PostMapping("/confirm-book")
    public String addNewBook(@ModelAttribute("book") Book book) {
        bookService.addNewBook(book);
        return "redirect:/admin/admin-dashboard";
    }
    
    @RequestMapping("/edit-book/{id}")
    public ModelAndView editBook(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("admin/add-new-book");
        Book book = bookService.getBook(id);
        mav.addObject(book);
        return mav;
    }
    
    @RequestMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/admin/admin-dashboard";
    }
    
    @GetMapping("/admin-logout")
    public String logout() {
        return "redirect:/admin";
    }
}
