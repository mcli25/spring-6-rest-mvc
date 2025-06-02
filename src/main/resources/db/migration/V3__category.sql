CREATE TABLE category (
                          id VARCHAR(36) NOT NULL PRIMARY KEY,
                          version BIGINT,
                          created_date TIMESTAMP(6),
                          last_modified_date TIMESTAMP(6),
                          description VARCHAR(255)
);

CREATE TABLE beer_category (
                               category_id VARCHAR(36) NOT NULL,
                               beer_id VARCHAR(36) NOT NULL,
                               PRIMARY KEY (category_id, beer_id),
                               FOREIGN KEY (category_id) REFERENCES category(id),
                               FOREIGN KEY (beer_id) REFERENCES beer(id)
);