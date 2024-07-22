package uz.pdp;

public class Main {
    public static void main(String[] args) {

        UserRepo userRepo = new UserRepo();

        UserService userService = new UserService(userRepo);
    }
}