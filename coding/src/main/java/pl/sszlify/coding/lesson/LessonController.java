package pl.sszlify.coding.lesson;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.lesson.exception.InvalidDate;
import pl.sszlify.coding.lesson.model.Lesson;
import pl.sszlify.coding.lesson.model.dto.LessonDto;
import pl.sszlify.coding.student.StudentService;
import pl.sszlify.coding.student.model.Student;
import pl.sszlify.coding.student.model.dto.StudentDto;
import pl.sszlify.coding.teacher.TeacherService;
import pl.sszlify.coding.teacher.model.Teacher;
import pl.sszlify.coding.teacher.model.dto.TeacherDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("lessons", lessonService.findAll());
        return "lesson/list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        model.addAttribute("today", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        model.addAttribute("languages", Language.values());
        model.addAttribute("teachers", teacherService.findAll());
        return "lesson/form";
    }




    /*
    * Dodaje tutaj Model, aby mozna bylo po froncie wyswietlac wiadomosci
    * */
    @PostMapping("/create")
    public String create(Lesson lesson, @RequestParam int teacherId, @RequestParam int studentId, Model model) {
//        if (lesson.getTerm().isBefore(LocalDateTime.now())) {
//            return modelForCreateLesson(model, "Data nie może być z przeszłości");
//        }
//
//        if (!lessonService.availableTerm(lesson.getTerm(), lesson.getTeacher())) {
//            return modelForCreateLesson(model,"Nauczyciel jest już zajęty w tym terminie");
//        }
//        model.addAttribute("today", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//        model.addAttribute("languages", Language.values());
//        model.addAttribute("errorMessage", "Data nie może być z przeszłości");
//        return "lesson/form";


        try {

            lessonService.create(lesson, teacherId, studentId);
            return "redirect:/lessons";
        } catch (InvalidDate e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "lesson/form";
        }


    }


    private String modelForCreateLesson(Model model, String message) {
        model.addAttribute("today", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        model.addAttribute("languages", Language.values());
        model.addAttribute("errorMessage", message);
        return "lesson/form";
    }

//    @GetMapping(params = "teacher")
//    @ResponseBody
//    public List<StudentDto> getStudentsByTeacher(@RequestParam Teacher teacher) {
//        return studentService.findStudentsByTeacher(teacher).stream()
//                .map(StudentDto::fromEntity)
//                .toList();
//    }

    @DeleteMapping
    @ResponseBody
    public void deleteById(@RequestParam int idToDelete) {
        lessonService.deleteById(idToDelete);
    }


}
