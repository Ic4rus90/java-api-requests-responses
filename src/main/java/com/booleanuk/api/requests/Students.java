package com.booleanuk.api.requests;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("students")
public class Students {
    private List<Student> students = new ArrayList<>(){{
        add(new Student("Nathan", "King"));
        add(new Student("Dave", "Ames"));
    }};

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@RequestBody Student student) {
        this.students.add(student);

        return student;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Student> getAll() {
        return this.students;
    }


    @GetMapping("/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    public Student getStudent(@PathVariable String firstName){
        Optional<Student> student = students.stream().filter(s -> s.getFirstName().equals(firstName)).findFirst();
        return student.orElse(null);
    }

    @PutMapping("/{firstName}")
    @ResponseStatus(HttpStatus.CREATED)
    public Student updateStudent(@PathVariable String firstName, @RequestBody Student student){

        Optional<Student> studentToUpdate = students.stream().filter(s -> s.getFirstName().equals(firstName)).findFirst();
        if (studentToUpdate.isPresent()){
            studentToUpdate.get().setLastName(student.getLastName());
            return studentToUpdate.get();
        } else {
            return null;
        }
    }

    @DeleteMapping("/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    public Student delete(@PathVariable String firstName){
        Student student;
        for (Student s : students){
            if (s.getFirstName().equals(firstName)){
                student = s;
                students.remove(s);
                return student;
            }
        }
        return null;
    }
}
