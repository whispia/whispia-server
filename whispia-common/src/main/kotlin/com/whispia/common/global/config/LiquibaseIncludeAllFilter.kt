package com.whispia.common.global.config

import liquibase.changelog.IncludeAllFilter

private const val YAML = ".yaml"

class LiquibaseIncludeAllFilter : IncludeAllFilter {

    override fun include(changeLogPath: String): Boolean {
        return changeLogPath.endsWith(YAML)
    }
}
