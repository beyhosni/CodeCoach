CREATE TABLE cc_track (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    programming_language VARCHAR(50) NOT NULL,
    difficulty INTEGER NOT NULL,
    is_published BOOLEAN NOT NULL DEFAULT false,
    created_by_user_id BIGINT NOT NULL REFERENCES cc_user(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_track_language ON cc_track(programming_language);
CREATE INDEX idx_track_published ON cc_track(is_published);
