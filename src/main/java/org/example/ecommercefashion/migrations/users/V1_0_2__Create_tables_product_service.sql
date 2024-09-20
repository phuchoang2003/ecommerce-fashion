CREATE TYPE product_state AS ENUM ('DRAFT', 'PUBLISHED', 'DELETED');

CREATE TABLE categories
(
    id                 BIGSERIAL PRIMARY KEY,
    level              SMALLINT     NOT NULL,
    parent_id          BIGINT,
    name               VARCHAR(255) NOT NULL,
    left_value         BIGINT       NOT NULL,
    right_value        BIGINT       NOT NULL,
    slug               VARCHAR(255),
    support_size_chart JSONB,
    created_at         TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    deleted_at         TIMESTAMP,
    deleted            BOOLEAN               DEFAULT FALSE,
    version            SMALLINT     NOT NULL DEFAULT 0
);


CREATE TABLE size_charts
(
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(255)  NOT NULL,
    size_chart_image_id BIGINT UNIQUE NOT NULL,
    description         TEXT,
);


CREATE TABLE attribute_value_unit (
    id BIGSERIAL PRIMARY KEY,
    unit_name VARCHAR(255) NOT NULL,
    display_unit_name VARCHAR(255) NOT NULL
);

CREATE TABLE rel_attribute_value_attribute_value_unit (
    attribute_value_id BIGINT NOT NULL ,
    attribute_value_unit_id BIGINT NOT NULL,
    PRIMARY KEY (attribute_value_id, attribute_value_unit_id)
);

CREATE TABLE attribute_value (
    id BIGSERIAL PRIMARY KEY,
    value VARCHAR(255) NOT NULL,
    attribute_id BIGINT NOT NULL,
    display_value VARCHAR(255) NOT NULL,
);

CREATE TABLE rel_product_size_chart (
    product_id BIGINT NOT NULL,
    size_chart_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, size_chart_id)
);

CREATE TABLE rel_attribute_value_product (
    product_id BIGSERIAL PRIMARY KEY,
    attribute_value_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, attribute_value_id)

);

CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(8, 2) NOT NULL,
    description TEXT NOT NULL,
    quantity SMALLINT NOT NULL,
    state product_state NOT NULL,
    category_id BIGINT NOT NULL,
    slug VARCHAR(255) NOT NULL
);

CREATE TABLE product_variant (
    id BIGSERIAL PRIMARY KEY,
    variant JSONB NOT NULL,
    product_id BIGINT NOT NULL,
    quantity SMALLINT NOT NULL,
    state product_state NOT NULL,
);


CREATE TABLE attribute (
    id BIGSERIAL PRIMARY KEY,
    key VARCHAR(255) NOT NULL,
    is_mandatory BOOLEAN NOT NULL,
    display_key VARCHAR(255) NOT NULL
);

CREATE TABLE product_restriction (
    id BIGSERIAL PRIMARY KEY,
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    restriction_type VARCHAR(255) NOT NULL,
    min_limit DECIMAL(8, 2),
    max_limit DECIMAL(8, 2),
    unit VARCHAR(255),
    description TEXT NOT NULL
);


CREATE TABLE rel_category_attribute (
    attribute_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (attribute_id, category_id)
);