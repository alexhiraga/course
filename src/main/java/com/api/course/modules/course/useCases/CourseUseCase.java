package com.api.course.modules.course.useCases;

import com.api.course.exceptions.CourseFoundException;
import com.api.course.modules.course.controllers.CourseController;
import com.api.course.modules.course.dto.CreateCourseDTO;
import com.api.course.modules.course.entities.ActiveStatus;
import com.api.course.modules.course.entities.CourseEntity;
import com.api.course.modules.course.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CourseUseCase {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // check and return course
    private CourseEntity findCourseById(UUID id) {
        // check if course exists
        return this.courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso não encontrado!"));
    }

    @Transactional
    public CourseEntity create(CreateCourseDTO dto) {
        if(Objects.isNull(dto.getCategory()) || Objects.isNull(dto.getName())) {
            throw new RuntimeException("Invalid body");
        }

        // check if there is already a course with this name and category
        this.courseRepository
                .findByNameAndCategory(dto.getName(), dto.getCategory())
                .ifPresent((course) -> {
                    throw new CourseFoundException();
                });

        CourseEntity newCourse = new CourseEntity();
        newCourse.setName(dto.getName());
        newCourse.setCategory(dto.getCategory());
        newCourse.setActive(ActiveStatus.ACTIVE);

        // save in db
        return this.courseRepository.save(newCourse);
    }

    // get courses, it can be filtered by name and/or category
    public List<CourseEntity> getCourses(String name, String category) {
        if(Objects.isNull(name) && Objects.isNull(category)) {
            // case 1: return all the courses
            return this.courseRepository.findAll();
        } else if(Objects.nonNull(name) && Objects.isNull(category)) {
            // case 2: search by name
            return this.courseRepository.findByName("%" + name + "%");
        } else if (Objects.isNull(name) && Objects.nonNull(category)) {
            // case 3: search by category
            return this.courseRepository.findByCategory("%" + category + "%");
        } else {
            // case 4: search by name and category
            return this.courseRepository.seachByNameAndCategory("%" + name + "%", "%" + category + "%");
        }
    }

    // update course
    @Transactional
    public CourseEntity update(UUID id, CreateCourseDTO dto) {
        // get the course by id
        CourseEntity course = findCourseById(id);

        // set new values
        if(Objects.nonNull(dto.getCategory())) {
            course.setCategory(dto.getCategory());
        }
        if(Objects.nonNull(dto.getName())) {
            course.setName(dto.getName());
        }

        // save
        return this.courseRepository.save(course);
    }

    // delete course
    @Transactional
    public void delete(UUID id) {
        CourseEntity course = findCourseById(id);

        // delete the course
        this.courseRepository.deleteById(course.getId());
    }

    // toggle Active status
    @Transactional
    public CourseEntity toggleActive(UUID id) {
        CourseEntity course = findCourseById(id);

        // toggle boolean status
        if(course.getActive().equals(ActiveStatus.ACTIVE)) {
            course.setActive(ActiveStatus.INACTIVE);
        } else {
            course.setActive(ActiveStatus.ACTIVE);
        }

        // save
        return this.courseRepository.save(course);
    }

}
