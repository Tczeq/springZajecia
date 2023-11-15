package pl.sszlify.coding.lesson;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.lesson.model.Lesson;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("lessons", lessonService.findAll());
        return "lesson/list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        model.addAttribute("languages", Language.values());
        return "lesson/form";
    }

    @PostMapping("/create")
    public String create(Lesson lesson) {
        lessonService.create(lesson);
        return "redirect:/lessons";
    }
}
