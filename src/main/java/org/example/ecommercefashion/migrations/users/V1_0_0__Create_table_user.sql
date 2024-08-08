CREATE TABLE user
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    email               VARCHAR(255)                     NOT NULL UNIQUE,
    password            TEXT,
    full_name           VARCHAR(50),
    phone_number        VARCHAR(12) UNIQUE,
    birth               DATE,
    gender              ENUM ('FEMALE', 'MALE', 'OTHER') NOT NULL,
    avatar              TEXT,
    id_google_account   VARCHAR(255),
    id_facebook_account VARCHAR(255),
    create_at           TIMESTAMP                                 DEFAULT CURRENT_TIMESTAMP,
    update_at           TIMESTAMP                                 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_admin            BOOLEAN                          NOT NULL DEFAULT false,
    deleted             BOOLEAN                                   DEFAULT false
);

CREATE TABLE role
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE user_roles
(
    id_user BIGINT,
    id_role BIGINT,
    PRIMARY KEY (id_user, id_role),
    FOREIGN KEY (id_user) REFERENCES user (id),
    FOREIGN KEY (id_role) REFERENCES role (id)
);

CREATE TABLE permission
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE role_permission
(
    id_role       BIGINT,
    id_permission BIGINT,
    PRIMARY KEY (id_role, id_permission),
    FOREIGN KEY (id_role) REFERENCES role (id),
    FOREIGN KEY (id_permission) REFERENCES permission (id)
);


CREATE TABLE refresh_tokens
(
    id         SERIAL PRIMARY KEY,
    token      VARCHAR(255) NOT NULL,
    user_id    BIGINT       NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_revoked BOOLEAN   DEFAULT FALSE,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES user (id)
);