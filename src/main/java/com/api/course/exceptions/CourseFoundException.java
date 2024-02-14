package com.api.course.exceptions;

public class CourseFoundException extends RuntimeException {
    public CourseFoundException() {
        super("Curso com esse nome e categoria já existem!");
    }
}
