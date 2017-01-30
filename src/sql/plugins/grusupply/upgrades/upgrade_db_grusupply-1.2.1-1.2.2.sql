--
-- Drop table grusupply_customer_indexer_action
--
DROP TABLE IF EXISTS grusupply_customer_indexer_action;

--
-- Add table grusupply_demand_indexer_action
--
DROP TABLE IF EXISTS grusupply_demand_indexer_action;
CREATE TABLE grusupply_demand_indexer_action (
  id_action INT DEFAULT 0 NOT NULL,
  id_demand VARCHAR(50) DEFAULT '' NOT NULL,
  demand_type_id VARCHAR(50) DEFAULT '' NOT NULL,
  id_task INT DEFAULT 0 NOT NULL ,
  PRIMARY KEY (id_action)
  );
CREATE INDEX grusupply_demand_indexer_action ON grusupply_demand_indexer_action (id_task);