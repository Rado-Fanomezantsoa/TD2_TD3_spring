package org.td2.td2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.td2.td2.exception.BadRequestException;
import org.td2.td2.model.Student;
import org.td2.td2.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<List<Student>> createStudents(@RequestBody List<Student> newStudents) {
        try {
            List<Student> allStudents = studentService.addStudents(newStudents);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(allStudents);

        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
}
