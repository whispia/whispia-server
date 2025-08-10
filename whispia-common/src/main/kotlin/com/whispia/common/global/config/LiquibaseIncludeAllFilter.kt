package com.whispia.common.global.config

import liquibase.changelog.IncludeAllFilter

private const val SQL_FORMAT = ".sql"

class LiquibaseIncludeAllFilter : IncludeAllFilter {

    override fun include(changeLogPath: String): Boolean {
        return changeLogPath.endsWith(SQL_FORMAT)
    }
}
