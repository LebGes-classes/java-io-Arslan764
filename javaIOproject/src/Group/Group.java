package Group;

import Student.Student;

import java.util.ArrayList;
import java.util.List;

public class Group implements Comparable<Group>{


    private int id;

    private String number;
    private List<Student> students;


    public Group(int id, String name) {
        this.id = id;
        this.number = name;
        students = new ArrayList<Student>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }
    public String getNumber() {
        return number;
    }
    public int getId() {
        return id;
    }

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return number;
    }

    @Override
    public int compareTo(Group o) {
        int cmp = number.compareTo(o.getNumber());
        if (cmp == 0) {
            cmp = Integer.compare(id, o.getId());
        }
        return cmp;
    }
}
