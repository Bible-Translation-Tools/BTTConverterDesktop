package bible.translationtools.desktop.converter.assets

import java.net.URL

object AppResources {
    fun load(path: String): URL {
        return AppResources.javaClass.getResource(path)
    }
}