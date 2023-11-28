package pl.sszlify.coding.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sszlify.coding.lesson.model.Lesson;


public interface LessonRepository extends JpaRepository<Lesson, Integer> {
//    private static final List<Lesson> DUMMY_LESSON_DB = new ArrayList<>();
//
//    public List<Lesson> findAll() {
//        return DUMMY_LESSON_DB;
//    }
//
//    public void save(Lesson lesson) {
//        DUMMY_LESSON_DB.add(lesson);
//        lesson.setId(DUMMY_LESSON_DB.size());
//    }
//
//    @PostConstruct
//    private static void init(){
//        Lesson lesson1 = Lesson.builder()
//                .id(1)
//                .name("JavaSuper")
//                .build();
//        Lesson lesson2 = Lesson.builder()
//                .id(2)
//                .name("JSnow")
//                .build();
//        DUMMY_LESSON_DB.add(lesson1);
//        DUMMY_LESSON_DB.add(lesson2);
//    }
}
