package pl.sszlify.coding.student;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import pl.sszlify.coding.student.model.Student;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {
    private static final List<Student> DUMMY_STUDENT_DB = new ArrayList<>();

    public List<Student> findAll(){
        return DUMMY_STUDENT_DB;
    }

    public void save(Student student){
        DUMMY_STUDENT_DB.add(student);
        student.setId(DUMMY_STUDENT_DB.size());
    }

    @PostConstruct
    private static void init(){
        Student student1 = Student.builder()
                .id(1)
                .firstName("Maciek")
                .lastName("Nowak")
                .build();
        Student student2 = Student.builder()
                .id(2)
                .firstName("Kamil")
                .lastName("Zajac")
                .build();
        DUMMY_STUDENT_DB.add(student1);
        DUMMY_STUDENT_DB.add(student2);
    }
}
