package com.hyoseok.kotlinjpa.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing
class JpaAuditConfig {

    @Bean
    fun auditorProvider(): AuditorAware<String> {
        return AuditorAware { Optional.of(UUID.randomUUID().toString()) }
    }

}