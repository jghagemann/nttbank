package com.hagemann.nttbank.naousar.helper;

import com.hagemann.nttbank.naousar.domain.correntista.Correntista;
import com.hagemann.nttbank.naousar.exceptions.ArquivoException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    public static String[] HEADERS = {"nome", "sobrenome", "email", "ativo"};
    public static String SHEET = "correntistas";

    public static ByteArrayInputStream downloadExcel(List<Correntista> correntistas) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
            }

            int rowIdx = 1;
            for (Correntista correntista : correntistas) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(correntista.getNome());
                row.createCell(1).setCellValue(correntista.getSobrenome());
                row.createCell(2).setCellValue(correntista.getEmail());
                row.createCell(3).setCellValue(correntista.getAtivo());
            }

            workbook.write(baos);
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter Excel", e);
        }
    }

    public static List<Correntista> uploadExcel(InputStream inputStream) {
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
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

                Correntista correntista = new Correntista();
                Cell nomeCell = currentRow.getCell(0);
                correntista.setNome(nomeCell != null ? nomeCell.getStringCellValue() : null);

                Cell sobrenomeCell = currentRow.getCell(1);
                correntista.setSobrenome(sobrenomeCell != null ? sobrenomeCell.getStringCellValue() : null);

                Cell emailCell = currentRow.getCell(2);
                correntista.setEmail(emailCell != null ? emailCell.getStringCellValue() : null);

                Cell ativoCell = currentRow.getCell(3);
                correntista.setAtivo(ativoCell != null && ativoCell.getCellType() == CellType.BOOLEAN
                        ? ativoCell.getBooleanCellValue()
                        : false);

                correntistaList.add(correntista);
            }

            return correntistaList;
        } catch (IOException e) {
            throw new ArquivoException("Erro ao ler arquivo Excel");
        }
    }
}
