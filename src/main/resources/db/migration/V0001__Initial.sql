CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE spreadsheet (
    ID uuid NOT NULL DEFAULT uuid_generate_v1(),
    name VARCHAR(255) NOT NULL,
    ownerId VARCHAR(255) NOT NULL,
    rowsCount INTEGER NOT NULL,
    columnsCount INTEGER NOT NULL,
    createdAt TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (ID));
CREATE INDEX table_id_index ON spreadsheet(ID);
CREATE INDEX table_name_index ON spreadsheet(name);
CREATE INDEX table_ownerId_index ON spreadsheet(ownerId);

CREATE TABLE row (
    ID uuid NOT NULL DEFAULT uuid_generate_v1(),
    number INTEGER NOT NULL,
    spreadsheetId uuid NOT NULL,
    cells VARCHAR[],
    createdAt TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    FOREIGN KEY (spreadsheetId) references spreadsheet(ID) ON DELETE CASCADE);
CREATE INDEX row_id_index ON row(ID);
CREATE INDEX row_tableId_index ON row(spreadsheetId);