package com.redux.Controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.redux.Entity.User;
import com.redux.Repository.UserRepository;
import com.redux.services.UserServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired 
	private UserRepository userRepository;
	
	@GetMapping(value = "/users")
	public List <User> retrieveAllUsers(){
		return userService.getAllUsers();
	}
	
	
	@GetMapping("/users/{id}")
	public User retrieveUserById(@PathVariable int id) {
		return userService.getUserById(id);		
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable int id) {
		this.userService.deleteUserById(id);
	}

	@PostMapping("/users")
	public String createUser(@ModelAttribute("user") User user,RedirectAttributes redirectAttrs) {
		userService.saveUser(user);
		System.out.println("user id " + user.getId());
		
		
		/*URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();

		return ResponseEntity.created(location).build();*/
		return "redirect:/users";

	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable int id) {
		
		user.setId(id);
		
		userService.saveUser(user);;

		return ResponseEntity.noContent().build();
	}
	
	

}
