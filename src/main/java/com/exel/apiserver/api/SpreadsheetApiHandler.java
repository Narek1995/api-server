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
public class SpreadsheetApiHandler {
	private final SpreadsheetRepository spreadSheetRepository;
	private final RowRepository rowRepository;

	@Autowired
	public SpreadsheetApiHandler (SpreadsheetRepository spreadSheetRepository,
	                              RowRepository rowRepository) {
		this.spreadSheetRepository = spreadSheetRepository;
		this.rowRepository = rowRepository;
	}

	public Spreadsheet getSpreadsheetById (String userId, UUID id) {
		Optional<Spreadsheet> spreadsheet = spreadSheetRepository.selectSpreadsheet(userId, id);
		if (spreadsheet.isEmpty()) {
			throw ApiInternalException
					.builder()
					.status(HttpStatus.NOT_FOUND)
					.message("Spreadsheet does not exists")
					.build();
		} else {
			Spreadsheet result = spreadsheet.get();
			List<Row> rows = rowRepository.selectRowsForSpreadsheet(userId, result.getId());
			result.setRows(rows);
			return result;
		}
	}

	public List<Spreadsheet> getSpreadsheetsForUser (String userId) {
		return spreadSheetRepository.selectSpreadsheetsForUser(userId);
	}

	public Spreadsheet saveSpreadsheet (Spreadsheet spreadsheet) {
		return spreadSheetRepository.save(spreadsheet);
	}

	public void deleteSpreadsheet (String userId, UUID spreadsheetId) {
		spreadSheetRepository.deleteSpreadsheet(userId, spreadsheetId);
	}
}
