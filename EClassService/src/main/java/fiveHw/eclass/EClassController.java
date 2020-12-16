/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiveHw.eclass;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author M M BARHOOM
 */
@RestController
@RequestMapping("/classes")
public class EClassController {

    private List<EClass> classes = new ArrayList<>();
    private static final Logger LOG = LogManager.getLogger(EClassController.class.getName());

    private Long firstId = 1L;
    private String fitstClass_name = "fifth year Class";
    private List<Long> firstStudent_id = new ArrayList<Long>();
    private List<Long> firstCourse_id = new ArrayList<Long>();

    public EClassController() {
        firstStudent_id.add(1L);
        firstCourse_id.add(1L);
        EClass firstEClass = new EClass(firstId, fitstClass_name, firstStudent_id, firstCourse_id);
        classes.add(firstEClass);
    }

    @Autowired
    StudentInfo studentInfo;

    @Autowired
    CourseInfo courseInfo;

    @GetMapping
    public List<EClass> findAll() {
        return classes;
    }

    @GetMapping("/{id}")
    public EClassInfo getById(@RequestParam("id") Long eclass_id) {
        String okResponse = "ooooooooook eclass Founded" + new Date();
        String noResponse = "noooooooooo eclass " + eclass_id + " Not Founded" + new Date();

        EClass eclass = classes //get eclass
                .stream()
                .filter(it -> it.getId().equals(eclass_id))
                .findFirst()
                .get();
        List<Student> students = eclass // get students
                .getStudent_id()
                .stream()
                .map(id -> {
                    return studentInfo.getStudentById(id);
                })
                .collect(Collectors.toList()); // request to student service
        List<Course> courses = eclass // get courses
                .getCourse_id().stream()
                .map(id -> {
                    return courseInfo.getCourseById(id);
                })
                .collect(Collectors.toList());// request to course service
        EClassInfo eclassInfo = new EClassInfo(eclass_id, eclass.getClass_name(), students, courses);

        if (eclassInfo != null) {
            LOG.log(Level.INFO, eclassInfo + " -> " + okResponse);
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error(noResponse + e.getStackTrace());
            }
        }

        return eclassInfo;
    }

    @GetMapping("/{name}")
    public EClassInfo getByName(@RequestParam("name") String eclass_name) {
        String okResponse = "ooooooooook eclass Founded" + new Date();
        String noResponse = "noooooooooo eclass " + eclass_name + " Not Founded" + new Date();
        EClass eclass = classes //get eclass
                .stream()
                .filter(it -> it.getClass_name().equals(eclass_name))
                .findFirst()
                .get();
        List<Student> students = eclass //get students
                .getStudent_id().stream()
                .map(id -> {
                    return studentInfo.getStudentById(id);
                })
                .collect(Collectors.toList());// request to student service
        List<Course> courses = eclass // get courses
                .getCourse_id().stream()
                .map(id -> {
                    return courseInfo.getCourseById(id);
                })
                .collect(Collectors.toList());// request to course service

        EClassInfo eclassInfo = new EClassInfo(eclass.getId(), eclass_name, students, courses);
        if (eclassInfo != null) {
            LOG.log(Level.INFO, eclassInfo + " -> " + okResponse);
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error(noResponse + e.getStackTrace());
            }
        }

        return eclassInfo;
    }

    @PostMapping
    public EClass add(@RequestBody EClass eclass) {
        String okResponse = "ooooooooook Eclass Added" + new Date();
        String noResponse = "noooooooooo EClass  " + " " + " Not Added" + new Date();
        
        long index;
        if (classes.size() > 0) {
            index = classes.get(classes.size() - 1).getId();
        } else {
            index = 0;
        }
        
        eclass.setId(index + 1);
        boolean bol = classes.add(eclass);
        if (bol == true) {
            LOG.log(Level.INFO, " -> " + okResponse);
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error(noResponse + e.getStackTrace());
            }
        }
        return eclass;
    }

}
