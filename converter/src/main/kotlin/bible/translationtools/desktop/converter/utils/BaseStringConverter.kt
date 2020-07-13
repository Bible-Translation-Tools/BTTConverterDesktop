package bible.translationtools.desktop.converter.utils

import javafx.util.StringConverter

abstract class BaseStringConverter<T> : StringConverter<T>() {

    abstract val factory: (group1: String, group2: String?, group3: String?) -> T

    override fun toString(data: T?): String {
        return data.toString()
    }

    override fun fromString(string: String?): T? {
        val result = getMatchResult(string)
        return result?.let {
            val group1 = result.groups[1]?.value ?: throw IllegalArgumentException("Should have the first group!")
            val group2 = result.groups[2]?.value
            val group3 = result.groups[3]?.value
            factory(group1, group2, group3)
        }
    }

    private fun getMatchResult(string: String?): MatchResult? {
        return string?.let {
            val regex = """\[\s(.*)\s\]\s(.*)(\s\(.*\))?""".toRegex()
            regex.find(string)
        }
    }
}
