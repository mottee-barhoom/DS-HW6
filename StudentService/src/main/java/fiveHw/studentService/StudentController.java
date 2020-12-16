/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiveHw.studentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author M M BARHOOM
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    private Long firstId = 1L;
    private String firstName = "Mottee BARHOOM";
    private int firstAge = 23;
    private Gender firstGender = Gender.MALE;

    private Student firstStudent = new Student(firstId, firstName, firstAge, firstGender);
    private List<Student> students = new ArrayList<>();

    private static final Logger LOG = LogManager.getLogger(StudentController.class.getName());

    public StudentController() {
        students.add(firstStudent);
    }

    @GetMapping
    public List<Student> findAll() {
        return students;
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable("id") Long id) {
         String okResponse = "ooooooooook Student Founded" + new Date();
        String noResponse = "noooooooooo Student " + id +" Not Founded" + new Date();
        
         Student student = students.stream().filter(it -> it.getId().equals(id)).findFirst().get();
         if (student != null)
        {
            LOG.log(Level.INFO, student+" -> "+okResponse);
        }else {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error(noResponse + e.getStackTrace());
            }
        }
         
         return student;
    }

    @PostMapping
    public Student add(@RequestBody Student s) {
        String okResponse = "ooooooooook Student Added" + new Date();
        String noResponse = "noooooooooo Student "  +" Not Added" + new Date();
        
        long index;
        if (students.size() > 0) {
            index = students.get(students.size() - 1).getId();
        } else {
            index = 0;
        }
        s.setId(index + 1);
        boolean bol = students.add(s);
        if (bol == true)
        {
            LOG.log(Level.INFO," -> "+okResponse);
        }else {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error(noResponse + e.getStackTrace());
            }
        }
        return s;
    }

}
