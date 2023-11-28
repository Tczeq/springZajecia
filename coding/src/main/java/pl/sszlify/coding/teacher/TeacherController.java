package pl.sszlify.coding.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.teacher.model.Teacher;
import pl.sszlify.coding.teacher.model.dto.TeacherDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        return "teacher/list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        model.addAttribute("languages", Language.values());
        return "teacher/form";
    }

    @PostMapping("/create")
    public String create(Teacher teacher) {
        teacherService.create(teacher);
        return "redirect:/teachers";
    }

    @GetMapping(params = "language")
    @ResponseBody
    public List<TeacherDto> getAll(@RequestParam Language language) {
        return teacherService.findAllByLanguage(language).stream()
                .map(TeacherDto::fromEntity)
                .toList();
    }
}
