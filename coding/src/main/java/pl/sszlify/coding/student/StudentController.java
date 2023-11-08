package pl.sszlify.coding.student;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StrudentService strudentService;

    @GetMapping
    public String getAll(){
        return "student/list";
    }
}
