package pl.nluk.fileserve.format

import pl.nluk.fileserve.models.PathModel

fun interface PathFormatter {
    fun format(path : PathModel) : Any
}