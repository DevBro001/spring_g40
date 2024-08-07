package uz.pdp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.SecurityUtils;
import uz.pdp.config.security.SpringSecurityConfig;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/seller")
    public String seller(Model model){
        model.addAttribute("page","SELLER PAGE");
        UserDetails user = SecurityUtils.getUser();
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getAuthorities());


        return "main/about";
    }
    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("page","ADMIN PAGE");
        return "main/about";
    }
}
