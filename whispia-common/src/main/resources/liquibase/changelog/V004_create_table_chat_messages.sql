-- liquibase formatted sql

-- changeset Seungwon-Choi:4
CREATE TABLE IF NOT EXISTS chat_messages (
    idx             BIGSERIAL PRIMARY KEY    NOT NULL,
    user_idx        BIGINT                   NOT NULL,
    chat_room_idx   BIGINT                   NOT NULL,
    content         TEXT                     NOT NULL,
    status          TEXT                     NOT NULL,
    created_at      TIMESTAMP DEFAULT NOW()  NOT NULL,
    updated_at      TIMESTAMP
);

COMMENT ON COLUMN chat_messages.idx IS '채팅메시지 idx';
COMMENT ON COLUMN chat_messages.user_idx IS '사용자 idx';
COMMENT ON COLUMN chat_messages.chat_room_idx IS '채팅방 idx';
COMMENT ON COLUMN chat_messages.content IS '채팅메시지 내용';
COMMENT ON COLUMN chat_messages.status IS '채팅메시지 상태';
COMMENT ON COLUMN chat_messages.created_at IS '데이터 생성일자';
COMMENT ON COLUMN chat_messages.updated_at IS '데이터 수정일자';
