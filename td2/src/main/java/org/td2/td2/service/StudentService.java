package org.td2.td2.service;

import org.springframework.stereotype.Service;
import org.td2.td2.model.Student;
import org.td2.td2.validator.StudentValidator;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final List<Student> studentsInMemory = new ArrayList<>();
    private final StudentValidator studentValidator;


    public StudentService(StudentValidator studentValidator) {
        this.studentValidator = studentValidator;
    }

    public List<Student> addStudents(List<Student> newStudents) {
        studentValidator.validateList(newStudents);

        studentsInMemory.addAll(newStudents);
        return new ArrayList<>(studentsInMemory);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(studentsInMemory);
    }
}
