package pl.nluk.fileserve.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.util.UriUtils
import pl.nluk.fileserve.files.FilesBrowser


@Controller
class FilesController(
        @Autowired val filesBrowser: FilesBrowser
) {

    companion object ModelAttributes{
        const val PATHS = "paths"
        const val LEVEL_UP = "levelUp"
        const val DOWNLOAD_PATH = "downloadPath"
        const val IS_ROOT = "isRoot"
    }

    @GetMapping(value = ["/browse"])
    fun listFiles(@RequestParam(name = "path") path : String?, model: Model) : String{
        val (levelUp, relativeURI) = getLevelUpAndRelativeURI(path)
        model.addAttribute(PATHS, filesBrowser.list(UriUtils.decode(relativeURI, Charsets.UTF_8)).sortedBy { it.isDirectory }.reversed())
        model.addAttribute(LEVEL_UP, levelUp)
        model.addAttribute(IS_ROOT, filesBrowser.normalizedURI(relativeURI).isBlank())
        return "browse_files"
    }

    @GetMapping(value = ["/requestDownload"])
    fun downloadFile(@RequestParam(name = "path") path : String?, model: Model) : String{
        val ( _, relativeURI ) = getLevelUpAndRelativeURI(path)
        model.addAttribute(DOWNLOAD_PATH, filesBrowser.get(relativeURI))
        return "request_download"
    }

    private fun getLevelUpAndRelativeURI(path: String?) : Pair<String, String>{
        val relativeURI = path ?: ""
        val levelUp = if(relativeURI.count { it == '/' } > 1) relativeURI.replaceAfterLast("/", "") else ""
        return Pair(levelUp, relativeURI)
    }

}