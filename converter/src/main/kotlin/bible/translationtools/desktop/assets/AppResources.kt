package bible.translationtools.desktop.assets

import java.net.URL

object AppResources {
    fun load(path: String): URL {
        return AppResources.javaClass.getResource(path)
    }
}
