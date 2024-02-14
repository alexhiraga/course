package com.api.course.modules.course.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCourseDTO {
    private String name;
    private String category;
}
