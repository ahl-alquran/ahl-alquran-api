package com.ahl.alquran.service;

import org.apache.poi.ss.usermodel.Row;

public interface ExcelExporter<T> {
    String[] getHeaders();
    void fillData(Row row, T item);
    String getSheetName();
}
