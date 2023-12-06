package pl.sszlify.coding.student;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sszlify.coding.common.exception.LanguageMismatchException;
import pl.sszlify.coding.student.model.Student;
import pl.sszlify.coding.teacher.TeacherRepository;
import pl.sszlify.coding.teacher.model.Teacher;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public List<Student> findAll(){
        return studentRepository.findAll();
    }

    public void create(Student student, int teacherId){
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Teacher with id={0} not found", teacherId)));
        if (!teacher.getLanguages().contains(student.getLanguage())) {
            throw new LanguageMismatchException();
        }
        student.setTeacher(teacher);
        studentRepository.save(student);
    }

    public List<Student> findStudentsByTeacher(Teacher teacher) {
        return studentRepository.findAllByTeacher(teacher);
    }
}
