package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    // Login GET
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    // Login POST
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        if (username.equals("admin") && password.equals("20240140075")) {
            session.setAttribute("loggedIn", true);
            return "redirect:/home";
        }
        model.addAttribute("error", "Username atau password salah!");
        return "login";
    }

    // Home GET
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/";
        }
        List<User> users = (List<User>) session.getAttribute("users");
        if (users == null) users = new ArrayList<>();
        model.addAttribute("users", users);
        return "home";
    }

    // Form GET
    @GetMapping("/form")
    public String formPage(HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/";
        }
        return "form";
    }

    // Form POST
    @PostMapping("/form")
    public String submitForm(@RequestParam String nama,
                             @RequestParam String nim,
                             @RequestParam String jenisKelamin,
                             HttpSession session) {
        List<User> users = (List<User>) session.getAttribute("users");
        if (users == null) users = new ArrayList<>();

        User user = new User();
        user.setNama(nama);
        user.setNim(nim);
        user.setJenisKelamin(jenisKelamin);
        users.add(user);

        session.setAttribute("users", users);
        return "redirect:/home";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
