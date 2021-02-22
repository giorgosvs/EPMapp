package com.redux.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.redux.Entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
	
	@Query("UPDATE Application a  SET a.enabled = true WHERE a.id = ?1")
	@Modifying
	@Transactional
	public void enable(int id);
	
	@Query("UPDATE Application a  SET a.enabled = false WHERE a.id = ?1")
	@Modifying
	@Transactional
	public void disable(int id);
	
	@Query("SELECT a FROM Application a WHERE a.enabled=false")
	public List<Application> findPendingApplications();
	
	
}
