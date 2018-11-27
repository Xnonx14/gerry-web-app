package app.controllers;

import app.dao.AccountDao;
import app.dao.TempDao;
import app.model.Account;
import app.repository.AccountRepository;
import app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GreetingController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    
    @GetMapping("/add")
    public String add(@RequestParam String email, @RequestParam String password) {
        Account n = new Account();
        n.setEmail(email);
        n.setPassword(password);
        n.setIsAdmin(false);
        accountRepository.save(n);
        return "about";
    }

    /*
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }*/

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("account", new AccountDao());
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerAccount(ModelAndView modelAndView, @ModelAttribute AccountDao accountDao) {
        Account account = accountService.findByEmail(accountDao.getEmail());
        if(account != null) {
            modelAndView.addObject("account", new AccountDao());
            //modelAndView.addObject("errorMessage", "Account already exists");
            modelAndView.getModelMap().addAttribute("errorMessage", "Account already exists");
            modelAndView.setViewName("register");
        }
        else {
            System.out.println("DOESNT EXIST");
            modelAndView.setViewName("about");
        }
//        Account n = new Account();
//        n.setEmail(email);
//        n.setPassword(password);
//        n.setIsAdmin(false);
//        accountRepository.save(n);
        return modelAndView;
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
        model.addAttribute("state", new TempDao());
        return "loggedIn";
    }

    @PostMapping("/loggedIn")
    public String loggedInState(@ModelAttribute("state") TempDao state) {
        System.out.println(state.getState());
        return "loggedIn";
    }

    @GetMapping("/myAccount")
    public String myAccount() {
        //model.addAttribute("name", name);
        return "myAccount";
    }

}
