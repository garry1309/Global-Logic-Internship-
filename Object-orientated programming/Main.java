import java.util.List;

public class Main {
    public static void main(String[] args) {

        Student s1 = new Student("Gaurav", 21, "S101");
        Student s2 = new Student("Priya", 22, "S102");
        Teacher t1 = new Teacher("Vedant Mehra", 30, "Java Programming");


        List<Person> people = List.of(s1, s2, t1);
        for (Person p : people) {
            System.out.println(p);
        }


        s1.enroll("Data Structures");
        s1.enroll("Java Fundamentals");
        s1.dropCourse("Data Structures");
        System.out.println(s1.getName() + "'s courses: " + s1.getCourses());


        s1.setAge(22);
        System.out.println("Updated age: " + s1.getAge());

        try {
            s1.setAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }
    }
}


