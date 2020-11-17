package pl.nluk.fileserve.format

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.util.UriUtils

@Component
class PathFormatters {
    @Bean(name = ["urlFormatter"])
    fun urlFormatter() = PathFormatter { path -> UriUtils.encodePath(path.relativePath, Charsets.UTF_8).removePrefix("/") }

    @Bean(name = ["displayPathFormatter"])
    fun displayPathFormatter() = PathFormatter { path -> path.relativePath.takeLastWhile { it != '/' } }
}