package DataManage;

import Group.Group;
import Schedule.Lesson;
import Student.Student;
import Subject.Subject;
import Teacher.Teacher;
import Score.Score;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelReader {

    private static final String FILEPATH = "C:/Users/asus/IdeaProjects/javaIOproject/src/resources/TestData.xlsx";

    public static void readTeachersFromExcel() {
        int id;
        String name;
        try{
            FileInputStream fls = new FileInputStream(FILEPATH);
            XSSFWorkbook workbook = new XSSFWorkbook(fls);
            XSSFSheet sheet = workbook.getSheet("Teachers");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                id = (int) row.getCell(0).getNumericCellValue();
                name = row.getCell(1).getStringCellValue();
                Teacher teacher = new Teacher(id, name);
                DataManager.addTeacher(teacher, id);
            }
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void readStudentsFromExcel() {
        int id;
        String name;
        int groupId;
        try{
            FileInputStream fls = new FileInputStream(FILEPATH);
            XSSFWorkbook workbook = new XSSFWorkbook(fls);
            XSSFSheet sheet = workbook.getSheet("Students");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                id = (int) row.getCell(0).getNumericCellValue();
                name = row.getCell(1).getStringCellValue();
                groupId = (int) row.getCell(2).getNumericCellValue();
                Student student = new Student(id, name, groupId);
                DataManager.addStudentToGroup(student, groupId);
                DataManager.addStudent(student, id);
            }
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    public static void readGroupsFromExcel() {
        int id;
        String number;
        try{
            FileInputStream fls = new FileInputStream(FILEPATH);
            XSSFWorkbook workbook = new XSSFWorkbook(fls);
            XSSFSheet sheet = workbook.getSheet("Groups");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                id = (int) row.getCell(0).getNumericCellValue();
                number = "" + (int) row.getCell(1).getNumericCellValue();
                Group group = new Group(id, number);
                DataManager.addGroup(group, id);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readScheduleFromExcel() {
        int id;
        int groupId;
        int teacherId;
        int subjectId;
        String dayOfTheWeek;
        String startTime;
        String endTime;
        try{
            FileInputStream fls = new FileInputStream(FILEPATH);
            XSSFWorkbook workbook = new XSSFWorkbook(fls);
            XSSFSheet sheet = workbook.getSheet("Schedule");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                id = (int) row.getCell(0).getNumericCellValue();
                groupId = (int) row.getCell(1).getNumericCellValue();
                teacherId = (int) row.getCell(2).getNumericCellValue();
                subjectId = (int) row.getCell(3).getNumericCellValue();
                dayOfTheWeek = row.getCell(4).getStringCellValue();
                startTime = row.getCell(5).getStringCellValue();
                endTime = row.getCell(6).getStringCellValue();
                Lesson lesson = new Lesson(id, groupId, teacherId, subjectId, dayOfTheWeek, startTime, endTime);
                Teacher currentTeacher = DataManager.getTeachers().get(teacherId);
                currentTeacher.addLesson(lesson, dayOfTheWeek);
                Group currentGroup = DataManager.getGroups().get(groupId);
                currentTeacher.addGroup(currentGroup, currentGroup.getNumber());
//                currentTeacher.addSubject( DataManager.getSubjects().get(subjectId), subjectId);
                currentTeacher.setSubject(DataManager.getSubjects().get(subjectId));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void readSubjectsFromExcel() {
        int id;
        String subjectName;
        int groupId;
        try{
            FileInputStream fls = new FileInputStream(FILEPATH);
            XSSFWorkbook workbook = new XSSFWorkbook(fls);
            XSSFSheet sheet = workbook.getSheet("Subjects");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                id = (int) row.getCell(0).getNumericCellValue();
                subjectName = row.getCell(1).getStringCellValue();
                groupId = (int) row.getCell(2).getNumericCellValue();
                Subject subject = new Subject(id, subjectName, groupId);
                DataManager.addSubject(subject, id);
            }
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void readScoresFromExcel() {
        int id;
        int studentId;
        int subjectId;
        double scoreValue;
        try{
            FileInputStream fls = new FileInputStream(FILEPATH);
            XSSFWorkbook workbook = new XSSFWorkbook(fls);
            XSSFSheet sheet = workbook.getSheet("Scores");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                id = (int) row.getCell(0).getNumericCellValue();
                studentId = (int) row.getCell(1).getNumericCellValue();
                subjectId = (int) row.getCell(2).getNumericCellValue();
                scoreValue = row.getCell(3).getNumericCellValue();
                Score score = new Score(id, studentId, subjectId, scoreValue);
                DataManager.getStudents().get(studentId).addScore(score, subjectId);
                DataManager.addScore(score, id);
            }

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
