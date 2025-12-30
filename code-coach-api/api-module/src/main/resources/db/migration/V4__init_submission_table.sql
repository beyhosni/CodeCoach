CREATE TABLE cc_submission (
    id BIGSERIAL PRIMARY KEY,
    exercise_id BIGINT NOT NULL REFERENCES cc_exercise(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES cc_user(id) ON DELETE CASCADE,
    code TEXT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    attempt_number INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_submission_exercise_user ON cc_submission(exercise_id, user_id);
CREATE INDEX idx_submission_status ON cc_submission(status);
CREATE INDEX idx_submission_user ON cc_submission(user_id);
