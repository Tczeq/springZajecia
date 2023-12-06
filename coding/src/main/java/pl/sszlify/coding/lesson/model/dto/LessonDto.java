package pl.sszlify.coding.lesson.model.dto;

import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import pl.sszlify.coding.lesson.model.Lesson;
import pl.sszlify.coding.student.model.Student;
import pl.sszlify.coding.teacher.model.Teacher;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class LessonDto {

    private int id;
    private LocalDateTime term;
    private Teacher teacher;
    private Set<Student> students = new HashSet<>();

    public static LessonDto fromEntity(Lesson lesson) {
        return LessonDto.builder()
                .id(lesson.getId())
                .term(lesson.getTerm())
                .teacher(lesson.getTeacher())
                .students(lesson.getStudents())
                .build();
    }
}
