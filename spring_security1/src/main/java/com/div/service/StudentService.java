package com.div.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.div.repo.StudentRepo;

@Service
public class StudentService {
	
	@Autowired 
	private StudentRepo repo;

}
