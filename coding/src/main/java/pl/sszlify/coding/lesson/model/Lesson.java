package pl.sszlify.coding.lesson.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
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
//@SQLDelete(sql = "UPDATE lesson SET deleted = 1 WHERE id = ?")
@SQLDelete(sql = "UPDATE lesson SET deleted = 1 WHERE id = ? AND deleted = ?")
@Where(clause = "deleted = false")
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

    @ManyToOne
    private Student student;

    @Version
    private Integer version;

    @Column(name = "deleted")
    private boolean isDeleted = false;
}
