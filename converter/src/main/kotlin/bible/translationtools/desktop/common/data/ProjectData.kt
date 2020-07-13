package bible.translationtools.desktop.common.data

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty

data class ProjectData(
    private val mode: String,
    private val language: LanguageData,
    private val version: VersionData,
    private val book: String?,
    private val shouldFix: Boolean,
    private val shouldUpdate: Boolean
) {
    val modeProperty = SimpleStringProperty(mode)
    val languageProperty = SimpleObjectProperty(language)
    val versionProperty = SimpleObjectProperty(version)
    val bookProperty = SimpleStringProperty(book)
    val shouldFixProperty = SimpleBooleanProperty(shouldFix)
    val shouldUpdateProperty = SimpleBooleanProperty(shouldUpdate)

    override fun toString(): String {
        return String.format(
            "%s | %s | %s",
            languageProperty.value.slug,
            versionProperty.value.slug,
            bookProperty.value
        )
    }
}
