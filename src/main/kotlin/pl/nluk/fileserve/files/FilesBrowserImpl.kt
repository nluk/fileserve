package pl.nluk.fileserve.files

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import pl.nluk.fileserve.models.PathModel
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Path

@Component
class FilesBrowserImpl(
        @Autowired
        @Qualifier(value = "hostingPath")
        val hostingPath : Path
) : FilesBrowser{

    override fun list(relativeURI : String) : List<PathModel> = listPath(resolveIfAllowed(relativeURI))

    override fun get(relativeURI: String): PathModel = getFile(resolveIfAllowed(relativeURI))

    override fun normalizedURI(relativeURI: String): String = resolveIfAllowed(relativeURI).normalize().toString().removePrefix(hostingPath.toString())

    fun resolveIfAllowed(relativeURI: String) : Path{
        val resolvedPath = hostingPath.resolve(relativeURI).normalize()
        if(!resolvedPath.startsWith(hostingPath)){
            throw IllegalAccessException("Illegal path access")
        }
        return resolvedPath
    }

    private fun getFile(path: Path) : PathModel{
        if (Files.isDirectory(path)){
            throw IllegalAccessException("Not a file")
        }
        return PathModel(path.toString().removePrefix(hostingPath.toString()), false)
    }

    private fun listPath(path: Path) : List<PathModel>{
        try {
            Files.newDirectoryStream(path).use { childPath ->
                return childPath.map { PathModel(it.toString().removePrefix(hostingPath.toString()), Files.isDirectory(it)) }
                                .toList()
            }
        }catch (e : Exception){
            return emptyList()
        }
    }

}