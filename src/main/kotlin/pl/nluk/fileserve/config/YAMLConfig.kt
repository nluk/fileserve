package pl.nluk.fileserve.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.nio.file.Path
import java.nio.file.Paths

@EnableConfigurationProperties(value = [YAMLConfig::class])
@ConfigurationProperties(prefix = "application")
@Component
data class YAMLConfig (
    var hostingPath : String = ""
)
{
    @Bean(name = ["hostingPath"])
    fun hostingPath() : Path {
        return Paths.get(hostingPath)
    }
}