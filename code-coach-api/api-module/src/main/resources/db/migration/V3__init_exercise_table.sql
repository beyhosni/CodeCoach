CREATE TABLE cc_exercise (
    id BIGSERIAL PRIMARY KEY,
    track_id BIGINT NOT NULL REFERENCES cc_track(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    starter_code TEXT,
    test_code TEXT NOT NULL,
    order_in_track INTEGER NOT NULL,
    difficulty INTEGER NOT NULL,
    timeout_millis BIGINT NOT NULL DEFAULT 5000,
    memory_limit_mb BIGINT NOT NULL DEFAULT 256,
    is_published BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_exercise_track ON cc_exercise(track_id);
CREATE INDEX idx_exercise_published ON cc_exercise(is_published);
