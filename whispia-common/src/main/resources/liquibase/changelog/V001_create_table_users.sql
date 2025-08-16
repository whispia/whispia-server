-- liquibase formatted sql

-- changeset jhyoo:1 runInTransaction:false
CREATE TABLE IF NOT EXISTS users (
    id          BIGSERIAL PRIMARY KEY    NOT NULL,
    key         UUID                     NOT NULL,
    status      TEXT                     NOT NULL,
    email       TEXT                     NOT NULL,
    password    TEXT                     NOT NULL,
    nickname    TEXT                     NOT NULL,
    created_at  TIMESTAMP DEFAULT NOW()  NOT NULL,
    updated_at  TIMESTAMP
);

COMMENT ON COLUMN users.id IS '사용자 id';
COMMENT ON COLUMN users.key IS '사용자 고유 키';
COMMENT ON COLUMN users.status IS '사용자의 status';
COMMENT ON COLUMN users.email IS '사용자의 이메일';
COMMENT ON COLUMN users.password IS '사용자의 비밀번호 해시';
COMMENT ON COLUMN users.nickname IS '사용자의 닉네임';
COMMENT ON COLUMN users.created_at IS '데이터 생성일자';
COMMENT ON COLUMN users.updated_at IS '데이터 수정일자';

CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS uidx_users_email ON users(email);
CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS uidx_users_key ON users(key);
CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS uidx_users_nickname ON users(nickname);
