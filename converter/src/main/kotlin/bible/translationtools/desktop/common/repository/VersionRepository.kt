package bible.translationtools.desktop.common.repository

import bible.translationtools.desktop.common.data.VersionData
import io.reactivex.Single

object VersionRepository {
    private val list = arrayListOf<VersionData>()

    fun list(): Single<List<VersionData>> {
        return Single.just(list)
    }

    init {
        list.add(VersionData("ulb", "Unlocked Literal Bible"))
        list.add(VersionData("udb", "Unlocked Dynamic Bible"))
        list.add(VersionData("reg", "Regular"))
        list.add(VersionData("v4", "Version 4 (OBS)"))
    }
}
