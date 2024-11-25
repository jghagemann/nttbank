package com.hagemann.nttbank.helper;

import com.hagemann.nttbank.domain.correntista.Correntista;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    public static String[] HEADERS = {"nome", "sobrenome", "email", "ativo"};
    public static String SHEET = "correntistas";

    public static ByteArrayInputStream downloadExcel(List<Correntista> correntistas) {

        try(Workbook workbook = WorkbookFactory.create(new File(SHEET)); ByteArrayOutputStream baos = new ByteArrayOutputStream();) {

            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
            }

            int rowIdx = 1;
            for (Correntista correntista: correntistas) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue((RichTextString) correntista.getId());
                row.createCell(1).setCellValue(correntista.getNome());
                row.createCell(2).setCellValue(correntista.getSobrenome());
                row.createCell(3).setCellValue(correntista.getEmail());
                row.createCell(4).setCellValue(correntista.getAtivo());
            }

            workbook.write(baos);
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter Excel");
        }
    }

    public static List<Correntista> uploadExcel(InputStream inputStream) {
        try {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rows = sheet.iterator();
            List<Correntista> correntistaList = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                Correntista correntista = new Correntista();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> correntista.setNome(currentCell.getStringCellValue());
                        case 1 -> correntista.setSobrenome(currentCell.getStringCellValue());
                        case 2 -> correntista.setEmail(currentCell.getStringCellValue());
                        case 3 -> correntista.setAtivo(currentCell.getBooleanCellValue());
                        default -> throw new RuntimeException("Erro carregando linhas do arquivo");
                    }
                    cellIdx++;
                }
                correntistaList.add(correntista);
            }
            workbook.close();
            return correntistaList;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo Excel");
        }
    }
}
