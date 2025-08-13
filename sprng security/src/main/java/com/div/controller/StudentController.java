package com.div.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.div.entity.Student;
import com.div.repo.StudentRepo;
import com.div.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController {
	@Autowired
	private StudentRepo studentRepo;

		
	@PostMapping("/students")
	public Student addStudent(@RequestBody Student student)
	{
		studentRepo.save(student);
		return student;
		
	}
	
	@GetMapping("/students")
	public List<Student> getStudent()
	{
		List<Student> sts=studentRepo.findAll();
		return sts;
	}
	
	@GetMapping("/csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request)
	{
		return (CsrfToken)request.getAttribute("_csrf");
	}
	
	
}
