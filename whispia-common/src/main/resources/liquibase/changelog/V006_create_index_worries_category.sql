-- liquibase formatted sql

-- changeset jhyoo:6 runInTransaction:false
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_worries_category ON worries(category);
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_worries_status ON worries(status);
