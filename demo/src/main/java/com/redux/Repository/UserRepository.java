package com.redux.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.redux.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> { //<entityclass,primary key type>
	
	@Query("SELECT u FROM User u WHERE u.username=:usernameOrEmail OR u.email=:usernameOrEmail")
    User findByUsernameOrEmail(String usernameOrEmail);
	
	@Query("UPDATE User u  SET u.enabled = true WHERE u.id = ?1")
	@Modifying
	@Transactional
	public void enable(long id);
	
	@Query("SELECT u FROM User u WHERE u.verificationCode= ?1")
	public User findByVerficationCode(String code);
	
}
