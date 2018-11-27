package app.controllers;

import app.dao.AccountDao;
import app.dao.LoginDao;
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
            modelAndView.getModelMap().addAttribute("account", new AccountDao());
            modelAndView.getModelMap().addAttribute("errorMessage", "Account already exists");
            modelAndView.setViewName("register");
        }
        else {
            account = new Account();
            account.setEmail(accountDao.getEmail());
            account.setPassword(accountDao.getPassword());
            account.setIsAdmin(false);
            accountService.saveAccount(account);
            modelAndView.getModelMap().addAttribute("successMessage", "Account successfully created!");
            modelAndView.getModelMap().addAttribute("login", new LoginDao());
            modelAndView.setViewName("login");
        }
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
    public String login(Model model) {
        System.out.println("running");
        model.addAttribute("login", new LoginDao());
        return "login";
    }
//    @PostMapping("/login")
//    public ModelAndView loginAttempt(ModelAndView modelAndView, @ModelAttribute("login") LoginDao loginDao) {
//        Account account = accountService.findByEmail(loginDao.getEmail());
//        System.out.println("DB password: " + account.getPassword());
//        System.out.println("Form password: " + loginDao.getPassword());
//        if(account == null) {
//            modelAndView.getModelMap().addAttribute("errorMessage", "Account doesn't exist");
//            modelAndView.setViewName("login");
//        }
//        else if(account.getPassword().equals(loginDao.getPassword())){
//            modelAndView.getModelMap().addAttribute("state", new TempDao());
//            modelAndView.setViewName("loggedIn");
//        }
//        else {
//            modelAndView.getModelMap().addAttribute("errorMessage", "Invalid password.");
//            modelAndView.setViewName("login");
//        }
//        modelAndView.setViewName("login");
//        return modelAndView;
//    }

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
