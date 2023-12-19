package pl.sszlify.coding.student.model.dto;


import lombok.Builder;
import lombok.Getter;
import pl.sszlify.coding.common.Language;
import pl.sszlify.coding.student.model.Student;

@Getter
@Builder
public class StudentDto {
    private int id;
    private String firstName;
    private String lastName;
    private Language language;
    private int teacherId;
    private boolean deleted;

    public static StudentDto fromEntity(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .language(student.getLanguage())
                .teacherId(student.getTeacher() != null ? student.getTeacher().getId() : null)
                .deleted(student.isDeleted())
                .build();
    }
}