package Subject;

public class Subject {
    int id;
    String subjectName;
    int teacherId;


    public Subject(int id, String subjectName, int teacherId) {
        this.id = id;
        this.subjectName = subjectName;
        this.teacherId = teacherId;
    }

    public int getId() {return id;}

    @Override
    public String toString() {
        return subjectName;
    }

}
