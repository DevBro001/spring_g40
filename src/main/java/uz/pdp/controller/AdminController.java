package uz.pdp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.SecurityUtils;
import uz.pdp.config.security.SpringSecurityConfig;

import java.util.Collection;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/getUser")
    @PreAuthorize("hasAuthority('SHOW_USER')")
    public String getUser(Model model){
        UserDetails user = SecurityUtils.getUser();
        model.addAttribute("page","ADMIN SHOW PAGE "+user.getUsername());
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        System.out.println(authorities);
        return "main/about";
    }
    @GetMapping("/deleteUser")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public String deleteUser(Model model){
        UserDetails user = SecurityUtils.getUser();

        model.addAttribute("page","ADMIN DELETE PAGE "+user.getUsername());
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        System.out.println(authorities);

        return "main/about";
    }
    @GetMapping("/createUser")
    @PreAuthorize("hasAuthority('CREATE_USER')")
    public String createUser(Model model){
        UserDetails user = SecurityUtils.getUser();
        model.addAttribute("page","ADMIN CREATE PAGE "+user.getUsername());
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        System.out.println(authorities);

        return "main/about";
    }
    @GetMapping("/blockUser")
    @PreAuthorize("hasAuthority('BLOCK_USER')")
    public String blockUser(Model model){
        UserDetails user = SecurityUtils.getUser();
        model.addAttribute("page","ADMIN BLOCK PAGE "+user.getUsername());
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        System.out.println(authorities);

        return "main/about";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String admin(Model model){
        model.addAttribute("page","ADMIN PAGE");
        return "main/about";
    }
}
