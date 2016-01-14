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

