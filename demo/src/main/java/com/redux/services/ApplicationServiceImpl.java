package com.redux.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redux.Entity.Application;
import com.redux.Entity.User;
import com.redux.Repository.ApplicationRepository;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	
	@Autowired
	private ApplicationRepository appRepository;
	
	@Override
	public List<Application> getAllApplications() {
		return appRepository.findAll();
	}

	@Override
	public void saveApplication(Application application) {
		this.appRepository.save(application);

	}

	@Override
	public Application getApplicationById(int id) {
		Optional <Application> optional = appRepository.findById(id);
        Application app = null;
        if (optional.isPresent()) {
            app = optional.get();
        } else {
            throw new RuntimeException(" Application with id " + id + " was not found");
        }
        return app;
	}

	@Override
	public void deleteApplicationById(int id) {
		this.appRepository.deleteById(id);
	}

	public List<Application> getAllUsersPendingApplications(long userId) {
		List<Application> pending = appRepository.findPendingApplications();
		List<Application> userList = new ArrayList();
		for(Application a : pending) {
			if(a.getUserId() == userId) {
				userList.add(a);
			}
		}
		return userList;
	}

	
}
