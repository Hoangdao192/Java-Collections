package com.tutorial.excelmapper.model;

import com.tutorial.excelmapper.mapper.CellProperty;
import com.tutorial.excelmapper.mapper.ExcelSheet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelSheet(name = "Luật")
public class Document {

    @CellProperty("Tên văn bản")
    private String documentName;
    @CellProperty("Mã văn bản")
    private String documentCode;
    @CellProperty("Loại văn bản")
    private String documentType;
    @CellProperty("Ngày ban hành")
    private String dateIssue;
    @CellProperty("Cơ quan ban hành")
    private String agencyIssue;
    @CellProperty("Ngày hiệu lực")
    private String effectiveDate;
}
