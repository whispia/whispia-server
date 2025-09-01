-- liquibase formatted sql

-- changeset jhyoo:8 runInTransaction:false
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_worries_hashtags ON worries USING gin (hashtags);
