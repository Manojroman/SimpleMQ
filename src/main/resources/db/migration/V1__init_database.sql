CREATE TABLE failed_messages (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 payload TEXT NOT NULL,
                                 error_message VARCHAR(255),
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 processed BOOLEAN DEFAULT FALSE
);
