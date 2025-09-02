package com.example.classcount.controller;

import com.example.classcount.entity.Classroom;
import com.example.classcount.entity.Student;
import com.example.classcount.repository.ClassroomRepository;
import com.example.classcount.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;

    public StudentController(StudentRepository studentRepository, ClassroomRepository classroomRepository) {
        this.studentRepository = studentRepository;
        this.classroomRepository = classroomRepository;
    }

    // Display the list of students for a specific year
    @GetMapping("/{year}")
    public String listStudents(@PathVariable("year") String year, Model model) {
        List<Student> students = studentRepository.findByClassroom_Year(year);
        model.addAttribute("students", students);
        model.addAttribute("currentYear", year);
        model.addAttribute("newStudent", new Student()); // For the Add Student form
        return "student-list"; // Renders src/main/resources/templates/student-list.html
    }

    // Handle the form submission for adding a student
    @PostMapping("/add")
    public String addStudent(@ModelAttribute("newStudent") Student student, RedirectAttributes redirectAttributes) {
        try {
            Classroom classroom = classroomRepository.findByYear(student.getClassroom().getYear())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid year provided."));
            student.setClassroom(classroom);
            studentRepository.save(student);
            redirectAttributes.addFlashAttribute("successMessage", "Student saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving student: " + e.getMessage());
        }
        return "redirect:/students/" + student.getClassroom().getYear();
    }

    // Handle the form for editing a student (displays the pre-filled form)
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable("id") Long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID:" + id));
        model.addAttribute("student", student);
        model.addAttribute("currentYear", student.getClassroom().getYear());
        return "student-edit"; // Renders src/main/resources/templates/student-edit.html
    }

    // Handle the submission of the edited student
    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id") Long id, @ModelAttribute("student") Student student, RedirectAttributes redirectAttributes) {
        student.setId(id);

        // This is the crucial part that was causing the error.
        // We need to re-attach the existing classroom before saving.
        Classroom existingClassroom = classroomRepository.findById(student.getClassroom().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid classroom ID."));
        student.setClassroom(existingClassroom);

        studentRepository.save(student);
        redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully!");

        // Correct redirect back to the student list for the correct year
        return "redirect:/students/" + existingClassroom.getYear();
    }

    // Handle student deletion
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID:" + id));
        String year = student.getClassroom().getYear();
        studentRepository.delete(student);
        redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully!");
        return "redirect:/students/" + year;
    }
}
