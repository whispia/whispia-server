-- liquibase formatted sql

-- changeset Seungwon-Choi:3 runInTransaction:false
CREATE TABLE IF NOT EXISTS chat_rooms (
    id          BIGSERIAL PRIMARY KEY    NOT NULL,
    worry_id    BIGINT                   NOT NULL,
    created_at  TIMESTAMP DEFAULT NOW()  NOT NULL,
    updated_at  TIMESTAMP
);

COMMENT ON COLUMN chat_rooms.id IS '채팅방 id';
COMMENT ON COLUMN chat_rooms.worry_id IS '고민 id';
COMMENT ON COLUMN chat_rooms.created_at IS '데이터 생성일자';
COMMENT ON COLUMN chat_rooms.updated_at IS '데이터 수정일자';

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_chat_rooms_worry_id ON chat_rooms(worry_id);
