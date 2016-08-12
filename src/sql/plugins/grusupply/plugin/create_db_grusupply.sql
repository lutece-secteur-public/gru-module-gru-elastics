--
-- Structure for table elastic_mapping
--

DROP TABLE IF EXISTS elastic_mapping;
CREATE TABLE elastic_mapping (
id_mapping int(11) NOT NULL,
id_customer int(11) NOT NULL ,
id_user int(11),
PRIMARY KEY (id_mapping)
);

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

