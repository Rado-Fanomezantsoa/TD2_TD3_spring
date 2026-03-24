package org.td2.td3.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.td2.td3.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(value = "name", required = false) String name) {

        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Le paramètre 'name' est obligatoire");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Welcome " + name);
    }


    @PostMapping("/students")
    public ResponseEntity<List<Student>> addStudents(@RequestBody List<Student> newStudents) {

        try {
            students.addAll(newStudents);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ArrayList<>(students));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }


    @GetMapping("/students")
    public ResponseEntity<?> getStudents(@RequestHeader(value = HttpHeaders.ACCEPT, required = false) String acceptHeader) {

        try {
            if (acceptHeader == null || acceptHeader.trim().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("L'entête 'Accept' est obligatoire");
            }

            if (acceptHeader.contains("text/plain")) {
                String names = students.stream()
                        .map(s -> s.getFirstName() + " " + s.getLastName())
                        .collect(Collectors.joining(", "));

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .header(HttpHeaders.CONTENT_TYPE, "text/plain")
                        .body(names.isEmpty() ? "Aucun étudiant enregistré" : names);

            } else if (acceptHeader.contains("application/json")) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .header(HttpHeaders.CONTENT_TYPE, "application/json")
                        .body(new ArrayList<>(students));     // Retourne la liste d'objets JSON

            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_IMPLEMENTED)
                        .body("Format non supporté. Formats acceptés : text/plain ou application/json");
            }

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne du serveur");
        }
    }
}
