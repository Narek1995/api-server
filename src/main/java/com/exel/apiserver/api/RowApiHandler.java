package com.exel.apiserver.api;

import com.exel.apiserver.data.db.Row;
import com.exel.apiserver.data.db.RowRepository;
import com.exel.apiserver.data.db.Spreadsheet;
import com.exel.apiserver.data.db.SpreadsheetRepository;
import com.exel.apiserver.exceptions.ApiInternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RowApiHandler {
	private final RowRepository rowRepository;
	private final SpreadsheetRepository spreadsheetRepository;

	@Autowired
	public RowApiHandler (RowRepository rowRepository,
	                      SpreadsheetRepository spreadsheetRepository) {
		this.rowRepository = rowRepository;
		this.spreadsheetRepository = spreadsheetRepository;
	}

	public void updateCellValue (String userId, UUID rowId, Integer cellNumber, String value) {
		Optional<Row> row = rowRepository.findById(rowId);
		if(row.isPresent()) {
			Optional<Spreadsheet> sp = spreadsheetRepository.selectSpreadsheet(userId, row.get().getSpreadsheetId());
			if(sp.isEmpty()) {
				throw ApiInternalException
						.builder()
						.message("Access denied")
						.status(HttpStatus.FORBIDDEN)
						.build();
			}
		}
		rowRepository.updateCellValue(rowId, cellNumber, value);
	}

	public List<Row> selectRowsForSpreadsheet (String userId, UUID spreadsheetId) {
		return rowRepository.selectRowsForSpreadsheet(userId, spreadsheetId);
	}

	public Row insertRow (String userId, Row row) {
		Optional<Spreadsheet> sp = spreadsheetRepository.selectSpreadsheet(userId, row.getSpreadsheetId());
		if (sp.isPresent()) {
			Spreadsheet spreadsheet = sp.get();
			if (row.getNumber() > spreadsheet.getRowsCount()) {
				throw ApiInternalException
						.builder()
						.message("rowNumber can not be greater than spreadsheet rows count")
						.status(HttpStatus.BAD_REQUEST)
						.build();
			}
			if (row.getCells().size() > spreadsheet.getColumnsCount()) {
				throw ApiInternalException
						.builder()
						.message("cells count can not be greater than spreadsheet sells count")
						.status(HttpStatus.BAD_REQUEST)
						.build();
			}
			Optional<Row> r = rowRepository.findBySpreadsheetIdAndNumber(spreadsheet.getId(), row.getNumber());
			if(r.isPresent()) {
				for(int i = 0; i <row.getCells().size(); i++) {
					if(!row.getCells().get(i).equals("")) {
						rowRepository.updateCellValue(r.get().getId(), i + 1, row.getCells().get(i));
						return r.get();
					}
				}
			}
			return rowRepository.save(row);
		} else {
			throw ApiInternalException
					.builder()
					.status(HttpStatus.FORBIDDEN)
					.message("Access denied")
					.build();
		}
	}
}
