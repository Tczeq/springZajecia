package pl.sszlify.coding.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.teacher.model.Teacher;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    List<Teacher> findAllByLanguagesContaining(Language language);
}
