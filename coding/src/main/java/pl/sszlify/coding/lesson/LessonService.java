package pl.sszlify.coding.lesson;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sszlify.coding.lesson.model.Lesson;
import pl.sszlify.coding.student.StudentRepository;
import pl.sszlify.coding.student.model.Student;
import pl.sszlify.coding.teacher.TeacherRepository;
import pl.sszlify.coding.teacher.model.Teacher;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    public void create(Lesson lesson, int teacherId, int studentId) {
        // TODO: 06.12.2023 weryfikacja, czy termin nie jest w przeszłości
//        if(lesson.)
        Teacher teacher = teacherRepository.findById(teacherId) // TODO: 06.12.2023 wywołanie powinno być z lockiem
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Teacher with id={0} not found", teacherId)));
        // TODO: 06.12.2023 weryfikacja, czy termin nie pokrywa się z żadną inną istniejącą lekcją dla tego nauczyciela
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Student with id={0} not found", studentId)));
        lesson.setStudent(student);
        lesson.setTeacher(teacher);
        lessonRepository.save(lesson);
    }


    public List<Lesson> findAllByStudent(Student student) {
        return lessonRepository.findAllByStudent(student);
    }

    public boolean availableTerm(LocalDateTime lessonTime, Teacher teacher) {
        for (Lesson lesson : lessonRepository.findAllByTeacher(teacher)) {
            if (lesson.getTerm().equals(lessonTime)) {
                return false;
            }
        }
        return true;
    }

    public void deleteById(int idToDelete) {
        lessonRepository.deleteById(idToDelete);
    }
}
