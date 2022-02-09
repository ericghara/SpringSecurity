DROP TABLE IF EXISTS UserPass, Otp, Token CASCADE;

CREATE TABLE UserPass(
	id SERIAL NOT null,
	username VARCHAR(45) NOT null UNIQUE,
	password VARCHAR(45),
	authorities BYTEA,
	PRIMARY KEY(id)
);

CREATE TABLE Otp(
	FK_UserPass_id INTEGER NOT null REFERENCES UserPass(id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	otp VARCHAR(4),
	attempts INTEGER NOT null,
	grant_time TIMESTAMP,
	PRIMARY KEY(FK_UserPass_id)
);

CREATE TABLE Token (
    FK_UserPass_id INTEGER NOT null REFERENCES UserPass(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    token VARCHAR(36),
    grant_time TIMESTAMP,
    PRIMARY KEY(FK_UserPass_id)
);