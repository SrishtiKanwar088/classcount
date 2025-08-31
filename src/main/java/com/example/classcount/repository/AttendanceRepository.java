package com.example.classcount.repository;

import com.example.classcount.entity.Attendance;
import com.example.classcount.entity.Student;
import com.example.classcount.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findBySubjectAndDate(Subject subject, LocalDate date);
    List<Attendance> findByStudentAndSubject(Student student, Subject subject);

    // The missing method:
    List<Attendance> findBySubject(Subject subject);
}