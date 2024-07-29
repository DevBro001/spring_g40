package uz.pdp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.dto.SignupDto;
import uz.pdp.dto.UserDTO;

import java.util.List;

@Controller
public class HelloController {

    @RequestMapping(method = RequestMethod.GET,value = "/hello")
    public ModelAndView hello(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/main/telegram");

        return modelAndView;
    }
    @RequestMapping(method = RequestMethod.GET,value = "/about")
    public ModelAndView about(@RequestParam("num") Integer num, @RequestParam(value = "isShow",required = false) Boolean isShow){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", List.of("Salim","Sattor","Smith","Alish"));
        modelAndView.addObject("isShow", isShow);
        modelAndView.addObject("num", num);
        modelAndView.setViewName("main/about");
        return modelAndView;
    }
    @RequestMapping(method = RequestMethod.GET,value = "/home")
    public ModelAndView home(){
        UserDTO user = new UserDTO("username", "Asror", "asror@gmail.com", 18);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",user);
        modelAndView.setViewName("main/home");
        return modelAndView;
    }
}
