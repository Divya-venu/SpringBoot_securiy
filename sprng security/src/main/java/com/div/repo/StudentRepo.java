package com.div.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.div.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer>{
	

}
