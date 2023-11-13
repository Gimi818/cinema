--liquibase formatted sql
--changeset wgmiterek:3
CREATE TABLE IF NOT EXISTS screening (
                                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         date DATE,
                                         time TIME,
                                         film_id BIGINT,
                                         FOREIGN KEY (film_id) REFERENCES film(id)
);
