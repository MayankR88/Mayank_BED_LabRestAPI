package com.glearning.students.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.glearning.students.dao.StudentRepository;
import com.glearning.students.dao.UserRepository;
import com.glearning.students.model.Role;
import com.glearning.students.model.Student;
import com.glearning.students.model.User;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BootstrapAppData {
	
	private final UserRepository userRepository;
	private final StudentRepository studentRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	@EventListener(ApplicationReadyEvent.class)
	public void initializeData(ApplicationReadyEvent readyEvent) {
		
		Student sumanth = new Student("Sumanth", "Rao", "Python", "India");
		Student ravi = new Student("Ravi", "Patel", "HTML", "USA");
		Student harsh = new Student("Harsh", "Mittal", "NodeJs", "Australia");
		Student mark = new Student("Mark", "Gregory", "Java", "Sweden");
		
		this.studentRepository.save(sumanth);
		this.studentRepository.save(ravi);
		this.studentRepository.save(harsh);
		this.studentRepository.save(mark);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void initializeUsersData(ApplicationReadyEvent readyEvent) {
		
			User sumanth = new User("sumanth", passwordEncoder.encode("welcome"));
			User harsh = new User("harsh", passwordEncoder.encode("welcome"));
			
			Role userRole = new Role("ROLE_USER");
			Role adminRole = new Role("ROLE_ADMIN");
			
			sumanth.addRole(userRole);
			
			harsh.addRole(userRole);
			harsh.addRole(adminRole);
			
			this.userRepository.save(sumanth);
			this.userRepository.save(harsh);
		
	}

}
