package com.whispia.api.worry.infrastructure

import com.whispia.api.worry.domain.Worry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorryJpaRepository : JpaRepository<Worry, Long>
