package uz.pdp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dto.SignupDto;
import uz.pdp.model.AuthUser;
import uz.pdp.repository.UserRepo;

@Controller
@RequestMapping(path= "/auth")
public class AuthController {


    private UserRepo userRepo;

    public AuthController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error",required = false) String error, Model model){
        model.addAttribute("error",error);
        return "auth/login";
    }

     @GetMapping("/unAuth")
    public String unAuthentication(){
        return "/auth/unAuthentication";
    }
    @GetMapping("/noAccess")
    public String noAccess(){
        return "/auth/NoAccessPage";
    }

    @GetMapping("/logout")
    public String logout( Model model){
//        model.addAttribute("error",error);
        return "auth/logout";
    }
    @GetMapping("/signup")
    public String signup(){
        return "auth/signup";
    }
    @PostMapping("/signup")
    public String signup(SignupDto signupDto){
        AuthUser user = AuthUser.builder()
                .name(signupDto.name())
                .username(signupDto.username())
                .password(signupDto.password()).build();
        userRepo.save(user);
        return "redirect:auth/login";
    }
}
