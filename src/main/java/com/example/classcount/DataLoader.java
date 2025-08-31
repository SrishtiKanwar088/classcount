package com.example.classcount;

import com.example.classcount.entity.Classroom;
import com.example.classcount.entity.Student;
import com.example.classcount.entity.Subject;
import com.example.classcount.entity.User;
import com.example.classcount.repository.ClassroomRepository;
import com.example.classcount.repository.StudentRepository;
import com.example.classcount.repository.SubjectRepository;
import com.example.classcount.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClassroomRepository classroomRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder, ClassroomRepository classroomRepository, StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.classroomRepository = classroomRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create an admin user if not exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRole("ADMIN");
            userRepository.save(adminUser);
            System.out.println("Default admin user created.");
        }

        // Create classrooms if not exists
        if (classroomRepository.findByYear("1st Year").isEmpty()) {
            Classroom c1 = new Classroom();
            c1.setYear("1st Year");
            classroomRepository.save(c1);

            Classroom c2 = new Classroom();
            c2.setYear("2nd Year");
            classroomRepository.save(c2);

            Classroom c3 = new Classroom();
            c3.setYear("3rd Year");
            classroomRepository.save(c3);

            Classroom c4 = new Classroom();
            c4.setYear("4th Year");
            classroomRepository.save(c4);
        }

        // Add some sample students and subjects to 3rd Year
        Classroom thirdYear = classroomRepository.findByYear("3rd Year").get();

        if (studentRepository.findByClassroom_Year("3rd Year").isEmpty()) {
            Student s1 = new Student();
            s1.setName("Srishti Kanwar");
            s1.setRollNumber("23ERWS088");
            s1.setClassroom(thirdYear);

            Student s2 = new Student();
            s2.setName("Nandani Sen");
            s2.setRollNumber("23ERWCS057");
            s2.setClassroom(thirdYear);

            studentRepository.saveAll(Arrays.asList(s1, s2));
        }

        if (subjectRepository.findByClassroom_Year("3rd Year").isEmpty()) {
            Subject sub1 = new Subject();
            sub1.setName("Computer Graphics (CGM)");
            sub1.setClassroom(thirdYear);

            Subject sub2 = new Subject();
            sub2.setName("Human-Computer Interaction (HCI)");
            sub2.setClassroom(thirdYear);

            subjectRepository.saveAll(Arrays.asList(sub1, sub2));
        }

        Classroom secondYear = classroomRepository.findByYear("2nd Year").get();

        if (studentRepository.findByClassroom_Year("2nd Year").isEmpty()) {
            Student s1 = new Student();
            s1.setName("Sita");
            s1.setRollNumber("23ERWS024");
            s1.setClassroom(secondYear);

            Student s2 = new Student();
            s2.setName("Geeta");
            s2.setRollNumber("23ERWCS045");
            s2.setClassroom(secondYear);

            studentRepository.saveAll(Arrays.asList(s1, s2));
        }

        if (subjectRepository.findByClassroom_Year("2nd Year").isEmpty()) {
            Subject sub1 = new Subject();
            sub1.setName("OOPS");
            sub1.setClassroom(secondYear);

            Subject sub2 = new Subject();
            sub2.setName("DSA");
            sub2.setClassroom(secondYear);

            subjectRepository.saveAll(Arrays.asList(sub1, sub2));
        }

        Classroom firstYear = classroomRepository.findByYear("1st Year").get();

        if (studentRepository.findByClassroom_Year("1st Year").isEmpty()) {
            Student s1 = new Student();
            s1.setName("Payal");
            s1.setRollNumber("23ERWS077");
            s1.setClassroom(firstYear);

            Student s2 = new Student();
            s2.setName("Soni");
            s2.setRollNumber("23ERWCS099");
            s2.setClassroom(firstYear);

            studentRepository.saveAll(Arrays.asList(s1, s2));
        }

        if (subjectRepository.findByClassroom_Year("1st Year").isEmpty()) {
            Subject sub1 = new Subject();
            sub1.setName("Maths 1");
            sub1.setClassroom(firstYear);

            Subject sub2 = new Subject();
            sub2.setName("Electronics");
            sub2.setClassroom(firstYear);

            subjectRepository.saveAll(Arrays.asList(sub1, sub2));
        }

        Classroom fourthYear = classroomRepository.findByYear("4th Year").get();

        if (studentRepository.findByClassroom_Year("4th Year").isEmpty()) {
            Student s1 = new Student();
            s1.setName("Riya");
            s1.setRollNumber("23ERWS033");
            s1.setClassroom(fourthYear);

            Student s2 = new Student();
            s2.setName("Shreya");
            s2.setRollNumber("23ERWCS022");
            s2.setClassroom(fourthYear);

            studentRepository.saveAll(Arrays.asList(s1, s2));
        }

        if (subjectRepository.findByClassroom_Year("4th Year").isEmpty()) {
            Subject sub1 = new Subject();
            sub1.setName("IOT");
            sub1.setClassroom(fourthYear);

            Subject sub2 = new Subject();
            sub2.setName("AI");
            sub2.setClassroom(fourthYear);

            subjectRepository.saveAll(Arrays.asList(sub1, sub2));
        }
    }
}


