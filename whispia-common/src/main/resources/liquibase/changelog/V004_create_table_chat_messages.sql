-- liquibase formatted sql

-- changeset Seungwon-Choi:4 runInTransaction:false
CREATE TABLE IF NOT EXISTS chat_messages (
    id              BIGSERIAL PRIMARY KEY    NOT NULL,
    user_id         BIGINT                   NOT NULL,
    chat_room_id    BIGINT                   NOT NULL,
    content         TEXT                     NOT NULL,
    status          TEXT                     NOT NULL,
    created_at      TIMESTAMP DEFAULT NOW()  NOT NULL,
    updated_at      TIMESTAMP
);

COMMENT ON COLUMN chat_messages.id IS '채팅메시지 id';
COMMENT ON COLUMN chat_messages.user_id IS '사용자 id';
COMMENT ON COLUMN chat_messages.chat_room_id IS '채팅방 id';
COMMENT ON COLUMN chat_messages.content IS '채팅메시지 내용';
COMMENT ON COLUMN chat_messages.status IS '채팅메시지 상태';
COMMENT ON COLUMN chat_messages.created_at IS '데이터 생성일자';
COMMENT ON COLUMN chat_messages.updated_at IS '데이터 수정일자';

CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS
    idx_chat_messages_user_id_chat_room_id ON chat_messages(user_id, chat_room_id);
