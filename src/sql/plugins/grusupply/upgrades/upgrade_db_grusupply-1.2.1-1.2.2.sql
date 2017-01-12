--
-- Alter table
--
DROP TABLE IF EXISTS grusupply_customer_indexer_action;
DROP TABLE IF EXISTS grusupply_indexer_action;
CREATE TABLE grusupply_indexer_action (
  id_action INT DEFAULT 0 NOT NULL,
  id_resource VARCHAR(50) DEFAULT '' NOT NULL,
  id_task INT DEFAULT 0 NOT NULL ,
  resource_type VARCHAR(255) DEFAULT '' NOT NULL,
  PRIMARY KEY (id_action)
  );
CREATE INDEX grusupply_indexer_action ON grusupply_indexer_action (id_task);