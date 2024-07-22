package uz.pdp;

public class UserService {

    private UserRepo userRepo ;
   /* private AdminService adminService;
    private ProductService productService;*/

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
}
