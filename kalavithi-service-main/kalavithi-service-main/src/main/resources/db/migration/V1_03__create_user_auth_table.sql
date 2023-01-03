CREATE TABLE IF NOT EXISTS user_auth (
    id          Int NOT NULL PRIMARY KEY,
    password    varchar(255) NOT NULL,

    FOREIGN KEY (id) REFERENCES user_profile (id) ON DELETE CASCADE
);


