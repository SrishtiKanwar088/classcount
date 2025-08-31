package com.example.classcount.repository;

import com.example.classcount.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByClassroom_Year(String year);
    // This method will automatically fetch all students belonging to a specific year.
}