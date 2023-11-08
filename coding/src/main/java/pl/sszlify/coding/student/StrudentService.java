package pl.sszlify.coding.student;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StrudentService {

    private final StudentRepository studentRepository;
}
