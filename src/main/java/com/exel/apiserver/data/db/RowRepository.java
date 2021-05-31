package com.exel.apiserver.data.db;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Repository
@Transactional
public interface RowRepository extends CrudRepository<Row, UUID> {
    @Modifying
    @Query(value = "UPDATE row set cells[?3] = ?4 from row r left join spreadsheet s on r.spreadsheetid = s.id where r.id = ?2 and s.ownerid = ?1", nativeQuery = true)
    void updateCellValue(String userId, UUID rowId, Integer cellNumber, String cellValue);
    @Query(value = "SELECT r from Row r LEFT JOIN Spreadsheet s ON r.spreadsheetId = s.Id where s.ownerId = ?1 AND s.Id = ?2")
    List<Row> selectRowsForSpreadsheet(String userId, UUID spreadsheetId);

}
