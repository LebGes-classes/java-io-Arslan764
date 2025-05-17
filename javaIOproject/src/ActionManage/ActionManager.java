package ActionManage;

import DataManage.DataManager;
import DataManage.ExcelReader;

public class ActionManager {

    public static void prepare(){
        ExcelReader.readTeachersFromExcel();
        ExcelReader.readGroupsFromExcel();
        ExcelReader.readStudentsFromExcel();
        ExcelReader.readSubjectsFromExcel();
        ExcelReader.readScoresFromExcel();
        ExcelReader.readScheduleFromExcel();
        DataManager.sort();
    }

}
