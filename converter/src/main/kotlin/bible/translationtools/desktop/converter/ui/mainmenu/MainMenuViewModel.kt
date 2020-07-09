package bible.translationtools.desktop.converter.ui.mainmenu

import bible.translationtools.desktop.converter.assets.Properties
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.io.File

class MainMenuViewModel : ViewModel() {

    val rootDirectoryProperty = SimpleStringProperty()
    val disableSetRootDirectoryProperty = SimpleBooleanProperty(false)

    init {
        Properties.config.getString("root")?.let {
            rootDirectoryProperty.set(it)
        }
    }

    fun setRootDirectory(file: File) {
        rootDirectoryProperty.set(file.absolutePath)
        Properties.config.setProperty("root", file.absolutePath)
        Properties.builder.save()
    }
}