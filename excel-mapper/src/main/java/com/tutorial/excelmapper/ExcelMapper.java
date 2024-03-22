package com.tutorial.excelmapper;

import com.tutorial.excelmapper.mapper.CellProperty;
import com.tutorial.excelmapper.mapper.ExcelSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ExcelMapper {

    public <T> void write(List<T> items, Class<T> c, OutputStream outputStream) throws IOException {
        Annotation[] annotations = c.getDeclaredAnnotations();
        String sheetName = c.getName();
        for (Annotation annotation : annotations) {
            if (annotation instanceof ExcelSheet) {
                sheetName = ((ExcelSheet) annotation).name();
            }
        }
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetName);
            Row headRow = createHeadRow(sheet, c);
            int rowIndex = 1;
            for (T item : items) {
                creatRow(
                        sheet, rowIndex, item, item.getClass()
                );
                ++rowIndex;
            }
            workbook.write(outputStream);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> Row creatRow(Sheet sheet, int rowIndex, T data, Class<?> c) throws IllegalAccessException {
        Row headRow = sheet.getRow(0);
        List<Field> fields = getAllFields(c);
        Row row = sheet.createRow(rowIndex);

        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof CellProperty) {
                    String header = ((CellProperty) annotation).value();
                    int columnIndex = findCellIndexByContent(
                            headRow, header
                    );
                    if (columnIndex >= 0) {
                        org.apache.poi.ss.usermodel.Cell cell = row.createCell(columnIndex);
                        field.setAccessible(true);
                        cell.setCellValue(
                                field.get(data).toString()
                        );
                    }
                }
            }
        }
        return row;
    }

    private int findCellIndexByContent(Row row, String content) {
        Iterator<org.apache.poi.ss.usermodel.Cell> iterator = row.cellIterator();
        while (iterator.hasNext()) {
            org.apache.poi.ss.usermodel.Cell cell = iterator.next();
            if (cell.getStringCellValue().equals(content)) {
                return cell.getColumnIndex();
            }
        }
        return -1;
    }

    private <T> Row createHeadRow(Sheet sheet, Class<T> c) {
        try {
            List<String> headers = new ArrayList<>();
            T t = c.getDeclaredConstructor().newInstance();
            List<Field> fields = getAllFields(c);
            for (Field field : fields) {
                Annotation[] annotations = field.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof CellProperty) {
                        headers.add(
                                ((CellProperty) annotation).value()
                        );
                    }
                }
            }

            Row row = sheet.createRow(0);
            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
//            cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
//            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            int columnIndex = 0;
            for (String header : headers) {
                org.apache.poi.ss.usermodel.Cell cell = row.createCell(columnIndex);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(header);
                ++columnIndex;
            }
            return row;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Provided class does not have default constructor");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            fields.addAll(getAllFields(type.getSuperclass()));
        }
        return fields;
    }


}
