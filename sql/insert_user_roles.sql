INSERT INTO qr.role(id, name)
  VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN'), (3, 'ROLE_TOP'), (4, 'ROLE_EXTERNAL_USER'), (5, 'ROLE_SCANER_USER'); 
  
INSERT INTO `user` (`name`, `pswd`, `password`, `external`, `super_user`) VALUES ('admin', 'serverQrp', '$2a$10$dkYqaVWoKfescMETDBLCSOCnQs2Eo8EL6r7IeVP9S2JxMuvfsAxHq', true,true);

INSERT INTO `user` (`name`, `pswd`, `password`, `external`, `super_user`) VALUES ('hereYouGo', 'qwertyui', '$2a$10$XBZTBmhFKfrBrjZtXISvZetA.ISV1XGNACPP7Z30WD5EmCzIvVyte', true,true);

INSERT INTO `user_roles` (`user_id`, `roles_id`) values (58, 3);
INSERT INTO `user_roles` (`user_id`, `roles_id`) values (59, 2);

INSERT INTO `user` (`name`, `pswd`, `password`, `external`, `super_user`) VALUES ('extUser', 'macRoExternaL', '$2a$10$BEzFOb9L.bD.YUptM.yUMO9txZyXFaEawtZRpCtqhc1/2rWclo3cO', true,true);
INSERT INTO `user` (`name`, `pswd`, `password`, `external`, `super_user`) VALUES ('intUser', 'stInternaL', '$2a$10$jZoT7FPR4IAFzn8sTl.hIuhVZBaVTm8hdwus30JtjZb6/EKNt5.Qa', true,true);
INSERT INTO `user_roles` (`user_id`, `roles_id`) values (60, 3);
INSERT INTO `user_roles` (`user_id`, `roles_id`) values (61, 3);


update `user` SET `password` = '$2a$10$H.CSvF1N.Pfu7ULApUtcCutH1KPrhzCwXLQb.pVNzkqAfXoQFC89y' where name='гпз';
update `user` SET `password` = '$2a$10$EDjl91o2gZ6rskd4/JTz4.goWTwuj1ZbO4u05WwVGWrQd4vbATA3m' where name='абв';
update `user` SET `password` = '$2a$10$pfwdw4jBv2XbDhY4SRioDOCGaUpxDlT7ISo2HckvLZsMcMTz.tAYe' where name='makro-m';
update `user` SET `password` = '$2a$10$YHNYvcJAz5DTeLAxyhFGyOdf3iUYDnKgvz.4RVcPzuyl0soNtQ.yO' where name='makroF1';

INSERT INTO `user` (`name`, `pswd`, `password`, `external`, `super_user`) VALUES ('John316', 'DNS156Kl', '$2a$10$e8XoesT2P52zvTEB5olCge0oXxktahz/K6umYWHTKTIzmv7PBlzte', true,true);
INSERT INTO `user` (`name`, `pswd`, `password`, `external`, `super_user`) VALUES ('DmitryL', 'A5p1re', '$2a$10$7oz05WMuL/9kUV/s4kNJAuSVc0skn7VpvlxyX2QM30k7pwbURTWcK', true,true);
INSERT INTO `user_roles` (`user_id`, `roles_id`) values (62, 1);
INSERT INTO `user_roles` (`user_id`, `roles_id`) values (63, 1);