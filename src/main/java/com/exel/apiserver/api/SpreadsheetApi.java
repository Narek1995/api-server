package com.exel.apiserver.api;

import com.exel.apiserver.data.db.Spreadsheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/spreadsheet")
@Validated
@CrossOrigin("http://localhost:3000")
public class SpreadsheetApi {
	private final SpreadsheetApiHandler spreadsheetApiHandler;

	@Autowired
	public SpreadsheetApi (SpreadsheetApiHandler spreadsheetApiHandler) {
		this.spreadsheetApiHandler = spreadsheetApiHandler;
	}

	@GetMapping("/{spreadsheetId}")
	public Spreadsheet getSpreadsheetById (@RequestAttribute(name = "userId") String userId,
	                                       @PathVariable(name = "spreadsheetId") UUID spreadsheetId) {
		return spreadsheetApiHandler.getSpreadsheetById(userId, spreadsheetId);
	}

	@GetMapping
	public List<Spreadsheet> getAllSpreadsheets (@RequestAttribute(name = "userId") String userId) {
		return spreadsheetApiHandler.getSpreadsheetsForUser(userId);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Spreadsheet createSpreadsheet (@RequestBody @Valid Spreadsheet spreadsheet,
	                                      @RequestAttribute(name = "userId") String userId) {
		spreadsheet.setOwnerId(userId);
		return spreadsheetApiHandler.saveSpreadsheet(spreadsheet);
	}

	@DeleteMapping("/{spreadsheetId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteSpreadsheet (@PathVariable("spreadsheetId") UUID spreadsheetId,
	                               @RequestAttribute(name = "userId") String userId) {
		spreadsheetApiHandler.deleteSpreadsheet(userId, spreadsheetId);
	}
}
