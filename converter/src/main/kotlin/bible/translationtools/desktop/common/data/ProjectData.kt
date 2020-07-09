package bible.translationtools.desktop.common.data

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty

data class ProjectData(
    private val mode: String,
    private val language: String,
    private val version: String,
    private val book: String?,
    private val pending: Boolean
) {
    val modeProperty = SimpleStringProperty(this, "mode", mode)
    val languageProperty = SimpleStringProperty(this, "language", language)
    val versionProperty = SimpleStringProperty(this, "version", version)
    val bookProperty = SimpleStringProperty(this, "book", book)
    val pendingProperty = SimpleBooleanProperty(this, "pending", pending)

    override fun toString(): String {
        return String.format(
            "%s | %s | %s",
            languageProperty.value, versionProperty.value, bookProperty.value
        )
    }
}