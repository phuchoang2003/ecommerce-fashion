CREATE TABLE images
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    url        VARCHAR(255) NOT NULL,
    type       ENUM('JPG', 'PNG', 'SVG') NOT NULL,
    size       BIGINT       NOT NULL,
    title      VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    deleted    BOOLEAN      NOT NULL DEFAULT FALSE
);