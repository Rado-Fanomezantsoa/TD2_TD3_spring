package org.td2.td2.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/students")
    public ResponseEntity<String> getStudents(@RequestHeader(value = HttpHeaders.ACCEPT, required = false) String acceptHeader) {

        if (acceptHeader != null && acceptHeader.contains("text/plain")) {
            String names = students.stream()
                    .map(s -> s.getFirstName() + " " + s.getLastName())
                    .collect(Collectors.joining(", "));

            return ResponseEntity.ok(names.isEmpty() ? "Aucun étudiant" : names);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("Format non supporté.");
    }
}
