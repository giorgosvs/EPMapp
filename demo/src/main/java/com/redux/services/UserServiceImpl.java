package com.redux.services;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.redux.Entity.Role;
import com.redux.Entity.User;
import com.redux.Repository.UserRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();	
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		if(user.getCreatedTime() == null) {
			user.setCreatedTime(new Date());
		}
		user.setEnabled(true);
		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		
		this.userRepository.save(user);

	}

	@Override
	public User getUserById(long id) {
		Optional <User> optional = userRepository.findById(id);
        User user = null;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new RuntimeException(" User with id " + id + " was not found");
        }
        return user;
	}

	@Override
	public void deleteUserById(long id) {
		this.userRepository.deleteById(id);

	}

	@Override
	public void registerNewUserAccount(User user) {
		String encodedPass = encoder.encode(user.getPassword());
		user.setPassword(encodedPass);
		user.setCreatedTime(new Date());
		user.setEnabled(false);

		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		
		String randomCode = RandomString.make(64);
		
		user.setVerificationCode(randomCode);
		userRepository.save(user);
		
	}

	public void sendVerificationEmail(User user,String siteURL) throws MessagingException, UnsupportedEncodingException {
		String subject = "EPM - Account Activation";
		String senderName = "Employee Petition Manager System";
		
		String mailContent = "<h3>Hello " + user.getUsername()  + "! </h3>";
		mailContent += "<p>Please click to the link below, in order to activate your account.</p>";
		
		String verificationURL = siteURL + "/activate?code=" + user.getVerificationCode();
		mailContent += "<h3><a href=\"" + verificationURL + "\">Activate account</a></h3>";
		
		mailContent += "<p>Thank you, the EPM team.</p>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		//try {
			helper.setFrom("georgeflybikes@gmail.com",senderName);
			helper.setTo(user.getEmail());
			helper.setSubject(subject);
			helper.setText(mailContent,true);
			mailSender.send(message);
		
	}

	@Override
	public boolean isEmailUnique(String email) {
		User existingUser = userRepository.findByUsernameOrEmail(email);
		return existingUser == null;
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByUsernameOrEmail(email);
	}

	@Override
	public boolean verifyCode(String verificationCode) {
		User user = userRepository.findByVerficationCode(verificationCode);
		if(user == null || user.isEnabled()) {
			return false;
		}else {
			userRepository.enable(user.getId());
			return true;
		}
	}
	
	

}
