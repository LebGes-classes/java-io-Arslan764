package resources;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.util.concurrent.ThreadLocalRandom;

public class ExcelTestDataGenerator {

    private static final String[] MALE_LAST_NAMES = {
            "Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов",
            "Попов", "Васильев", "Новиков", "Федоров", "Морозов",
            "Лебедев", "Козлов", "Соколов", "Белов", "Комаров"
    };

    private static final String[] FEMALE_LAST_NAMES = {
            "Иванова", "Петрова", "Сидорова", "Смирнова", "Кузнецова",
            "Попова", "Васильева", "Новикова", "Федорова", "Морозова",
            "Лебедева", "Козлова", "Соколова", "Белова", "Комарова"
    };

    private static final String[] MALE_NAMES = {
            "Александр", "Дмитрий", "Максим", "Сергей", "Андрей",
            "Алексей", "Артем", "Илья", "Кирилл", "Михаил"
    };

    private static final String[] FEMALE_NAMES = {
            "Анна", "Мария", "Елена", "Ольга", "Наталья",
            "Ирина", "Татьяна", "Екатерина", "Юлия", "Анастасия"
    };

    private static final String[] MALE_MIDDLE_NAMES = {
            "Александрович", "Дмитриевич", "Сергеевич", "Андреевич",
            "Алексеевич", "Артемович", "Ильич", "Кириллович", "Михайлович"
    };

    private static final String[] FEMALE_MIDDLE_NAMES = {
            "Александровна", "Дмитриевна", "Сергеевна", "Андреевна",
            "Алексеевна", "Артемовна", "Ильинична", "Кирилловна", "Михайловна"
    };

    private static final String[] SUBJECTS = {
            "Алгебра", "Геометрия", "Дискретная математика", "Программирование",
            "Физика", "Химия", "История", "Философия", "Иностранный язык",
            "Базы данных", "Алгоритмы", "ООП", "Веб-разработка", "ИИ", "Кибербезопасность"
    };

    private static final String[] DAYS = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница"};
    private static final String[] TIMES = {"8:30", "10:00", "11:40", "13:20", "15:00", "16:30"};

    public static void main(String[] args) throws Exception {
        Workbook workbook = new XSSFWorkbook();

        // Генерация данных с учетом 1 преподаватель = 1 предмет
        Sheet teachersSheet = generateTeachers(workbook, 40);
        Sheet groupsSheet = generateGroups(workbook, 30);
        Sheet subjectsSheet = generateSubjectsWithUniqueTeachers(workbook, 40); // Теперь 40 предметов (по одному на препода)
        Sheet studentsSheet = generateStudents(workbook, 200, 30);
        Sheet scheduleSheet = generateScheduleWithTeacherSubjectConstraint(workbook, 300, 30, 40);
        Sheet scoresSheet = generateScores(workbook, 1000, 200, 40); // Теперь 40 предметов

        // Сохранение файла
        try (FileOutputStream out = new FileOutputStream("TestData.xlsx")) {
            workbook.write(out);
        }

        System.out.println("Файл успешно сгенерирован: TestData_OneSubjectPerTeacher.xlsx");
    }

    private static Sheet generateTeachers(Workbook workbook, int count) {
        Sheet sheet = workbook.createSheet("Teachers");
        createHeader(sheet, "ID", "ФИО");

        for (int i = 1; i <= count; i++) {
            Row row = sheet.createRow(i);
            boolean isMale = (i % 4 != 0); // 75% мужчин
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(generateFullName(isMale));
        }
        return sheet;
    }

    private static Sheet generateGroups(Workbook workbook, int count) {
        Sheet sheet = workbook.createSheet("Groups");
        createHeader(sheet, "ID", "Номер");

        for (int i = 1; i <= count; i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(400 + i);
        }
        return sheet;
    }

    private static Sheet generateStudents(Workbook workbook, int count, int groupsCount) {
        Sheet sheet = workbook.createSheet("Students");
        createHeader(sheet, "ID", "ФИО", "ID группы");

        for (int i = 1; i <= count; i++) {
            Row row = sheet.createRow(i);
            boolean isMale = ThreadLocalRandom.current().nextBoolean();

            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(generateFullName(isMale));
            row.createCell(2).setCellValue(1 + ThreadLocalRandom.current().nextInt(groupsCount));
        }
        return sheet;
    }

    private static Sheet generateSubjects(Workbook workbook, int count, int teachersCount) {
        Sheet sheet = workbook.createSheet("Subjects");
        createHeader(sheet, "ID", "название", "ID преподавателя");

        for (int i = 1; i <= count; i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(SUBJECTS[i % SUBJECTS.length]);
            row.createCell(2).setCellValue(1 + ThreadLocalRandom.current().nextInt(teachersCount));
        }
        return sheet;
    }

    private static Sheet generateSchedule(Workbook workbook, int count, int groupsCount, int teachersCount, int subjectsCount) {
        Sheet sheet = workbook.createSheet("Schedule");
        createHeader(sheet, "ID", "ID группы", "ID преподавателя", "ID предмета", "dayOfTheWeek", "startTime", "endTime");

        for (int i = 1; i <= count; i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(1 + ThreadLocalRandom.current().nextInt(groupsCount));
            row.createCell(2).setCellValue(1 + ThreadLocalRandom.current().nextInt(teachersCount));
            row.createCell(3).setCellValue(1 + ThreadLocalRandom.current().nextInt(subjectsCount));
            row.createCell(4).setCellValue(DAYS[i % DAYS.length]);

            int timeIndex = ThreadLocalRandom.current().nextInt(TIMES.length - 1);
            row.createCell(5).setCellValue(TIMES[timeIndex]);
            row.createCell(6).setCellValue(TIMES[timeIndex + 1]);
        }
        return sheet;
    }

    private static Sheet generateScores(Workbook workbook, int count, int studentsCount, int subjectsCount) {
        Sheet sheet = workbook.createSheet("Scores");
        createHeader(sheet, "ID", "ID студента", "ID предмета", "оценка");

        for (int i = 1; i <= count; i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(1 + ThreadLocalRandom.current().nextInt(studentsCount));
            row.createCell(2).setCellValue(1 + ThreadLocalRandom.current().nextInt(subjectsCount));
            row.createCell(3).setCellValue(2 + ThreadLocalRandom.current().nextInt(4)); // 2-5
        }
        return sheet;
    }

    private static void createHeader(Sheet sheet, String... headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
    }

    private static String generateFullName(boolean isMale) {
        String lastName = isMale ?
                MALE_LAST_NAMES[ThreadLocalRandom.current().nextInt(MALE_LAST_NAMES.length)] :
                FEMALE_LAST_NAMES[ThreadLocalRandom.current().nextInt(FEMALE_LAST_NAMES.length)];

        String firstName = isMale ?
                MALE_NAMES[ThreadLocalRandom.current().nextInt(MALE_NAMES.length)] :
                FEMALE_NAMES[ThreadLocalRandom.current().nextInt(FEMALE_NAMES.length)];

        String middleName = isMale ?
                MALE_MIDDLE_NAMES[ThreadLocalRandom.current().nextInt(MALE_MIDDLE_NAMES.length)] :
                FEMALE_MIDDLE_NAMES[ThreadLocalRandom.current().nextInt(FEMALE_MIDDLE_NAMES.length)];

        return lastName + " " + firstName + " " + middleName;
    }

    private static Sheet generateSubjectsWithUniqueTeachers(Workbook workbook, int teachersCount) {
        Sheet sheet = workbook.createSheet("Subjects");
        createHeader(sheet, "ID", "название", "ID преподавателя");

        for (int i = 1; i <= teachersCount; i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(SUBJECTS[i % SUBJECTS.length] + " (группа " + (i % 10 + 1) + ")");
            row.createCell(2).setCellValue(i); // ID преподавателя = ID предмета
        }
        return sheet;
    }

    private static Sheet generateScheduleWithTeacherSubjectConstraint(
            Workbook workbook, int count, int groupsCount, int teachersCount) {
        Sheet sheet = workbook.createSheet("Schedule");
        createHeader(sheet, "ID", "ID группы", "ID преподавателя", "ID предмета", "dayOfTheWeek", "startTime", "endTime");

        for (int i = 1; i <= count; i++) {
            Row row = sheet.createRow(i);
            int teacherId = 1 + ThreadLocalRandom.current().nextInt(teachersCount);

            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(1 + ThreadLocalRandom.current().nextInt(groupsCount));
            row.createCell(2).setCellValue(teacherId);
            row.createCell(3).setCellValue(teacherId); // ID предмета = ID преподавателя
            row.createCell(4).setCellValue(DAYS[i % DAYS.length]);

            int timeIndex = ThreadLocalRandom.current().nextInt(TIMES.length - 1);
            row.createCell(5).setCellValue(TIMES[timeIndex]);
            row.createCell(6).setCellValue(TIMES[timeIndex + 1]);
        }
        return sheet;
    }

}