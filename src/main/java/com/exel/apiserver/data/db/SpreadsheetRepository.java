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
public interface SpreadsheetRepository extends CrudRepository<Spreadsheet, UUID> {

	@Query("SELECT s from Spreadsheet s where s.ownerId = ?1 AND s.Id = ?2")
	Optional<Spreadsheet> selectSpreadsheet (String userId, UUID spreadsheetId);

	@Query("SELECT s from Spreadsheet s where s.ownerId = ?1")
	List<Spreadsheet> selectSpreadsheetsForUser (String userId);

	@Modifying
	@Query("DELETE FROM Spreadsheet s where s.ownerId = ?1 AND s.Id = ?2")
	void deleteSpreadsheet (String userId, UUID spreadsheetId);

}
