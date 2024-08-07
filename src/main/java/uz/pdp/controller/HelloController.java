package uz.pdp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HelloController {


    @GetMapping("/main/home")
    public String home(){
        return "main/home";
    }
    @GetMapping("/main/about")
    public String about(Model model){
        model.addAttribute("page","ABOUT PAGE");
        return "main/about";
    }


    @GetMapping("/main/user")
    @PreAuthorize("hasAnyRole('ADMIN','USER','SELLER')")
    public String user(Model model){
        model.addAttribute("page","USER PAGE");

        return "main/about";
    }
}
