-- liquibase formatted sql

-- changeset jhyoo:7 runInTransaction:false endDelimiter:\
DO
$$
    BEGIN
        IF NOT EXISTS(
            SELECT column_name
            FROM information_schema.columns
            WHERE table_name = 'worries'
              AND column_name = 'hashtags')
        THEN
            ALTER TABLE worries ADD COLUMN IF NOT EXISTS hashtags TEXT[] NOT NULL DEFAULT '{}';
            COMMENT ON COLUMN worries.hashtags is '고민 게시글의 해시태그 목록';
        END IF;
    END
$$;
\