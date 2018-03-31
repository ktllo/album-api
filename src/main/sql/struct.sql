CREATE TABLE `user`(
	`uid` INT UNSIGNED NOT NULL PRIMARY KEY,
    `uname` VARCHAR(32) COLLATE ascii_general_ci NOT NULL,
    `password` TEXT COLLATE ascii_general_ci NOT NULL,
    `quota` INT UNSIGNED DEFAULT 0,
    `fullname` TEXT,
    CONSTRAINT UNIQUE KEY (`uname`)
);

CREATE TABLE `thumbnail`(
	`id` CHAR(12) COLLATE ascii_bin NOT NULL PRIMARY KEY,
    `hash` TEXT COLLATE ascii_general_ci NOT NULL,
    `hash_alg` VARCHAR(100) COLLATE ascii_general_ci DEFAULT 'sha256',
    `path` TEXT,
    `size` INT UNSIGNED DEFAULT 0,
    `width` INT UNSIGNED DEFAULT 0,
    `height` INT UNSIGNED DEFAULT 0,
    `format` VARCHAR(100) DEFAULT 'image/jpeg'
);

CREATE TABLE `album`(
	`albumID` CHAR(12) COLLATE ascii_bin NOT NULL PRIMARY KEY,
    `owner` INT UNSIGNED NOT NULL,
    `name` TEXT,
    `description` TEXT,
    `thumbnail`  CHAR(12) COLLATE ascii_bin,
    `created` DATETIME DEFAULT NOW(),
    CONSTRAINT FOREIGN KEY (`owner`) REFERENCES `user`(`uid`),
    CONSTRAINT FOREIGN KEY (`thumbnail`) REFERENCES `thumbnail`(`id`)
);

CREATE TABLE `authtoken`(
	`token` CHAR(24)  COLLATE ascii_bin NOT NULL PRIMARY KEY,
	`user` INT UNSIGNED NOT NULL,
	`creared` DATETIME NOT NULL DEFAULT NOW(),
	`lastaccess` DATETIME NOT NULL,
	CONSTRAINT FOREIGN KEY (`user`) REFERENCES `user`(`uid`)
);