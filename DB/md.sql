INSERT INTO `master_data` (`attrib`,`count_box_in_order`,`count_prod_in_box`,`count_prod_in_order`,`customer`,`nomenklature`,`order_id`,`order_text`,`dt`) VALUES ('Вст.No15 Шпал.No16762/No15995 Прокр. Губа.Черн./No11 Терт.',2,14,25,'Саркисян','703-G No20344/Черн. №41','712000100000014544.18.0.25','S7-120-0010','2017-12-21 12:44:57');
INSERT INTO `master_data` (`attrib`,`count_box_in_order`,`count_prod_in_box`,`count_prod_in_order`,`customer`,`nomenklature`,`order_id`,`order_text`,`dt`) VALUES ('Вст.No15 Шпал.No16762/No15995 Прокр. Губа.Черн./No11 Терт.',1,5,5,'Саркисян','703-G No20344/Черн. №42','712000100000014544.18.1.5','S7-120-0010','2017-12-21 12:44:57');
INSERT INTO `master_data` (`attrib`,`count_box_in_order`,`count_prod_in_box`,`count_prod_in_order`,`customer`,`nomenklature`,`order_id`,`order_text`,`dt`) VALUES ('Вст.No15 Шпал.No16762/No15995 См.разм. Прокр. Губа.Черн./No11 Терт.',4,14,50,'Саркисян','703-G No20344/Черн. №42','712000100000014545.13.0.50','S7-120-0010','2017-12-21 12:44:57');
INSERT INTO `master_data` (`attrib`,`count_box_in_order`,`count_prod_in_box`,`count_prod_in_order`,`customer`,`nomenklature`,`order_id`,`order_text`,`dt`) VALUES ('Вст.No15 Шпал.No16762/No15995 Прокр. Губа.Черн./No11',4,14,50,'Саркисян','703-G No20344/Черн. №43','712000100000014546.18.0.50','S7-120-0010','2017-12-21 12:44:57');
INSERT INTO `master_data` (`attrib`,`count_box_in_order`,`count_prod_in_box`,`count_prod_in_order`,`customer`,`nomenklature`,`order_id`,`order_text`,`dt`) VALUES ('Вст.No15 Шпал.No16762/No15995 Прокр. Губа.Черн./No11',2,14,25,'Саркисян','703-G No20344/Черн. №44','712000100000014547.18.0.25','S7-120-0010','2017-12-21 12:44:57');
INSERT INTO `master_data` (`attrib`,`count_box_in_order`,`count_prod_in_box`,`count_prod_in_order`,`customer`,`nomenklature`,`order_id`,`order_text`,`dt`) VALUES ('Вст.No15 Шпал.No16762/No15995 Прокр. Губа.Черн./No11',2,14,25,'Саркисян','703-G No20344/Черн. №45','712000100000014548.18.0.25','S7-120-0010','2017-12-21 12:44:57');
INSERT INTO `master_data` (`attrib`,`count_box_in_order`,`count_prod_in_box`,`count_prod_in_order`,`customer`,`nomenklature`,`order_id`,`order_text`,`dt`) VALUES ('Вст.Сер./Беж. См.разм. Прокр. Кабл.Сер./Беж. Терт.',2,20,40,'Оксузян','Прима No9/No11 Рант.Кор./Черн. No36','710100110100000785.17.0.40','S7-101-0011','2017-12-21 12:44:57');
INSERT INTO `master_data` (`attrib`,`count_box_in_order`,`count_prod_in_box`,`count_prod_in_order`,`customer`,`nomenklature`,`order_id`,`order_text`,`dt`) VALUES ('Вст.No7',3,20,50,'Оксузян','Терминатор Рант.Сер. No43','710100110100000470.19.0.50','S7-101-0011','2017-12-21 12:44:57');
INSERT INTO `master_data` (`attrib`,`count_box_in_order`,`count_prod_in_box`,`count_prod_in_order`,`customer`,`nomenklature`,`order_id`,`order_text`,`dt`) VALUES ('Вст.No7',2,20,40,'Оксузян','Терминатор Рант.Сер. No44','710100110100000471.19.0.40','S7-101-0011','2017-12-21 12:44:57');
INSERT INTO `master_data` (`attrib`,`count_box_in_order`,`count_prod_in_box`,`count_prod_in_order`,`customer`,`nomenklature`,`order_id`,`order_text`,`dt`) VALUES ('Вст.No7',1,10,10,'Оксузян','Терминатор Черн./No11 Рант.Беж. No42','710100110100001034.19.1.10','S7-101-0011','2017-12-21 12:44:57');

INSERT INTO Department (code,name) VALUES ('000000020','Бригада ТЭП №1 Каблуки'),
 ('000000027','Бригада ТЭП №1 Ранты'),
 ('000000001','Бригада ТЭП №1 Стойки'),
 ('000000013','Бригада ТЭП №1 Тройки'),
 ('000000014','Бригада ТЭП №1 Цвет'),
 ('000000019','Бригада ТЭП №2 Каблуки'),
 ('000000028','Бригада ТЭП №2 Ранты'),
 ('000000004','Бригада ТЭП №2 Стойки'),
 ('000000015','Бригада ТЭП №2 Тройки'),
 ('000000016','Бригада ТЭП №2 Цвет');
 
 INSERT INTO `employee` (`name`,`code`) VALUES ('Чеботов','00000001');
INSERT INTO `employee` (`name`,`code`) VALUES ('Сапожинский','00000002');
INSERT INTO `employee` (`name`,`code`) VALUES ('Сандалкин','00000003');


INSERT INTO `operation` (`name`) VALUES ('Производство');
INSERT INTO `operation` (`name`) VALUES ('Отгрузка');
INSERT INTO `operation` (`name`) VALUES ('Покраска');

delete FROM qr.part_box where id>0;
delete FROM qr.box_move where id>0;
delete FROM qr.box where id>0;