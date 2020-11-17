package pl.nluk.fileserve.files

import pl.nluk.fileserve.models.PathModel

interface FilesBrowser {
    fun list(relativeURI : String) : List<PathModel>
    fun get(relativeURI: String) : PathModel
    fun normalizedURI(relativeURI: String) : String
}