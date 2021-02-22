package com.redux.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "applications")
public class Application {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "app_id")
	int id;
	
	@Column(name = "user_id")
	long userId;
	
	@Column(name = "enabled")
	private boolean enabled;
	
	@Column(name="type")
	private String applicationType;
	
	@Column(name = "description")
	private String applicationDescription;
	
	@Column(name="date_from")
	private String dateFrom;
	
	@Column(name="date_to")
	private String dateTo;
	
	public Application() {
		
	}

	public Application(int id,long userId, String applicationType, String applicationDescription,boolean enabled,String dateFrom,String dateTo) {
		super();
		this.id = id;
		this.userId = userId;
		this.applicationType = applicationType;
		this.applicationDescription = applicationDescription;
		this.enabled=enabled;
		this.dateFrom=dateFrom;
		this.dateTo=dateTo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getApplicationDescription() {
		return applicationDescription;
	}

	public void setApplicationDescription(String applicationDescription) {
		this.applicationDescription = applicationDescription;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	@Override
	public String toString() {
		return "Application [id=" + id + ", userId=" + userId + ", enabled=" + enabled + ", applicationType="
				+ applicationType + ", applicationDescription=" + applicationDescription + ", dateFrom=" + dateFrom
				+ ", dateTo=" + dateTo + "]";
	}
	
}
