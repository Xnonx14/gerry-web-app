package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController {
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/add")
    public String add(@RequestParam String email, @RequestParam String password) {
        Account n = new Account();
        n.setEmail(email);
        n.setPassword(password);
        n.setIsAdmin(false);
        userRepository.save(n);
        return "about";
    }

    /*
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }*/

    @GetMapping("/index")
    public String index(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerAccount() {
        /*
            if failed to create account, return "register"
            else,
                create account
                persist to database
                return "loggedIn
         */

        return "register";
    }

    @GetMapping("/about")
    public String about() {
        //model.addAttribute("name", name);
        return "about";
    }

    @GetMapping("/logout")
    public String logout() {
        //model.addAttribute("name", name);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        //model.addAttribute("name", name);
        return "login";
    }

    @GetMapping("/loggedIn")
    public String loggedIn(Model model) {
        model.addAttribute("success", true);
        return "loggedIn";
    }

    @GetMapping("/myAccount")
    public String myAccount() {
        //model.addAttribute("name", name);
        return "myAccount";
    }

}
