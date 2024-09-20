CREATE TYPE image_type AS ENUM ('JPG', 'PNG', 'SVG');


CREATE TABLE images
(
    id         BIGSERIAL PRIMARY KEY,
    url        VARCHAR(255) NOT NULL,
    version    INT NOT NULL DEFAULT 0,
    type       image_type NOT NULL,
    size       BIGINT NOT NULL,
    title      VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted    BOOLEAN DEFAULT FALSE
);
