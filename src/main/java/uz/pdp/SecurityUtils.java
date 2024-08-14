package uz.pdp;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.config.security.AuthUser;

public class SecurityUtils {

    public static AuthUser getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null&&authentication.isAuthenticated()){
            Object principal = authentication.getPrincipal();
            if (principal instanceof AuthUser userDetails){
               return userDetails;
            }
        }
        return null;
    }
}
