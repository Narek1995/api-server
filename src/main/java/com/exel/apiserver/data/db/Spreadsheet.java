package com.exel.apiserver.data.db;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "spreadsheet")
public class Spreadsheet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private UUID Id;
	@NotBlank(message = "Name is mandatory")
	@Column(name = "name")
	private String name;
	@Column(name = "ownerId")
	private String ownerId;
	@NotNull(message = "rowsCount is mandatory")
	@Min(value = 1, message = "minimum 1 row is permitted")
	@Max(value = 50, message = "maximum 10 rows are permitted")
	@Column(name = "rowsCount")
	private Integer rowsCount;
	@NotNull(message = "columnsCount is mandatory")
	@Column(name = "columnsCount")
	@Min(value = 1, message = "minimum 1 column is permitted")
	@Max(value = 50, message = "maximum 10 columns are permitted")
	private Integer columnsCount;
	@Transient
	private List<Row> rows;
}
