package bible.translationtools.desktop.common.repository

import bible.translationtools.desktop.common.data.LanguageData
import bible.translationtools.desktop.assets.AppResources
import io.reactivex.Single
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

object LanguageRepository {
    private val list = ArrayList<LanguageData>()

    private fun loadJson(): String? {
        return try {
            AppResources
                .load("/langnames.json")
                .readText(Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    private fun parseJson() {
        try {
            val jsonArr = JSONArray(loadJson())
            jsonArr.forEach {
                val obj = it as JSONObject
                list.add(
                    LanguageData(
                        obj.getString("lc"),
                        obj.getString("ln"),
                        obj.getString("ang")
                    )
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun list(): Single<List<LanguageData>> {
        return Single.just(list)
    }

    init {
        parseJson()
    }
}
