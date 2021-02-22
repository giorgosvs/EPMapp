package com.redux.Controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.redux.Entity.Application;
import com.redux.Repository.ApplicationRepository;
import com.redux.services.ApplicationServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class ApplicationController {
	
	@Autowired
	private ApplicationServiceImpl applicationService;
	
	@Autowired 
	private ApplicationRepository applicationRepository;
	
	@GetMapping("/applications")
	public List<Application> retrieveAllApplications(Model model){
		return applicationService.getAllApplications();
	}
	
	
	@GetMapping("/applications/{id}")
	public Application retrieveApplicationById(@PathVariable int id) {
		return applicationService.getApplicationById(id);		
	}
	
	@DeleteMapping("/applications/{id}")
	public void deleteApplicationById(@PathVariable int id) {
		this.applicationService.deleteApplicationById(id);
	}

	@PostMapping("/applications")
	public ResponseEntity<Object> createUser(@RequestBody Application application) {
		Application savedApplication = applicationRepository.save(application);
		System.out.println("application id " + savedApplication.getId());

		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedApplication.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@PutMapping("/applications/{id}")
	public ResponseEntity<Object> updateUser(@RequestBody Application application, @PathVariable int id) {
		
		application.setId(id);
		
		applicationService.saveApplication(application);;

		return ResponseEntity.noContent().build();
	}
	
}
