package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/index")
    public String index() {
        //model.addAttribute("name", name);
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

    @GetMapping("/login")
    public String login() {
        //model.addAttribute("name", name);
        return "login";
    }

    @GetMapping("/loggedIn")
    public String loggedIn() {
        //model.addAttribute("name", name);
        return "loggedIn";
    }

    @GetMapping("/myAccount")
    public String myAccount() {
        //model.addAttribute("name", name);
        return "myAccount";
    }
}
