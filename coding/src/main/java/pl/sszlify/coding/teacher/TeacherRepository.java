package pl.sszlify.coding.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sszlify.coding.teacher.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

}
