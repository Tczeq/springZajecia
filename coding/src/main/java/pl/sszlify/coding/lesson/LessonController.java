package pl.sszlify.coding.lesson;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sszlify.coding.common.Language;
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
//        model.addAttribute("students", studentService.findStudentsByTeacher(teacher));
        return "lesson/form";
    }



    @PostMapping("/create")
    public String create(Lesson lesson, Model model) {
        if (lesson.getTerm().isBefore(LocalDateTime.now())) {
            model.addAttribute("today", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            model.addAttribute("languages", Language.values());
            model.addAttribute("errorMessage", "Date can't be from the past");
            return "lesson/form";
        }
        lessonService.create(lesson);
        return "redirect:/lessons";
    }

    @GetMapping(params = "student")
    @ResponseBody
    public List<LessonDto> getAll(@RequestParam Student student) {
        return lessonService.findAllByStudent(student).stream()
                .map(LessonDto::fromEntity)
                .toList();
    }

//    @GetMapping(params = "teacher")
//    @ResponseBody
//    public List<StudentDto> getStudentsByTeacher(@RequestParam Teacher teacher) {
//        return studentService.findStudentsByTeacher(teacher).stream()
//                .map(StudentDto::fromEntity)
//                .toList();
//    }

    @GetMapping(params = "teacher")
    @ResponseBody
    public List<StudentDto> getStudentsByTeacher(@RequestParam("teacher") int teacherId) {
        Teacher teacher = teacherService.findTeacherById(teacherId);
        return studentService.findStudentsByTeacher(teacher).stream()
                .map(StudentDto::fromEntity)
                .toList();
    }


}
