-- liquibase formatted sql

-- changeset Seungwon-Choi:2
CREATE TABLE IF NOT EXISTS worries (
    idx         BIGSERIAL PRIMARY KEY    NOT NULL,
    user_idx    BIGINT                   NOT NULL,
    title       TEXT                     NOT NULL,
    content     TEXT                     NOT NULL,
    category    TEXT                     NOT NULL,
    status      TEXT                     NOT NULL,
    created_at  TIMESTAMP DEFAULT NOW()  NOT NULL,
    updated_at  TIMESTAMP
);

COMMENT ON COLUMN worries.idx IS '고민 idx';
COMMENT ON COLUMN worries.user_idx IS '사용자 idx';
COMMENT ON COLUMN worries.title IS '고민의 제목';
COMMENT ON COLUMN worries.content IS '고민의 내용';
COMMENT ON COLUMN worries.category IS '고민의 카테고리';
COMMENT ON COLUMN worries.status IS '고민의 상태';
COMMENT ON COLUMN worries.created_at IS '데이터 생성일자';
COMMENT ON COLUMN worries.updated_at IS '데이터 수정일자';
