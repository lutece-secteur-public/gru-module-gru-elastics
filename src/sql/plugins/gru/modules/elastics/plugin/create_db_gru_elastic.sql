--
-- Structure for table elastic_mapping
--

DROP TABLE IF EXISTS elastic_mapping;
CREATE TABLE elastic_mapping (
id_mapping int(11) NOT NULL,
id_customer int(11) NOT NULL ,
id_user int(11),
ref_user varchar(50) ,
PRIMARY KEY (id_mapping)
);

--
-- Structure for table demand_mapping
--

DROP TABLE IF EXISTS demand_mapping;
CREATE TABLE demand_mapping (
id_demand varchar(50) NOT NULL ,
id_demand_type int(11) NOT NULL,
id_customer int(11) NOT NULL,
ref_notification varchar(50) NOT NULL,
PRIMARY KEY (id_elasticsearch)
);