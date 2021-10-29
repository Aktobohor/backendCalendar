CREATE TABLE `structures` (

  `id` INT NOT NULL AUTO_INCREMENT,

  `idQuestionary` INT NULL DEFAULT NULL,

  `idTask` INT NULL DEFAULT NULL,

  `idChallenges` INT NULL DEFAULT NULL,

  `idRandomTask` INT NULL DEFAULT NULL,

  PRIMARY KEY (`id`),

  UNIQUE INDEX `id_UNIQUE` (`id` ASC)

  );

 
  CREATE TABLE `reminders` (

  `id` INT NOT NULL AUTO_INCREMENT,

  `id_structur` INT NOT NULL,

  `freq` INT NOT NULL,

  `dt_start` DATE NOT NULL,

  `interval` DOUBLE NOT NULL DEFAULT '1',

  `wkst` INT NOT NULL,

  `count` INT NOT NULL DEFAULT '1',

  `until` DATE NULL,

  `tzid` VARCHAR(156) NULL,

  `bysetpos` VARCHAR(45) NULL,

  `bymonth` VARCHAR(45) NULL,

  `byyearday` VARCHAR(45) NULL,

  `byweekno` VARCHAR(45) NULL,

  `byweekday` VARCHAR(45) NULL,

  `byhour` VARCHAR(45) NULL,

  `byminute` VARCHAR(45) NULL,

  `byseconds` VARCHAR(45) NULL,

  PRIMARY KEY (`id`),

  UNIQUE INDEX `id_UNIQUE` (`id` ASC),

  FOREIGN KEY (id_structur) REFERENCES structures(id)

  );