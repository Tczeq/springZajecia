package pl.sszlify.coding.lesson;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sszlify.coding.lesson.model.Lesson;
import pl.sszlify.coding.student.StudentRepository;
import pl.sszlify.coding.teacher.TeacherRepository;

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

    public void create(Lesson lesson) {
        lessonRepository.save(lesson);
    }
}
