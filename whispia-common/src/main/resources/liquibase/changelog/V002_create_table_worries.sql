-- liquibase formatted sql

-- changeset Seungwon-Choi:2 runInTransaction:false
CREATE TABLE IF NOT EXISTS worries (
    id          BIGSERIAL PRIMARY KEY    NOT NULL,
    user_id     BIGINT                   NOT NULL,
    title       TEXT                     NOT NULL,
    content     TEXT                     NOT NULL,
    category    TEXT                     NOT NULL,
    status      TEXT                     NOT NULL,
    created_at  TIMESTAMP DEFAULT NOW()  NOT NULL,
    updated_at  TIMESTAMP
);

COMMENT ON COLUMN worries.id IS '고민 id';
COMMENT ON COLUMN worries.user_id IS '사용자 id';
COMMENT ON COLUMN worries.title IS '고민의 제목';
COMMENT ON COLUMN worries.content IS '고민의 내용';
COMMENT ON COLUMN worries.category IS '고민의 카테고리';
COMMENT ON COLUMN worries.status IS '고민의 상태';
COMMENT ON COLUMN worries.created_at IS '데이터 생성일자';
COMMENT ON COLUMN worries.updated_at IS '데이터 수정일자';

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_worries_user_id ON worries(user_id);
