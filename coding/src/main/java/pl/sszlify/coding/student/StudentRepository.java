package pl.sszlify.coding.student;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sszlify.coding.student.model.Student;
import pl.sszlify.coding.teacher.model.Teacher;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findAllByTeacherContaining(Teacher teacher);
}


