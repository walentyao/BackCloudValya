package main.java.ru.valya.serveback.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.matveyakulov.markoservcomeback.domain.Request;
import ru.matveyakulov.markoservcomeback.utils.Constant;

import java.io.*;
import java.util.*;

public class ExcelHelper {

    public static String write(Request request) {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + Constant.EXCEL_FILE_NAME;

        FileInputStream file;
        Workbook workbook;
        try {
            file = new FileInputStream(fileLocation);
            workbook = new XSSFWorkbook(file);
        } catch (Exception e) {
            workbook = new XSSFWorkbook();
        }
        try {
            Sheet sheet;
            if (workbook.getNumberOfSheets() > 0) {
                sheet = workbook.getSheetAt(0);
            } else {
                sheet = workbook.createSheet("Requests");
            }

            int lastRow = sheet.getLastRowNum();
            if (lastRow <= 0) {
                sheet.setColumnWidth(0, 4000);
                sheet.setColumnWidth(1, 4000);
                sheet.setColumnWidth(2, 4000);
                sheet.setColumnWidth(3, 4000);
                Row header = sheet.createRow(0);
                List<String> fields = List.of("RequestText", "AnsweredText", "UserRole");
                for (int i = 0; i < fields.size(); i++) {
                    Cell headerCell = header.createCell(i);
                    headerCell.setCellValue(fields.get(i));
                }
            }
            lastRow = sheet.getLastRowNum();
            int i = 0;
            Row row = sheet.createRow(lastRow + 1);
            Cell month = row.createCell(i++);
            month.setCellValue(request.getRequestText());
            Cell day = row.createCell(i++);
            day.setCellValue(request.getAnsweredText());
            Cell value = row.createCell(i++);
            value.setCellValue(request.getUserRole());

            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
            return fileLocation;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Request> read(String fileLocation) {
        FileInputStream file;
        try {
            file = new FileInputStream(fileLocation);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            List<Request> requests = new ArrayList<>();
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                Request request = Request.builder()
                        .requestText(row.getCell(0).getStringCellValue())
                        .answeredText(row.getCell(1).getStringCellValue())
                        .userRole(row.getCell(2).getStringCellValue())
                        .build();
                requests.add(request);
            }
            return requests;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}
