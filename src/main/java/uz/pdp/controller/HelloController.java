package uz.pdp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HelloController {


    @GetMapping("/main/home")
    public String home(){
        return "main/home";
    }
    @GetMapping("/main/about")
    public String about(){
        return "main/about";
    }
}
