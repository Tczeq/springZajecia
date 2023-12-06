package pl.sszlify.coding.teacher;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.teacher.model.Teacher;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public void create(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public List<Teacher> findAllByLanguage(Language language) {
        return teacherRepository.findAllByLanguagesContaining(language);
    }

    public Teacher findTeacherById(int teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with id " + teacherId + " not found"));
    }
}
