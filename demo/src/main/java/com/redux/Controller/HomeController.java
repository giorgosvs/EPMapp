package com.redux.Controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.redux.Entity.Application;
import com.redux.Entity.User;
import com.redux.Repository.UserRepository;
import com.redux.services.ApplicationServiceImpl;
import com.redux.services.UserServiceImpl;
import com.redux.util.Utility;


@Controller
public class HomeController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApplicationServiceImpl applicationService;
	
	@GetMapping("/")
	public String index(Model model,Authentication auth){
		model.addAttribute("pageTitle","Welcome");
		if(auth != null) {
		model.addAttribute("name",auth.getName());
		}
		
		return "home/index";
	}

	@GetMapping("/home")
	public String home(Model model,Authentication auth,User user) {
		model.addAttribute("name",auth.getName());
		model.addAttribute("pageTitle","Home");
		
		return "home/home";
	}

	// Login form
	@RequestMapping("/login")
	public String login(Authentication auth) {
		auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth == null || auth instanceof AnonymousAuthenticationToken) {
			return "home/login";
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/signup")
	public String showRegistrationForm(Model model) {
		User user = new User();
		model.addAttribute("user",user);
		model.addAttribute("pageTitle","Register");
		return "views/registration";
	}
	
	@PostMapping("/process_registration")
	public String registerUser(User user,Model model,HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		
		String siteURL = Utility.getSiteURL(request);
		
		userService.registerNewUserAccount(user);
		userService.sendVerificationEmail(user,siteURL);
		model.addAttribute("pageTitle","Registration Success");
		return "views/registrationSuccess";
	}
	
	@PostMapping("/home/process_request")
	public String registerLeaveRequest(User user,Application application,Authentication auth,Model model) {
		User u = userRepository.findByUsernameOrEmail(auth.getName());
		application.setEnabled(false);
		application.setUserId(u.getId());
		applicationService.saveApplication(application);
		model.addAttribute("pageTitle","Leave Request");
		return "views/submissionSuccess";
	}
	
}
