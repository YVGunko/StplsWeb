CREATE TABLE `pt2crude_root` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_of_change` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert into pt2crude_root (date_of_change) values ("2020-01-01");
insert into pt2crude_root (date_of_change) values ("2022-04-01");
update price_type2crude set pt2crude_root_id = 1;

insert into price_type2crude (crude_id, price_type_id, pt2crude_root_id) 
select  s.crude_id, s.price_type_id, 2 from price_type2crude s where s.pt2crude_root_id=1;