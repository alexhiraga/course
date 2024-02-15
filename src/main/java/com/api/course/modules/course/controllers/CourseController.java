package com.api.course.modules.course.controllers;

import com.api.course.modules.course.dto.CreateCourseDTO;
import com.api.course.modules.course.entities.CourseEntity;
import com.api.course.modules.course.useCases.CourseUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        return ResponseEntity.ok(this.courseUseCase.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<CourseEntity>> getCourses(
            @Valid @RequestParam(required = false) String name,
            @Valid @RequestParam(required = false) String category)
    {
        return ResponseEntity.ok(this.courseUseCase.getCourses(name, category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseEntity> update(
            @PathVariable UUID id,
            @RequestBody CreateCourseDTO dto
    ) {
        return ResponseEntity.ok(this.courseUseCase.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id
    ) {
        try {
            this.courseUseCase.delete(id);
            return ResponseEntity.ok(id + " deleted");
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<CourseEntity> toggleStatus(@PathVariable UUID id) {
        return ResponseEntity.ok(this.courseUseCase.toggleActive(id));
    }
}
