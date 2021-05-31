package com.exel.apiserver.api;

import com.exel.apiserver.data.db.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/row")
@Validated
public class RowApi {
    private final RowApiHandler rowApiHandler;

    @Autowired
    public RowApi(RowApiHandler rowApiHandler) {
        this.rowApiHandler = rowApiHandler;
    }


    @PatchMapping("/{rowId}/{cellNumber}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCellValue(@RequestParam(name = "userId") String userId,
                                @PathVariable(name = "rowId") UUID rowId,
                                @PathVariable(name = "cellNumber") Integer cellNumber,
                                @RequestParam("value")
                                @Valid @Pattern(regexp = "^[1-9]\\d*$|^=SUM\\((\\w+\\d+)((:\\w+\\d+)|(,\\w+\\w+)*)((,\\w+\\d)((:\\w+\\d+)|(,\\w+\\w+)*))*\\)$|^=(\\w+\\d+)", message = "Invalid Cell Value")
                                String value) {
        rowApiHandler.updateCellValue(userId, rowId, cellNumber, value);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Row createRow(@RequestParam("userId") String userId,
                         @RequestBody @Valid Row row) {
        return rowApiHandler.insertRow(userId, row);
    }

    @GetMapping("/{spreadsheetId}")
    @ResponseBody
    public List<Row> getSpreadsheetRows(@PathVariable("spreadsheetId") UUID spreadsheetId,
                                        @RequestParam("userId") String userId) {
        return rowApiHandler.selectRowsForSpreadsheet(userId, spreadsheetId);
    }
}
