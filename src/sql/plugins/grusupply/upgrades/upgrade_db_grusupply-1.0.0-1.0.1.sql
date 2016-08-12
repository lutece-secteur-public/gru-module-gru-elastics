--
-- Structure for table grusupply_customer_indexer_action
--

DROP TABLE IF EXISTS grusupply_customer_indexer_action;
CREATE TABLE grusupply_customer_indexer_action (
  id_action INT DEFAULT 0 NOT NULL,
  id_customer INT DEFAULT 0 NOT NULL,
  id_task INT DEFAULT 0 NOT NULL ,
  PRIMARY KEY (id_action)
  );
CREATE INDEX grusupply_customer_indexer_id_task ON grusupply_customer_indexer_action (id_task);