package Teacher;

import Group.Group;
import Schedule.Lesson;
import Subject.Subject;

import java.util.*;

public class Teacher{
    private int id;
    private String fullName;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Map<String, ArrayList<Lesson>> schedule;
    private HashMap<String, Group> groups;
//    private HashMap<Integer, Subject> subjects;
    private Subject mainSubject;


    public Map<String, ArrayList<Lesson>> getSchedule() {
        return schedule;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public HashMap<String, Group> getGroups(){return groups;}

//    public HashMap<Integer,Subject> getSubjects() {
//        return subjects;
//    }

    public int getId() {
        return id;
    }

    public Subject getMainSubject() {return mainSubject;}

    public void setSubject(Subject subject){
        this.mainSubject = subject;
    }

    public Teacher(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
        String[] parts = fullName.split(" ");
        this.lastName = parts[0];
        this.firstName = parts[1];
        this.patronymic = parts[2];
        schedule = new HashMap<>();
        String[] russianDayOfTheWeek = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
        for(String day: russianDayOfTheWeek){
            schedule.put(day, new ArrayList<>());
        }
        groups = new HashMap<>();
//        subjects = new HashMap<>();
    }

    public void addLesson(Lesson lesson, String dayOfTheWeek){
        schedule.get(dayOfTheWeek).add(lesson);
    }

    public void addGroup(Group group, String groupNumber){
        groups.put(groupNumber, group);
    }

//    public void addSubject(Subject subject, int id){
//        subjects.put(id, subject);
//    }

    @Override
    public String toString() {
        return fullName;
    }
}
