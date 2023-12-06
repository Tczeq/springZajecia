package pl.sszlify.coding.teacher;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.teacher.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    List<Teacher> findAllByLanguagesContaining(Language language);

//    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
//    Optional<Teacher> findWithLockingById(Long id);
}
