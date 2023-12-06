package pl.sszlify.coding.student.model;

import jakarta.persistence.*;
import lombok.*;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.lesson.model.Lesson;
import pl.sszlify.coding.teacher.model.Teacher;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "student")
    private Set<Lesson> lessons = new HashSet<>();

}
