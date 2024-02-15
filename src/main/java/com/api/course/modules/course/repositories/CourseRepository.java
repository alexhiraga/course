package com.api.course.modules.course.repositories;

import com.api.course.modules.course.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    Optional<CourseEntity> findByNameAndCategory(String name, String category);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM course c " +
                    "WHERE c.name LIKE :name "
    )
    List<CourseEntity> findByName(String name);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM course c " +
                    "WHERE c.category LIKE :category "
    )
    List<CourseEntity> findByCategory(String category);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM course c " +
                    "WHERE c.name LIKE :name " +
                    "AND c.category LIKE :category "
    )
    List<CourseEntity> seachByNameAndCategory(String name, String category);

}
