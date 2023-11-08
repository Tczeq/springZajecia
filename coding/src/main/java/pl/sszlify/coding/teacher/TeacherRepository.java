package pl.sszlify.coding.teacher;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import pl.sszlify.coding.teacher.model.Teacher;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TeacherRepository {

    private static final List<Teacher> DUMMY_TEACHER_DB = new ArrayList<>();

    public List<Teacher> findAll() {
        return DUMMY_TEACHER_DB;
    }

    public void save(Teacher teacher) {
        DUMMY_TEACHER_DB.add(teacher);
        teacher.setId(DUMMY_TEACHER_DB.size());
    }

    @PostConstruct
    private static void init() {
        Teacher teacher1 = Teacher.builder()
                .id(1)
                .firstName("Jan")
                .lastName("Kowalski")
                .build();
        Teacher teacher2 = Teacher.builder()
                .id(2)
                .firstName("Janusz")
                .lastName("Kowal")
                .build();
        DUMMY_TEACHER_DB.add(teacher1);
        DUMMY_TEACHER_DB.add(teacher2);
    }
}
