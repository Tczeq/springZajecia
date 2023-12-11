package pl.sszlify.coding.teacher.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
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
@SQLDelete(sql = "UPDATE teacher SET fired = 1 WHERE id = ?")
@Where(clause = "fired = false")
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

    @Column(name = "fired")
    private boolean fired = false;

    @Version
    private Integer version;
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
