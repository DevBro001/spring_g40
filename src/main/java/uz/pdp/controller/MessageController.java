package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.Utils;

import java.util.Locale;

@Controller
@RequestMapping("/message")

public class MessageController {

    final    Utils utils;

    public MessageController(Utils utils) {
        this.utils = utils;
    }

    @GetMapping("/{lang}/{code}")
    @ResponseBody
    public String message(@PathVariable("lang") String lang, @PathVariable("code") String code){
        Locale uz = Locale.forLanguageTag(lang);
        String message = utils.getMessage(code);
        System.out.println(message);
        return message;
    }
    @GetMapping("/{code}")
    @ResponseBody
    public String message(@PathVariable("code") String code){
        String message = utils.getMessageWithDefault(code,"This property not exist");
        System.out.println(message);
        return message;
    }
    @GetMapping("/param/{code}")
    @ResponseBody
    public String messageWithParam(@PathVariable("code") String code){
        String message = utils.getMessage(code,new Object[]{"12:00","23:00"});
        System.out.println(message);
        return message;
    }
    @GetMapping("/pageNotFound")
    public String pageNotFound(){
      return "pageNotFound";
    }
}
