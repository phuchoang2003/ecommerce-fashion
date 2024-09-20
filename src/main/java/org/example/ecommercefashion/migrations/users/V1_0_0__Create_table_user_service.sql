CREATE TYPE gender_type AS ENUM ('FEMALE', 'MALE', 'OTHER');
CREATE TYPE token_type AS ENUM ('ACCESS', 'REFRESH_TOKEN');



CREATE TABLE users
(
    id                  BIGSERIAL PRIMARY KEY,
    email               VARCHAR(255) NOT NULL UNIQUE,
    password            TEXT,
    full_name           VARCHAR(50),
    phone_number        VARCHAR(12) UNIQUE,
    birth               DATE,
    gender              gender_type NOT NULL,
    avatar              TEXT,
    id_google_account   VARCHAR(255),
    id_facebook_account VARCHAR(255),
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_admin            BOOLEAN NOT NULL DEFAULT FALSE,
    deleted             BOOLEAN DEFAULT FALSE,
    version             SMALLINT NOT NULL DEFAULT 0
);

CREATE TABLE role
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL UNIQUE,
);


CREATE TABLE user_roles
(
    id_user BIGINT,
    id_role BIGINT,
    PRIMARY KEY (id_user, id_role),
);


CREATE TABLE permission
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(100) NOT NULL UNIQUE
);


CREATE TABLE role_permission
(
    id_role       BIGINT,
    id_permission BIGINT,
    PRIMARY KEY (id_role, id_permission),
);



CREATE TABLE jwt_token (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    hash_token VARCHAR(255) NOT NULL,
    token_type token_type NOT NULL,
    expiration_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    device VARCHAR(255),
    device_id VARCHAR(255),
    browser VARCHAR(255),
    ip VARCHAR(45),
    reference_token_id BIGINT
);

CREATE TABLE reset_password_tokens(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiration_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    is_used BOOLEAN NOT NULL DEFAULT FALSE
);
