package pl.sszlify.coding.lesson.model;

import lombok.*;
import pl.sszlify.coding.common.Language;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson {
    private int id;
    private String name;
    private Set<Language> languages = new HashSet<>();
}
