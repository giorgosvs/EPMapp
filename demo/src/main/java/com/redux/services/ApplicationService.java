package com.redux.services;

import java.util.List;

import com.redux.Entity.Application;
import com.redux.Entity.User;

public interface ApplicationService {
	List <Application> getAllApplications();
    void saveApplication(Application application);
    Application getApplicationById(int id);
    void deleteApplicationById(int id);
    List<Application> getAllUsersPendingApplications(long userId);
}
