-- liquibase formatted sql

-- changeset Seungwon-Choi:5 runInTransaction:false
CREATE TABLE IF NOT EXISTS reports (
    id          BIGSERIAL PRIMARY KEY    NOT NULL,
    type        TEXT                     NOT NULL,
    target_id   BIGINT                   NOT NULL,
    user_id     BIGINT                   NOT NULL,
    reason      TEXT                     NOT NULL,
    created_at  TIMESTAMP DEFAULT NOW()  NOT NULL,
    updated_at  TIMESTAMP
);

COMMENT ON COLUMN reports.id IS '신고 id';
COMMENT ON COLUMN reports.type IS '신고의 타입';
COMMENT ON COLUMN reports.target_id IS '대상 id';
COMMENT ON COLUMN reports.user_id IS '사용자 id';
COMMENT ON COLUMN reports.reason IS '신고의 사유';
COMMENT ON COLUMN reports.created_at IS '데이터 생성일자';
COMMENT ON COLUMN reports.updated_at IS '데이터 수정일자';

CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS uidx_reports_type_target_id ON reports(type, target_id);
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_reports_user_id ON reports(user_id);
