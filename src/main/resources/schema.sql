-- 기존 테이블 삭제
DROP TABLE IF EXISTS news_articles;
DROP TABLE IF EXISTS cheer_messages;
DROP TABLE IF EXISTS highlights;
DROP TABLE IF EXISTS fan_notes;
DROP TABLE IF EXISTS player_record;
DROP TABLE IF EXISTS users;

-- 테이블: users
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       nickname VARCHAR(255) NOT NULL,
                       fan_level INT,
                       role VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- 테이블: player_record (임찬규 단독)
CREATE TABLE player_record (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               year INT NOT NULL,
                               wins INT NOT NULL,
                               losses INT NOT NULL,
                               era DOUBLE NOT NULL,
                               innings DOUBLE NOT NULL,
                               strikeouts INT NOT NULL,
                               game_count INT NOT NULL
) ENGINE=InnoDB;

-- 테이블: fan_notes
CREATE TABLE fan_notes (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           user_id BIGINT NOT NULL,
                           date DATE NOT NULL,
                           title VARCHAR(255) NOT NULL,
                           content TEXT NOT NULL,
                           CONSTRAINT fk_fan_note_user FOREIGN KEY (user_id)
                               REFERENCES users(id)
                               ON DELETE CASCADE
) ENGINE=InnoDB;

-- 테이블: highlights
CREATE TABLE highlights (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            title VARCHAR(255) NOT NULL,
                            description TEXT NOT NULL,
                            media_url VARCHAR(255) NOT NULL,
                            created_at DATETIME NOT NULL
) ENGINE=InnoDB;

-- 테이블: cheer_messages
CREATE TABLE cheer_messages (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                nickname VARCHAR(255) NOT NULL,
                                password_hash VARCHAR(255) NOT NULL,
                                content TEXT NOT NULL,
                                created_at DATETIME NOT NULL
) ENGINE=InnoDB;

-- 테이블: news_articles
CREATE TABLE news_articles (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               title VARCHAR(255) NOT NULL,
                               link VARCHAR(1024) NOT NULL,
                               pub_date VARCHAR(50) NOT NULL,
                               description TEXT NOT NULL,
                               source VARCHAR(512) NOT NULL,
                               image_url VARCHAR(1024),
                               fetched_at DATETIME NOT NULL,
                               UNIQUE KEY uniq_link (link(512))
) ENGINE=InnoDB;
