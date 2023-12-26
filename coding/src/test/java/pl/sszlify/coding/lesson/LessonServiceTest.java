package pl.sszlify.coding.lesson;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.common.exception.LanguageMismatchException;
import pl.sszlify.coding.lesson.model.Lesson;
import pl.sszlify.coding.student.StudentRepository;
import pl.sszlify.coding.student.model.Student;
import pl.sszlify.coding.teacher.TeacherRepository;
import pl.sszlify.coding.teacher.model.Teacher;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @InjectMocks
    private LessonService lessonService;
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private StudentRepository studentRepository;
    @Captor
    private ArgumentCaptor<Lesson> lessonArgumentCaptor;
    @Test
    void testFindAll_HappyPath_ResultsInLessonFindAllLessons() {
        //given

        LocalDateTime localDateTime = LocalDateTime.of(2022, 12, 30, 12, 12);

        Student student = Student.builder()
                .firstName("Test")
                .lastName("Testowy")
                .language(Language.JAVA)
                .build();
        Teacher teacher = Teacher.builder()
                .languages(Set.of(student.getLanguage()))
                .build();
        Lesson toFind = Lesson.builder()
                .term(localDateTime)
                .student(student)
                .teacher(teacher)
                .build();

        when(lessonRepository.findAll()).thenReturn(List.of(toFind));

        //when
        List<Lesson> actualLessons = lessonService.findAll();

        //then
        verify(lessonRepository).findAll();
        assertEquals(List.of(toFind), actualLessons);
    }



    @Test
    void testFindAll_UnHappyPath_ResultsInTeacherNotFoundAllTeachers() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2022, 12, 30, 12, 12);

        Student student = Student.builder()
                .firstName("Test")
                .lastName("Testowy")
                .language(Language.JAVA)
                .build();
        Teacher teacher = Teacher.builder()
                .languages(Set.of(student.getLanguage()))
                .build();
        Lesson toFind = Lesson.builder()
                .term(localDateTime)
                .student(student)
                .teacher(teacher)
                .build();

        when(lessonRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //when
        List<Lesson> actualLessons = lessonService.findAll();

        //then
        verify(lessonRepository).findAll();
        assertNotEquals(List.of(toFind), actualLessons);
    }

    @Test
    void testCreate_HappyPath_ResultsInLessonBeingSaved(){
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 12, 30, 12, 12);

        Student student = Student.builder()
                .firstName("Test")
                .lastName("Testowy")
                .language(Language.JAVA)
                .build();
        Teacher teacher = Teacher.builder()
                .languages(Set.of(student.getLanguage()))
                .build();
        Lesson toSave = Lesson.builder()
                .term(localDateTime)
                .student(student)
                .teacher(teacher)
                .build();

        when(lessonRepository.findById(toSave.getId())).thenReturn(Optional.of(toSave));

        //when
        lessonService.create(toSave, teacher.getId(), student.getId());

        //then
        verify(teacherRepository).findWithLockingById(teacher.getId());
        verify(studentRepository).findWithLockingById(student.getId());

        verify(lessonRepository).save(lessonArgumentCaptor.capture());
        Lesson saved = lessonArgumentCaptor.getValue();
        assertEquals(toSave.getStudent(), saved.getStudent());
        assertEquals(toSave.getTeacher(), saved.getTeacher());
        assertEquals(toSave.getTerm(), saved.getTerm());
        assertEquals(toSave, saved);
    }

    @Test
    void testCreate_UnHappyPath_ResultsInLessonNotBeingSaved_WhenTeacherNotFound(){

        //given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 12, 30, 12, 12);

        Student student = Student.builder()
                .firstName("Test")
                .lastName("Testowy")
                .language(Language.JAVA)
                .build();
        Teacher teacher = Teacher.builder()
                .languages(Set.of(student.getLanguage()))
                .build();
        Lesson toSave = Lesson.builder()
                .term(localDateTime)
                .student(student)
                .teacher(teacher)
                .build();

        when(lessonRepository.findById(toSave.getId())).thenReturn(Optional.of(toSave));

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            //when
            lessonService.create(toSave, teacher.getId(), student.getId());
        });
    }

    @Test
    void testCreate_UnHappyPath_ResultsInLessonNotBeingSaved_WhenStudentNotFound(){

    }

    @Test
    void testCreate_UnHappyPath_ResultsInLessonNotBeingSaved_WhenTermIsFromPast(){

    }
}