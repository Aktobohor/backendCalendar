CREATE TABLE `Reminders` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `r_title` varchar(45) NOT NULL,
                             `r_freq` varchar(45) NOT NULL,
                             `r_dt_start` datetime NOT NULL,
                             `r_interval` double NOT NULL DEFAULT '1',
                             `r_wkst` int NOT NULL,
                             `r_count` int NOT NULL DEFAULT '1',
                             `r_until` datetime DEFAULT NULL,
                             `r_tzid` varchar(156) DEFAULT NULL,
                             `r_bysetpos` varchar(45) DEFAULT NULL,
                             `r_bymonth` varchar(45) DEFAULT NULL,
                             `r_byyearday` varchar(45) DEFAULT NULL,
                             `r_byweekno` varchar(45) DEFAULT NULL,
                             `r_byweekday` varchar(45) DEFAULT NULL,
                             `r_byhour` varchar(45) DEFAULT NULL,
                             `r_byminute` varchar(45) DEFAULT NULL,
                             `r_byseconds` varchar(45) DEFAULT NULL,
                             `r_string_rule` varchar(455) NOT NULL DEFAULT '',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Structures` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `idQuestionary` varchar(50) DEFAULT NULL,
                              `idTask` varchar(50) DEFAULT NULL,
                              `idChallenges` varchar(50) DEFAULT NULL,
                              `idRandomTask` varchar(50) DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `StrsRems` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `id_structure` int NOT NULL,
                            `id_reminder` int NOT NULL,
                            `creator` varchar(45) DEFAULT NULL,
                            `timestamp` datetime DEFAULT NULL,
                            `approved` char(1) NOT NULL DEFAULT 'N',
                            `user_approvation` varchar(45) NOT NULL DEFAULT 'N',
                            `event_duration` int NOT NULL DEFAULT '0',
                            `event_color` varchar(15) NOT NULL DEFAULT '',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `id_UNIQUE` (`id`),
                            KEY `id_structure` (`id_structure`),
                            KEY `id_reminder` (`id_reminder`),
                            CONSTRAINT `StrsRems_ibfk_1` FOREIGN KEY (`id_structure`) REFERENCES `Structures` (`id`),
                            CONSTRAINT `StrsRems_ibfk_2` FOREIGN KEY (`id_reminder`) REFERENCES `Reminders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
