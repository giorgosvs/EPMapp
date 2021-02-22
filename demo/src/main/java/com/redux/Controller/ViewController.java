package com.redux.Controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.redux.Entity.Application;
import com.redux.Entity.Role;
import com.redux.Entity.User;
import com.redux.Repository.ApplicationRepository;
import com.redux.Repository.UserRepository;
import com.redux.services.ApplicationService;
import com.redux.services.ApplicationServiceImpl;
import com.redux.services.UserService;
import com.redux.services.UserServiceImpl;

@Controller
public class ViewController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private ApplicationServiceImpl applicationService;
	
	@GetMapping("/users")
	public String manageUsers(Model model,Authentication auth) {
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("name",auth.getName());
		return "views/list-users";
	}
	
	
	@GetMapping("users/showAddForm")
	public String showAddForm(Model model) {
		
		User user = new User();
		
		model.addAttribute("user", user);
		model.addAttribute("pageTitle","Add New User");
		
		return "views/create-users";
	}
	
	@GetMapping("users/showUpdateForm/{id}")
	public String showUpdateForm(@PathVariable (value = "id") long id,Model model) {
		
		User user = userService.getUserById(id);
		
		model.addAttribute("user", user);
		model.addAttribute("pageTitle","Modify User");
		
		return "views/update-users";
	}
	
	@GetMapping("/users/deleteUser/{id}")
	public String deleteUser(@PathVariable (value="id") int id) {
		this.userService.deleteUserById(id);
		
		return "redirect:/users";
	}

	@GetMapping("/requests")
	public String manageApplications(Model model,Authentication auth) {
		model.addAttribute("applications",applicationService.getAllApplications());
		model.addAttribute("name",auth.getName());
		return "views/list-applications";
	}

	@GetMapping("/requests/pending")
	public String getPendingLeaveRequests(Model model,Authentication auth,User user,Application application) {
		model.addAttribute("applications",applicationRepository.findPendingApplications());
		return "views/list-applications-pending";
	}
	
	@GetMapping("/requests/approveRequest/{id}")
	public String approveLeaveRequest(@PathVariable (value="id") int id) {
		applicationRepository.enable(id);
		return "redirect:/requests/pending";
	}
	
	@GetMapping("/requests/declineRequest/{id}")
	public String declineLeaveRequest(@PathVariable (value="id") int id) {
		applicationService.deleteApplicationById(id);
		return "redirect:/requests/pending";
	}
	
	@GetMapping("/requests/deleteRequest/{id}")
	public String deleteLeaveRequest(@PathVariable (value="id") int id) {
		applicationService.deleteApplicationById(id);
		return "redirect:/requests";
	}
		
	
	@GetMapping("/activate")
	public String verifyAccount(@Param ("code") String code,Model model) {
		boolean verified = userService.verifyCode(code);
		
		String pageTitle = verified ? "Activation Successful!" : "Activation Failed!";
		model.addAttribute("pageTitle",pageTitle);
		
		return "views/" + (verified ? "activationSuccess" : "activationFailed");
	}
	
	@GetMapping("/home/submitRequest")
	public String newLeaveRequest(User user,Application application,Model model) {
		model.addAttribute("user",user);
		model.addAttribute("application",application);
		model.addAttribute("pageTitle","Work Permit");
		return "views/application-form";
	}
	
	@GetMapping("/home/requests")
	public String getPendningRequests(Model model,Authentication auth,User user,Application application) {
		User u = userRepository.findByUsernameOrEmail(auth.getName());
		model.addAttribute("applications", applicationService.getAllUsersPendingApplications(u.getId()));
		model.addAttribute("pageTitle","Requests");
		return "views/list-requests";
	}
	
	

}
