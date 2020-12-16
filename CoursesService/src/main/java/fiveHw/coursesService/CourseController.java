/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiveHw.coursesService;

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
@RequestMapping("/courses")
public class CourseController {

    private Long firstId = 1L;
    private String firstTitle = "N";
    private String firstDescription = "Network Course";
    private Date firstStart_at = new Date();
    private Date firstEnd_at = new Date();
    private String firstLecturer = "Dr.SABA";
    private Course firstCourse = new Course(firstId, firstTitle, firstDescription, firstStart_at, firstEnd_at, firstLecturer);
    private List<Course> courses = new ArrayList<>();

        private static final Logger LOG = LogManager.getLogger(CourseController.class.getName());

    public CourseController() {
        courses.add(firstCourse);
    }

    
    @GetMapping
    public List<Course> findAll() {
        
        return courses;
    }

    @GetMapping("/{id}")
    public Course getById(@PathVariable("id") Long id) {
        
        Course course= courses.stream().filter(it -> it.getId().equals(id)).findFirst().get();
        String okResponse = "ooooooooook Course Founded" + new Date();
        String noResponse = "noooooooooo Course " + id +" Not Founded" + new Date();
        if (course != null)
        {
            LOG.log(Level.INFO, course+" -> "+okResponse);
        }else {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error(noResponse + e.getStackTrace());
            }
        }
        
        return course;
    }

    @PostMapping
    public Course add(@RequestBody Course c) {
        long index;
          String okResponse = "ooooooooook Course Added" + new Date();
        String noResponse = "noooooooooo Course " + " " +" Not Added" + new Date();
        if (courses.size() > 0) {
            index = courses.get(courses.size() - 1).getId();
        } else {
            index = 0;
        }
        c.setId(index + 1);
        boolean bol = courses.add(c);
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
        return c;

    }
}
