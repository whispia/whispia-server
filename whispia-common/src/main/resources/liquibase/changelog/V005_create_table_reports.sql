-- liquibase formatted sql

-- changeset Seungwon-Choi:5
CREATE TABLE IF NOT EXISTS reports (
    idx         BIGSERIAL PRIMARY KEY    NOT NULL,
    user_idx    BIGINT                   NOT NULL,
    target_idx  BIGINT                   NOT NULL,
    type        TEXT                     NOT NULL,
    reason      TEXT                     NOT NULL,
    created_at  TIMESTAMP DEFAULT NOW()  NOT NULL,
    updated_at  TIMESTAMP
);

COMMENT ON COLUMN reports.idx IS '신고 idx';
COMMENT ON COLUMN reports.user_idx IS '사용자 idx';
COMMENT ON COLUMN reports.target_idx IS '대상 idx';
COMMENT ON COLUMN reports.type IS '신고의 타입';
COMMENT ON COLUMN reports.reason IS '신고의 사유';
COMMENT ON COLUMN reports.created_at IS '데이터 생성일자';
COMMENT ON COLUMN reports.updated_at IS '데이터 수정일자';
