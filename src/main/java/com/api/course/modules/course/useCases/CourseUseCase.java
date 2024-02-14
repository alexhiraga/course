package com.api.course.modules.course.useCases;

import com.api.course.exceptions.CourseFoundException;
import com.api.course.modules.course.controllers.CourseController;
import com.api.course.modules.course.dto.CreateCourseDTO;
import com.api.course.modules.course.entities.CourseEntity;
import com.api.course.modules.course.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CourseUseCase {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
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

        // save in db
        return this.courseRepository.save(newCourse);
    }
}
