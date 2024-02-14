package com.api.course.modules.course.controllers;

import com.api.course.modules.course.dto.CreateCourseDTO;
import com.api.course.modules.course.entities.CourseEntity;
import com.api.course.modules.course.useCases.CourseUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cursos")
public class CourseController {

    private final CourseUseCase courseUseCase;

    @Autowired
    public CourseController(CourseUseCase courseUseCase) {
        this.courseUseCase = courseUseCase;
    }

    @PostMapping
    public ResponseEntity<CourseEntity> create(@Valid @RequestBody CreateCourseDTO dto) {
//        try {
//            var result = this.courseUseCase.create(dto);
//            return ResponseEntity.ok().body(result);
//        } catch(Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
        return ResponseEntity.ok(this.courseUseCase.create(dto));
    }
}
