package Schedule;

import DataManage.DataManager;

public class Lesson {


    private int id;
    private int groupId;
    private int teacherId;
    private int subjectId;
    private String dayOfTheWeek;
    private String startTime;
    private String endTime;

    public Lesson(int id, int groupId, int teacherId, int subjectId, String day, String startTime, String endTime) {
        this.groupId = groupId;
        this.id = id;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.dayOfTheWeek = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    @Override
    public String toString() {
        return DataManager.getSubjects().get(subjectId)+ " " + "группа " + DataManager.getGroups().get(groupId) + "  " + startTime + " " + endTime;
    }

}
