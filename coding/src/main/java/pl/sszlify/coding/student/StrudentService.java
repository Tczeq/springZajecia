package pl.sszlify.coding.student;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sszlify.coding.student.model.Student;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StrudentService {

    private final StudentRepository studentRepository;

    public List<Student> findAll(){
        return studentRepository.findAll();
    }

    public void create(Student student){
        studentRepository.save(student);
    }
}
