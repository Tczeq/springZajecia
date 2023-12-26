package pl.sszlify.coding.student;

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
import pl.sszlify.coding.student.model.Student;
import pl.sszlify.coding.teacher.TeacherRepository;
import pl.sszlify.coding.teacher.model.Teacher;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //zaprzęga do pracy Mockito (@Mock oraz @InjectMocks)
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Captor
    private ArgumentCaptor<Student> studentArgumentCaptor;

    //praktycznie ten sam efekt co metodą poniżem możemy uzyskać używając adnotacji @Mock oraz @InjectMocks
//    @BeforeEach
//    void init() {
//        studentRepository = mock(StudentRepository.class);
//        teacherRepository = mock(TeacherRepository.class);
//        studentService = new StudentService(studentRepository, teacherRepository);
//    }


    @Test
    void testCreate_HappyPath_ResultsInStudentBeingSaved() {
        //given
        int teacherId = 2;
        Student toSave = Student.builder()
                .firstName("Test")
                .lastName("Testowy")
                .language(Language.JAVA)
                .build();
        Teacher teacher = Teacher.builder()
                .languages(Set.of(toSave.getLanguage()))
                .build();
        when(teacherRepository.findWithLockingById(teacherId)).thenReturn(Optional.of(teacher));

        //when
        studentService.create(toSave, teacherId);

        //then
        verify(teacherRepository).findWithLockingById(teacherId);

        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student saved = studentArgumentCaptor.getValue();
        assertEquals(toSave.getFirstName(), saved.getFirstName());
        assertEquals(toSave.getLastName(), saved.getLastName());
        assertEquals(toSave.getLanguage(), saved.getLanguage());
        assertEquals(teacher, saved.getTeacher());
    }


    @Test
    void testCreate_UnHappyPath_ResultsInStudentNotBeingSaved_WhenTeacherNotFound() {
        //given
        int teacherId = 2;
        Student toSave = Student.builder()
                .firstName("Test")
                .lastName("Testowy")
                .language(Language.JAVA)
                .build();
        when(teacherRepository.findWithLockingById(teacherId)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            //when
            studentService.create(toSave, teacherId);
        });
    }

    @Test
    void testCreate_UnHappyPath_ResultsInStudentNotBeingSaved_WhenLanguageIsNotTheSame() {
        //given
        int teacherId = 2;
        Student toSave = Student.builder()
                .firstName("Test")
                .lastName("Testowy")
                .language(Language.JAVA)
                .build();
        Teacher teacher = Teacher.builder()
                .languages(Set.of(Language.PYTHON))
                .build();
        when(teacherRepository.findWithLockingById(teacherId)).thenReturn(Optional.of(teacher));

        //then
        assertThrows(LanguageMismatchException.class, () -> {
            //when
            studentService.create(toSave, teacherId);
        });
    }

    @Test
    void testFindStudentById_HappyPath_ResultsInStudentFound() {
        //given
        int studentId = 3;
        Student toFind = Student.builder()
                .firstName("Test")
                .lastName("Testowy")
                .language(Language.JAVA)
                .build();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(toFind));
        //when
        studentService.findStudentById(studentId);

        //then
        verify(studentRepository).findById(studentId);
    }

    @Test
    void testFindStudentById_UnHappyPath_ResultsInStudentNotFound() {
        //given
        int studentId = 3;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            //when
            studentService.findStudentById(studentId);
        });
    }

    @Test
    void testBringBackStudent_HappyPath_ResultsInStudentBroughtBack() {
        int studentId = 3;
        Student toFind = Student.builder()
                .firstName("Test")
                .lastName("Testowy")
                .language(Language.JAVA)
                .build();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(toFind));

        studentService.bringBackStudent(studentId);


        verify(studentRepository).findById(studentId);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student saved = studentArgumentCaptor.getValue();


        assertEquals(toFind.getFirstName(), saved.getFirstName());
        assertEquals(toFind.getLastName(), saved.getLastName());
        assertEquals(toFind.getLanguage(), saved.getLanguage());
    }


    @Test
    void testBringBackStudent_UnHappyPath_ResultsInStudentNotBroughtBack() {
        int studentId = 3;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            //when
            studentService.bringBackStudent(studentId);
        });
    }


    @Test
    void testFindStudentByTeacher_HappyPath_ResultsInStudentFound() {
        //given
        Student toFind = Student.builder()
                .firstName("Test")
                .lastName("Testowy")
                .language(Language.JAVA)
                .build();
        Teacher teacher = Teacher.builder()
                .languages(Set.of(toFind.getLanguage()))
                .build();

        List<Student> students = new ArrayList<>();
        students.add(toFind);

        when(studentRepository.findAllByTeacher(teacher)).thenReturn(students);

        //when
        List<Student> actualStudents = studentService.findStudentsByTeacher(teacher);

        //then
        verify(studentRepository).findAllByTeacher(teacher);
        assertEquals(students, actualStudents);
    }

    @Test
    void testFindStudentByTeacher_UnHappyPath_ResultsInStudentNotFound() {
        //given
        Student toFind = Student.builder()
                .firstName("Test")
                .lastName("Testowy")
                .language(Language.JAVA)
                .build();
        Teacher teacher = Teacher.builder()
                .languages(Set.of(toFind.getLanguage()))
                .build();

        List<Student> students = new ArrayList<>();
        students.add(toFind);

        when(studentRepository.findAllByTeacher(teacher)).thenReturn(Collections.EMPTY_LIST);

        //when
        List<Student> actualStudents = studentService.findStudentsByTeacher(teacher);

        //then
        verify(studentRepository).findAllByTeacher(teacher);
        assertTrue(actualStudents.isEmpty());
        assertNotEquals(students, actualStudents);
    }


    @Test
    void testDeleteById_HappyPath_ResultsInStudentFound() {
        int studentId = 3;

        studentService.deleteById(studentId);

        verify(studentRepository).deleteById(studentId);

    }

    @Test
    void testDeleteById_UnHappyPath_ResultsInStudentNotFound() {
        int studentId = 3;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        studentService.deleteById(studentId);

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            //when
            studentService.findStudentById(studentId);
        });

        verify(studentRepository).deleteById(studentId);

    }


    @Test
    void testFindAll_HappyPath_ResultsInStudentFoundAllStudents() {
        //given
        Student toFind = Student.builder()
                .firstName("Test")
                .lastName("Testowy")
                .language(Language.JAVA)
                .build();

        when(studentRepository.findAll()).thenReturn(List.of(toFind));

        //when
        List<Student> actualStudents = studentService.findAll();

        //then
        verify(studentRepository).findAll();
        assertEquals(List.of(toFind), actualStudents);
    }


    @Test
    void testFindAll_UnHappyPath_ResultsInStudentNotFoundAllStudents() {
        //given
        Student toFind = Student.builder()
                .firstName("Test")
                .lastName("Testowy")
                .language(Language.JAVA)
                .build();

        when(studentRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //when
        List<Student> actualStudents = studentService.findAll();

        //then
        verify(studentRepository).findAll();
        assertNotEquals(List.of(toFind), actualStudents);
    }
}