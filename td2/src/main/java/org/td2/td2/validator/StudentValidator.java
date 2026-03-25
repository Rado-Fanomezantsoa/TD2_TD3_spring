package org.td2.td2.validator;


import org.springframework.stereotype.Component;
import org.td2.td2.exception.BadRequestException;
import org.td2.td2.model.Student;

import java.util.List;

@Component
public class StudentValidator {

    public void validateList(List<Student> students) {
        if (students == null || students.isEmpty()) {
            throw new BadRequestException("La liste des étudiants ne peut pas être vide");
        }

        for (Student student : students) {
            validateSingle(student);
        }
    }

    private void validateSingle(Student student) {
        if (student == null) {
            throw new BadRequestException("Un étudiant ne peut pas être null");
        }

        if (student.getReference() == null || student.getReference().trim().isBlank()) {
            throw new BadRequestException("La référence de l'étudiant est obligatoire");
        }

        if (student.getFirstName() == null || student.getFirstName().trim().isBlank()) {
            throw new BadRequestException("Le prénom de l'étudiant est obligatoire");
        }

        if (student.getLastName() == null || student.getLastName().trim().isBlank()) {
            throw new BadRequestException("Le nom de l'étudiant est obligatoire");
        }
    }
}
