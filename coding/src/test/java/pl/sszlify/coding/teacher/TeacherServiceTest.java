package pl.sszlify.coding.teacher;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.teacher.model.Teacher;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    @Captor
    private ArgumentCaptor<Teacher> teacherArgumentCaptor;

    @Test
    void testFindAll_HappyPath_ResultsInTeacherFoundAllTeachers() {
        //given
        Teacher toFind = Teacher.builder()
                .firstName("Test")
                .lastName("Testowy")
                .languages(Set.of(Language.JAVA, Language.JS))
                .build();

        when(teacherRepository.findAll()).thenReturn(List.of(toFind));

        //when
        List<Teacher> actualStudents = teacherService.findAll();

        //then
        verify(teacherRepository).findAll();
        assertEquals(List.of(toFind), actualStudents);
    }


    @Test
    void testFindAll_UnHappyPath_ResultsInTeacherNotFoundAllTeachers() {
        //given
        Teacher toFind = Teacher.builder()
                .firstName("Test")
                .lastName("Testowy")
                .languages(Set.of(Language.JAVA, Language.JS))
                .build();

        when(teacherRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //when
        List<Teacher> actualTeachers = teacherService.findAll();

        //then
        verify(teacherRepository).findAll();
        assertNotEquals(List.of(toFind), actualTeachers);
    }

    @Test
    void testCreate_HappyPath_ResultsInTeacherBeingSaved() {
        //given
        Teacher toSave = Teacher.builder()
                .firstName("Test")
                .lastName("Testowy")
                .languages(Set.of(Language.JAVA, Language.JS))
                .build();

        //when
        teacherService.create(toSave);

        //then

        verify(teacherRepository).save(teacherArgumentCaptor.capture());
        Teacher saved = teacherArgumentCaptor.getValue();
        assertEquals(toSave.getFirstName(), saved.getFirstName());
        assertEquals(toSave.getLastName(), saved.getLastName());
        assertEquals(toSave.getLanguages(), saved.getLanguages());
        assertEquals(toSave, saved);
    }


    @Test
    void testDeleteById_HappyPath_ResultsInTeacherFound() {
        int teacherId = 3;

        teacherService.deleteById(teacherId);

        verify(teacherRepository).deleteById(teacherId);

    }

    @Test
    void testDeleteById_UnHappyPath_ResultsInTeacherNotFound() {
        int teacherId = 3;

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        teacherService.deleteById(teacherId);

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            //when
            teacherService.findTeacherById(teacherId);
        });

        verify(teacherRepository).deleteById(teacherId);

    }


    @Test
    void testFindTeachersByLanguages_HappyPath_ResultsInTeachersFound() {
        //given
        Teacher toFind = Teacher.builder()
                .firstName("Test")
                .lastName("Testowy")
                .languages(Set.of(Language.JAVA, Language.JS))
                .build();
        Language language = Language.JAVA;

        List<Teacher> teachers = new ArrayList<>();
        teachers.add(toFind);

        when(teacherRepository.findAllByLanguagesContaining(language)).thenReturn(teachers);

        //when
        List<Teacher> actualTeachers = teacherService.findAllByLanguage(language);

        //then
        verify(teacherRepository).findAllByLanguagesContaining(language);
        assertEquals(teachers, actualTeachers);
    }

    @Test
    void testFindTeachersByLanguages_UnHappyPath_ResultsInTeachersNotFound() {
        //given
        Teacher toFind = Teacher.builder()
                .firstName("Test")
                .lastName("Testowy")
                .languages(Set.of(Language.JAVA, Language.JS))
                .build();
        Language language = Language.JAVA;

        List<Teacher> languages = new ArrayList<>();
        languages.add(toFind);

        when(teacherRepository.findAllByLanguagesContaining(language)).thenReturn(Collections.EMPTY_LIST);

        //when
        List<Teacher> actualLanguages = teacherService.findAllByLanguage(language);

        //then
        verify(teacherRepository).findAllByLanguagesContaining(language);
        assertTrue(actualLanguages.isEmpty());
        assertNotEquals(languages, actualLanguages);
    }


    @Test
    void testFindTeacherById_HappyPath_ResultsInTeacherFound() {
        //given
        int teacherId = 3;
        Teacher toFind = Teacher.builder()
                .firstName("Test")
                .lastName("Testowy")
                .languages(Set.of(Language.JAVA, Language.JS))
                .build();
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(toFind));
        //when
        teacherService.findTeacherById(teacherId);

        //then
        verify(teacherRepository).findById(teacherId);
    }

    @Test
    void testFindTeacherById_UnHappyPath_ResultsInTeacherNotFound() {
        //given
        int teacherId = 3;
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            //when
            teacherService.findTeacherById(teacherId);
        });
    }


    @Test
    void testFireTeacher_HappyPath_ResultsInTeacherFire() {
        int teacherId = 3;
        Teacher toFind = Teacher.builder()
                .firstName("Test")
                .lastName("Testowy")
                .languages(Set.of(Language.JAVA, Language.JS))
                .build();

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(toFind));

        teacherService.fireTeacher(teacherId);


        verify(teacherRepository).findById(teacherId);
        verify(teacherRepository).save(teacherArgumentCaptor.capture());
        Teacher saved = teacherArgumentCaptor.getValue();


        assertEquals(toFind.getFirstName(), saved.getFirstName());
        assertEquals(toFind.getLastName(), saved.getLastName());
        assertEquals(toFind.getLanguages(), saved.getLanguages());
        assertEquals(toFind.getStudents(), saved.getStudents());
        assertTrue(saved.isFired());
    }


    @Test
    void testFireTeacher_UnHappyPath_ResultsInTeacherNotFire() {
        int teacherId = 3;

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            //when
            teacherService.fireTeacher(teacherId);
        });
    }


    @Test
    void testHireTeacher_HappyPath_ResultsInTeacherHire() {
        int teacherId = 3;
        Teacher toFind = Teacher.builder()
                .firstName("Test")
                .lastName("Testowy")
                .languages(Set.of(Language.JAVA, Language.JS))
                .build();

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(toFind));

        teacherService.hireTeacher(teacherId);


        verify(teacherRepository).findById(teacherId);
        verify(teacherRepository).save(teacherArgumentCaptor.capture());
        Teacher saved = teacherArgumentCaptor.getValue();


        assertEquals(toFind.getFirstName(), saved.getFirstName());
        assertEquals(toFind.getLastName(), saved.getLastName());
        assertEquals(toFind.getLanguages(), saved.getLanguages());
        assertEquals(toFind.getStudents(), saved.getStudents());
        assertFalse(saved.isFired());
    }


    @Test
    void testHireTeacher_UnHappyPath_ResultsInTeacherNotHire() {
        int teacherId = 3;

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            //when
            teacherService.hireTeacher(teacherId);
        });
    }


    @Test
    void testUpdate_HappyPath_ResultsInStudentBeingUpdated() {
        //given
        int teacherId = 3;

        Teacher actualTeacher = Teacher.builder()
                .id(teacherId)
                .firstName("Test1")
                .lastName("Testowy1")
                .languages(Set.of(Language.C, Language.JS))
                .build();

        Teacher toSave = Teacher.builder()
                .id(teacherId)
                .firstName("Test2")
                .lastName("Testowy2")
                .languages(Set.of(Language.JAVA, Language.PYTHON))
                .build();


        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(actualTeacher));

        //when
        teacherService.update(toSave);

        //then
        verify(teacherRepository).findById(teacherId);
        verify(teacherRepository).save(teacherArgumentCaptor.capture());

        Teacher saved = teacherArgumentCaptor.getValue();
        assertEquals(toSave.getFirstName(), saved.getFirstName());
        assertEquals(toSave.getLastName(), saved.getLastName());
        assertEquals(toSave.getLanguages(), saved.getLanguages());
        assertEquals(teacherId, saved.getId());
    }

    @Test
    void testUpdate_UnHappyPath_ResultsInStudentNotUpdated() {
        //given
        int teacherId = 3;
        Teacher toUpdate = Teacher.builder()
                .id(teacherId)
                .firstName("Test2")
                .lastName("Testowy2")
                .languages(Set.of(Language.JAVA, Language.PYTHON))
                .build();


        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            //when
            teacherService.update(toUpdate);
        });

    }


}