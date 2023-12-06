package pl.sszlify.coding.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.lesson.model.Lesson;
import pl.sszlify.coding.student.model.Student;
import pl.sszlify.coding.teacher.model.Teacher;

import java.util.List;


public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    List<Lesson> findAllByStudentsContaining(Student student);
}
