package com.panelbjj.dto;

import com.panelbjj.dto.Category;
import com.panelbjj.dto.Competitor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class SpreadsheetGenerator extends AbstractXlsView {
    private List<Competitor> competitorList;
    private List<String> listName;
    Category category;
    private int fours;
    Workbook workbook;
    CellStyle bottom, right, corner, categoryName;
    List<Sheet> sheets = new ArrayList<>();

    public SpreadsheetGenerator(List<Competitor> competitorList, Category category) {
        this.category = category;
        this.competitorList = competitorList;
        if (competitorList.size() <= 2) return;
        fours = addByes();
        sortOut(this.competitorList, fours);

        for (int i = fours; i >= 1; i--) {
            shuffleAFour(competitorList.subList(4 * i - 4, 4 * i));
        }
    }

    private void prepareSheet(String part) {
        Sheet sheet = workbook.createSheet(part);
        Row row0 = sheet.createRow(0);
        Cell name = row0.createCell(3);
        name.setCellStyle(categoryName);
        name.setCellValue(category.getCategory() + ", " + category.getBelt() + ", " + category.getWeight() + "kg " + part);
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 7));
        sheets.add(sheet);
    }

    private void build2(int rowIndex, String zaw1, String zaw2, int sheetIndex) {
        Row row0 = sheets.get(sheetIndex).createRow(rowIndex);
        Cell zawodnik1 = row0.createCell(0);
        zawodnik1.setCellValue(zaw1);
        zawodnik1.setCellStyle(bottom);

        Row row1 = sheets.get(sheetIndex).createRow(rowIndex + 1);
        row1.createCell(0).setCellStyle(right);
        row1.createCell(1).setCellStyle(bottom);

        Row row2 = sheets.get(sheetIndex).createRow(rowIndex + 2);

        Cell zawodnik2 = row2.createCell(0);
        zawodnik2.setCellValue(zaw2);
        zawodnik2.setCellStyle(corner);
    }

    private void build4(int row, int compIndex, int sheetIndex) {
        System.out.println("weszlo do czwrotki, sheet: " + sheetIndex);
        build2(row, listName.get(compIndex), listName.get(compIndex + 1), sheetIndex);
        build2(row + 4, listName.get(compIndex + 2), listName.get(compIndex + 3), sheetIndex);
        sheets.get(sheetIndex).getRow(row + 4).createCell(1).setCellStyle(right);
        sheets.get(sheetIndex).getRow(row + 5).getCell(1).setCellStyle(corner);
        sheets.get(sheetIndex).getRow(row + 2).createCell(1).setCellStyle(right);

        Row nextStage = sheets.get(sheetIndex).createRow(row + 3);
        nextStage.createCell(1).setCellStyle(right);
        nextStage.createCell(2).setCellStyle(bottom);
    }

    private void build8(int row, int compIndex, int sheetIndex) {
        build4(row, compIndex, sheetIndex);
        build4(row + 8, compIndex + 4, sheetIndex);

        Row nextStage = sheets.get(sheetIndex).createRow(row + 7);
        nextStage.createCell(2).setCellStyle(right);
        nextStage.createCell(3).setCellStyle(bottom);
        sheets.get(sheetIndex).getRow(row + 11).getCell(2).setCellStyle(corner);
        sheets.get(sheetIndex).getRow(row + 10).createCell(2).setCellStyle(right);
        sheets.get(sheetIndex).getRow(row + 9).createCell(2).setCellStyle(right);
        sheets.get(sheetIndex).getRow(row + 8).createCell(2).setCellStyle(right);
        sheets.get(sheetIndex).getRow(row + 6).createCell(2).setCellStyle(right);
        sheets.get(sheetIndex).getRow(row + 5).createCell(2).setCellStyle(right);
        sheets.get(sheetIndex).getRow(row + 4).createCell(2).setCellStyle(right);
    }

    private void build16(int compIndex, int sheetIndex) {

        build8(1, compIndex, sheetIndex);
        build8(17, compIndex + 8, sheetIndex);
        sheets.get(sheetIndex).getRow(9).createCell(3).setCellStyle(right);
        sheets.get(sheetIndex).getRow(10).createCell(3).setCellStyle(right);
        sheets.get(sheetIndex).getRow(11).createCell(3).setCellStyle(right);
        sheets.get(sheetIndex).getRow(12).createCell(3).setCellStyle(right);
        sheets.get(sheetIndex).getRow(13).createCell(3).setCellStyle(right);
        sheets.get(sheetIndex).getRow(14).createCell(3).setCellStyle(right);
        sheets.get(sheetIndex).getRow(15).createCell(3).setCellStyle(right);
        Row nextStage = sheets.get(sheetIndex).createRow(16);
        nextStage.createCell(3).setCellStyle(right);
        nextStage.createCell(4).setCellStyle(bottom);
        sheets.get(sheetIndex).getRow(17).createCell(3).setCellStyle(right);
        sheets.get(sheetIndex).getRow(18).createCell(3).setCellStyle(right);
        sheets.get(sheetIndex).getRow(19).createCell(3).setCellStyle(right);
        sheets.get(sheetIndex).getRow(20).createCell(3).setCellStyle(right);
        sheets.get(sheetIndex).getRow(21).createCell(3).setCellStyle(right);
        sheets.get(sheetIndex).getRow(22).createCell(3).setCellStyle(right);
        sheets.get(sheetIndex).getRow(23).createCell(3).setCellStyle(right);
        sheets.get(sheetIndex).getRow(24).getCell(3).setCellStyle(corner);
        sheets.get(sheetIndex).autoSizeColumn(0);
    }


    private int addByes() {
        fours = 1;
        int licznik = 0;
        while (licznik < competitorList.size()) {
            if (licznik == 4 || licznik == 8 || licznik == 16 || licznik == 32 || licznik == 64 || licznik == 128)
                fours = fours * 2;
            licznik++;
        }
        Competitor wolnyLos = new Competitor();
        wolnyLos.setClub(" ");
        wolnyLos.setName("Wolny los");
        while (competitorList.size() < fours * 4) competitorList.add(wolnyLos);
        return fours;
    }


    private void sortOut(List<Competitor> list, int iloscCzwor) {
        list.sort((zaw1, zaw2) -> zaw1.getClub().compareToIgnoreCase(zaw2.getClub()));
        int half = list.size() / 2;
        Competitor[] temp = new Competitor[list.size()];
        for (int i = 0; i < list.size(); i++) {
            if (i % 2 != 0) temp[(i - 1) / 2 + half] = list.get(i);
            else temp[i / 2] = list.get(i);
        }
        for (int i = 0; i < list.size(); i++) {
            list.set(i, temp[i]);
        }
        if (iloscCzwor > 3) {
            sortOut(list.subList(0, half), iloscCzwor / 2);
            sortOut(list.subList(half, list.size()), iloscCzwor / 2);
        }
    }

    private void shuffleAFour(List<Competitor> lista) {
        int a = 0;
        do {
            Collections.shuffle(lista);
            a++;
            if (a > 10) break;
        }
        while (lista.get(0).getClub().equals(lista.get(1).getClub()) ||
                lista.get(2).getClub().equals(lista.get(3).getClub()));
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> map,
                                      Workbook wb, HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse) throws Exception {
        // change the file name
        httpServletResponse.setHeader("Content-Disposition",
                "attachment; filename=\"" + category.getCategory() + "_" +
                        category.getBelt().substring(0, 3) + category.getWeight() + ".xls\"");


        listName = competitorList.stream()
                .map(zawodnik -> zawodnik.getName() + " " + zawodnik.getClub())
                .collect(toList());

        this.workbook = wb;

        bottom = workbook.createCellStyle();
        bottom.setBorderBottom(BorderStyle.THICK);
        right = workbook.createCellStyle();
        right.setBorderRight(BorderStyle.THICK);
        corner = workbook.createCellStyle();
        corner.setBorderRight(BorderStyle.THICK);
        corner.setBorderBottom(BorderStyle.THICK);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 18);
        categoryName = workbook.createCellStyle();
        categoryName.setFont(font);


        switch (competitorList.size()) {
            case 2:
                prepareSheet(" ");
                build2(1, listName.get(0), listName.get(1), 0);
                break;
            case 4:
                prepareSheet(" ");
                build4(1, 0, 0);
                break;
            case 8:
                prepareSheet(" ");
                build8(1, 0, 0);
                break;
            case 16:
                prepareSheet(" ");
                build16(0, 0);
                break;
            case 32:
                for (int i = 0; i < 2; i++) {
                    prepareSheet("cz." + (i + 1));
                    build16(i * 16, i);
                }
                break;
            case 64:

                for (int i = 0; i < 4; i++) {
                    prepareSheet("cz." + (i + 1));
                    build16(i * 16, i);
                }
                break;
            case 128:
                for (int i = 0; i < 8; i++) {
                    prepareSheet("cz." + (i + 1));
                    build16(i * 16, i);
                }
                break;
        }

        sheets.get(0).autoSizeColumn(0);

        try (OutputStream outStream = httpServletResponse.getOutputStream()) {
            workbook.write(outStream);
            outStream.flush();
        }
    }
}
