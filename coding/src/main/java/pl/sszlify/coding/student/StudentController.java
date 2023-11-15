package pl.sszlify.coding.student;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.student.model.Student;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StrudentService strudentService;

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("students", strudentService.findAll());
        return "student/list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model){
        model.addAttribute("languages", Language.values());
        return "student/form";
    }

    @PostMapping("/create")
    public String create(Student student){
        strudentService.create(student);
        return "redirect:/students";
    }
}
