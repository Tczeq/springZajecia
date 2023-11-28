package pl.sszlify.coding.teacher.model;

import jakarta.persistence.*;
import lombok.*;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.student.model.Student;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "teacher_language", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "language")
    private Set<Language> languages = new HashSet<>();

    @OneToMany(mappedBy = "teacher")
    private Set<Student> students;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
