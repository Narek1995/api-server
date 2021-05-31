package com.exel.apiserver.data.db;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "row")
@TypeDef(
		name = "list-array",
		typeClass = ListArrayType.class
)
public class Row {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private UUID id;
	@Column(name = "number")
	@Min(value = 1, message = "Row count starts from 1")
	@NotNull(message = "Row number is mandatory")
	private Integer number;
	@Column(name = "spreadsheetId")
	@NotNull(message = "SpreadsheetId is mandatory")
	private UUID spreadsheetId;
	@NotEmpty(message = "Cells are mandatory")
	@Type(type = "list-array")
	@Column(name = "cells", columnDefinition = "cells[]")
	private List<@Pattern(regexp = "^[1-9]\\d*$|^=SUM\\((\\w+\\d+)((:\\w+\\d+)|(,\\w+\\w+)*)((,\\w+\\d)((:\\w+\\d+)|(,\\w+\\w+)*))*\\)$|^=(\\w+\\d+)|^$", message = "Invalid Cell Value") String> cells;
}
