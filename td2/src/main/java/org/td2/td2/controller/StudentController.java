package org.td2.td2.controller;

import org.springframework.web.bind.annotation.*;
import org.td2.td2.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {
    private final List<Student> students = new ArrayList<>();
    @GetMapping("/welcome")
    public String welcome(@RequestParam("name") String name) {
        return "Welcome " + name;
    }

    @PostMapping("/students")
    public String addStudents(@RequestBody List<Student> newStudents) {
        students.addAll(newStudents);
        String names = students.stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.joining(", "));

        return names.isEmpty() ? "Aucun étudiant enregistré" : names;
    }
}
