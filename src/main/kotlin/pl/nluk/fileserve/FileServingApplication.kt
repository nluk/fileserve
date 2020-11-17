package pl.nluk.fileserve

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FileServingApplication

fun main(args: Array<String>) {
	runApplication<FileServingApplication>(*args)
}
