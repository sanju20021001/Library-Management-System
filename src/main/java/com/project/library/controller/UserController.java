package com.project.library.controller;

import com.project.library.model.Admin;
import com.project.library.model.Book;
import com.project.library.model.Request;
import com.project.library.model.Reserve;
import com.project.library.model.User;
import com.project.library.service.AdminService;
import com.project.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.project.library.service.BookService;
import com.project.library.service.RequestService;
import com.project.library.service.ReserveService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private ReserveService reserveService;
    
    @Autowired
    private RequestService requestService;
    
    private String username = "Public User";
    
    private int userid;
    
    @GetMapping("/")
    public String loadHomePage(Model model) {
        Admin admin = adminService.getAdminDetails();
        List<Book> booksList = bookService.getAllBooks();
        
        model.addAttribute("booksList", booksList);
        model.addAttribute("admin", admin);
        model.addAttribute("username", username);
        
        return "index";
    }
    
    @GetMapping("/login")
    public String loadLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    
    @GetMapping("/register")
    public String loadRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    @GetMapping("/request-book")
    public String loadRequestPage(Model model) {
        model.addAttribute("request", new Request());
        return "request";
    }
    
    @PostMapping("/validate-login")
    public String validateLogin(@ModelAttribute("user") User user) {
        User u = userService.login(user.getEmail(), user.getPassword());
        
        if (u != null) {
            userid = u.getId();
            username = u.getFirstname();
            return "redirect:/";
        }else {
            return "redirect:/login";
        }
    }
    
    @PostMapping("/validate-registration")
    public String validateRegistration(@ModelAttribute("user") User user) {
        userService.registerUser(user);
        
        userid = user.getId();
        username = user.getFirstname();
        
        return "redirect:/";
    }
    
    @PostMapping("/confirm-request")
    public String confirmRequest(@ModelAttribute("request") Request request, RedirectAttributes attr) {
        request.setUserid(userid);
        requestService.request(request);
        attr.addFlashAttribute("message_success", "Request sent!");
        return "redirect:/";
    }
    
    @RequestMapping("/reserve-book/{id}")
    public String reserveBook(@PathVariable("id") int id, RedirectAttributes attr) {
        boolean isReserved = reserveService.isReserved(id);
        
        if (isReserved) {
            attr.addFlashAttribute("message_failed", "You have reserved book already!");
            return "redirect:/";
        }
        
        Reserve r = new Reserve();
        
        r.setBid(id);
        r.setUid(userid);
        r.setStatus(1);
        
        reserveService.reserveBook(r);
        
        attr.addFlashAttribute("message_success", "Reserved!");
        
        isReserved = true;
        
        return "redirect:/";
    }
    
    @RequestMapping("/return-book")
    public String returnBook(RedirectAttributes attr) {
        int reserveId = reserveService.getReservationId(userid);
        
        if (reserveId != 0) {
            reserveService.deleteReservation(reserveId);
        
            attr.addFlashAttribute("message_success", "Book returned successfully!");
        }
        
        return "redirect:/";
    }
    
    @GetMapping("/logout")
    public String logOut() {
        userid = 0;
        username = "Public User";
        
        return "redirect:/";
    }

    public String getUsername() {
        return username;
    }
    
    
}
