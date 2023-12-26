package pl.sszlify.coding.student;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void create(Student student, int teacherId){
        Teacher teacher = teacherRepository.findWithLockingById(teacherId)
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


    @Transactional
    public void deleteById(int idToDelete) {
        studentRepository.deleteById(idToDelete);
    }

    public Student findStudentById(int studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with id " + studentId + " not found"));
    }


    @Transactional
    public void deleteStudent(int studentId){
        Student student = findStudentById(studentId);
        student.setDeleted(true);
        studentRepository.save(student);

    }


    @Transactional
    public void bringBackStudent(int studentId) {
        Student student = findStudentById(studentId);
        student.setDeleted(false);
        studentRepository.save(student);
    }
}
