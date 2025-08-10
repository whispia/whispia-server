-- liquibase formatted sql

-- changeset Seungwon-Choi:3
CREATE TABLE IF NOT EXISTS chat_rooms (
    idx         BIGSERIAL PRIMARY KEY    NOT NULL,
    worry_idx   BIGINT                   NOT NULL,
    created_at  TIMESTAMP DEFAULT NOW()  NOT NULL,
    updated_at  TIMESTAMP
);

COMMENT ON COLUMN chat_rooms.idx IS '채팅방 idx';
COMMENT ON COLUMN chat_rooms.worry_idx IS '고민 idx';
COMMENT ON COLUMN chat_rooms.created_at IS '데이터 생성일자';
COMMENT ON COLUMN chat_rooms.updated_at IS '데이터 수정일자';
