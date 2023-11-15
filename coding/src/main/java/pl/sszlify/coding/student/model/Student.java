package pl.sszlify.coding.student.model;

import lombok.*;
import pl.sszlify.coding.common.Language;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private Set<Language> languages = new HashSet<>();
}
