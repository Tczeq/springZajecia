package pl.sszlify.coding.student;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.student.model.Student;
import pl.sszlify.coding.teacher.TeacherService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final TeacherService teacherService;

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("students", studentService.findAll());
        return "student/list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model){
        model.addAttribute("languages", Language.values());
        model.addAttribute("teachers", teacherService.findAll());
        return "student/form";
    }

    @PostMapping("/create")
    public String create(Student student, @RequestParam int teacherId){
        studentService.create(student, teacherId);
        return "redirect:/students";
    }
}
