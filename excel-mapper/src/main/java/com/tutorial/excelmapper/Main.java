package com.tutorial.excelmapper;

import com.poiji.bind.Poiji;
import com.tutorial.excelmapper.model.Document;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        ExcelMapper excelMapper = new ExcelMapper();
        List<Document> documents = new ArrayList<>();
        documents.add(new Document(
                "Luật Đất đai 2024",
                "31/2024/QH15",
                "Luật",
                "20/03/2024",
                "Quốc hội",
                "20/03/2023"
        ));
        documents.add(new Document(
                "Luật Đất đai 2024",
                "31/2024/QH15",
                "Luật",
                "20/03/2024",
                "Quốc hội",
                "20/03/2023"
        ));
        documents.add(new Document(
                "Luật Đất đai 2024",
                "31/2024/QH15",
                "Luật",
                "20/03/2024",
                "Quốc hội",
                "20/03/2023"
        ));
        documents.add(new Document(
                "Luật Đất đai 2024",
                "31/2024/QH15",
                "Luật",
                "20/03/2024",
                "Quốc hội",
                "20/03/2023"
        ));
        excelMapper.write(
                documents, Document.class,
                new FileOutputStream("/home/hoangdao/Workspace/Java/JavaCollections/out.xlsx")
        );

    }

}
