package app.controllers;

import app.dao.AccountDao;
import app.dao.LoginDao;
import app.dao.SliderDao;
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
        if(!accountDao.getConfirm().equals(accountDao.getPassword())){
            modelAndView.getModelMap().addAttribute("account", new AccountDao());
            modelAndView.getModelMap().addAttribute("errorMessage", "Passwords do not match");
            modelAndView.setViewName("register");
        }
        else if(account != null) {
            modelAndView.getModelMap().addAttribute("account", new AccountDao());
            modelAndView.getModelMap().addAttribute("errorMessage", "Account already exists");
            modelAndView.setViewName("register");
        }
        else {
            account = new Account();
            account.setEmail(accountDao.getEmail());
            account.setPassword(accountDao.getPassword());
            account.setIs_admin(false);
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

    @GetMapping("/loggedIn")
    public String loggedIn(Model model) {
        model.addAttribute("state", new SliderDao());
        return "loggedIn";
    }

    @PostMapping("/loggedIn")
    public String loggedInState(@ModelAttribute("state") SliderDao state) {
        System.out.println("Compactness is " + state.getCompactness());
        System.out.println("Political Fairness is " + state.getPolitical());
        System.out.println("Population is " + state.getPopulation());

        return "loggedIn";
    }

    @GetMapping("/myAccount")
    public String myAccount() {
        //model.addAttribute("name", name);
        return "myAccount";
    }

}
