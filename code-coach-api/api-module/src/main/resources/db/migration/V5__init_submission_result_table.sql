CREATE TABLE cc_submission_result (
    id BIGSERIAL PRIMARY KEY,
    submission_id BIGINT NOT NULL UNIQUE REFERENCES cc_submission(id) ON DELETE CASCADE,
    compilation_error TEXT,
    runtime_error TEXT,
    tests_passed INTEGER NOT NULL DEFAULT 0,
    tests_failed INTEGER NOT NULL DEFAULT 0,
    failure_details TEXT,
    execution_time_ms BIGINT NOT NULL DEFAULT 0,
    memory_used_mb BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_submission_result_submission ON cc_submission_result(submission_id);
