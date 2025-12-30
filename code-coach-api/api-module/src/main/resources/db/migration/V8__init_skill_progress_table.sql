CREATE TABLE cc_skill_progress (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES cc_user(id) ON DELETE CASCADE,
    exercise_id BIGINT NOT NULL REFERENCES cc_exercise(id) ON DELETE CASCADE,
    successful_attempts INTEGER NOT NULL DEFAULT 0,
    total_attempts INTEGER NOT NULL DEFAULT 0,
    is_mastered BOOLEAN NOT NULL DEFAULT false,
    last_attempt_at TIMESTAMP,
    mastered_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX idx_skill_progress_user_exercise ON cc_skill_progress(user_id, exercise_id);
CREATE INDEX idx_skill_progress_mastered ON cc_skill_progress(is_mastered);
