package pl.sszlify.coding.teacher;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

//    public void deleteById(int idToDelete) {
//        teacherRepository.deleteById(idToDelete);
//    }


    @Transactional
    public void deleteById(int idToDelete) {
        teacherRepository.deleteById(idToDelete);
    }



    public List<Teacher> findAllByLanguage(Language language) {
        return teacherRepository.findAllByLanguagesContaining(language);
    }

    public Teacher findTeacherById(int teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with id " + teacherId + " not found"));
    }

    public void fireTeacher(int teacherId){
        Teacher teacher = findTeacherById(teacherId);
        teacher.setFired(true);
        teacherRepository.save(teacher);

    }

    public void hireTeacher(int teacherId) {
        Teacher teacher = findTeacherById(teacherId);
        teacher.setFired(false);
        teacherRepository.save(teacher);
    }


    public void update(Teacher updatedTeacher) {
        Teacher existingTeacher = teacherRepository.findById(updatedTeacher.getId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher with id " + updatedTeacher.getId() + " not found"));

        existingTeacher.setFirstName(updatedTeacher.getFirstName());
        existingTeacher.setLastName(updatedTeacher.getLastName());
        existingTeacher.setLanguages(updatedTeacher.getLanguages());

        teacherRepository.save(existingTeacher);
    }

}
