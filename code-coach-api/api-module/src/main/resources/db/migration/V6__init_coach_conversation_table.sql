CREATE TABLE cc_coach_conversation (
    id BIGSERIAL PRIMARY KEY,
    submission_id BIGINT NOT NULL UNIQUE REFERENCES cc_submission(id) ON DELETE CASCADE,
    hint_level INTEGER NOT NULL DEFAULT 1,
    context TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_coach_conversation_submission ON cc_coach_conversation(submission_id);
