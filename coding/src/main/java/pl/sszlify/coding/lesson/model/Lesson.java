package pl.sszlify.coding.lesson.model;

import jakarta.persistence.*;
import lombok.*;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.student.model.Student;
import pl.sszlify.coding.teacher.model.Teacher;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//jQuery - wybieramy nauczyciela i przeladowuje nam studentow dla danego anuczyciela
    //Asocjacja
    //walidacja lekcji
    private LocalDateTime term;
    @ManyToOne
    private Teacher teacher;
    @ManyToMany
//    @JoinTable(
//            name = "lesson_student",
//            joinColumns = @JoinColumn(name = "lesson_id"),
//            inverseJoinColumns = @JoinColumn(name = "student_id")
//    )
    private Set<Student> students = new HashSet<>();

//    @ManyToOne
//    private Student student;

}
