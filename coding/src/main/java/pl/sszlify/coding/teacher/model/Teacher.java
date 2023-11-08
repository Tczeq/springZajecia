package pl.sszlify.coding.teacher.model;

import lombok.*;
import pl.sszlify.coding.common.Language;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {

    private int id;
    private String firstName;
    private String lastName;
    private Set<Language> languages = new HashSet<>();
}
