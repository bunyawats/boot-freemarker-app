CREATE TABLE freemarker_template (
  id bigint(20) NOT NULL,
  template_name varchar(100) DEFAULT NULL,
  template_body varchar(100) DEFAULT NULL,
  template_json json DEFAULT NULL,
  PRIMARY KEY (`id`)
);