package com.exel.apiserver.data.db;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
@Transactional
public interface RowRepository extends CrudRepository<Row, UUID> {
	@Modifying
	@Query(value = "UPDATE row  set cells[?2] = ?3 where id = ?1", nativeQuery = true)
	void updateCellValue (UUID rowId, Integer cellNumber, String cellValue);

	@Query(value = "SELECT r from Row r LEFT JOIN Spreadsheet s ON r.spreadsheetId = s.Id where s.ownerId = ?1 AND s.Id = ?2 ORDER BY r.number ASC")
	List<Row> selectRowsForSpreadsheet (String userId, UUID spreadsheetId);

	Optional<Row> findBySpreadsheetIdAndNumber(UUID spreadsheetId, Integer number);
}
