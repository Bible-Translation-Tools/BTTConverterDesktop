package bible.translationtools.desktop.converter.ui.boot

import bible.translationtools.desktop.converter.ui.main.MainView
import bible.translationtools.desktop.converter.ui.mainmenu.MainMenu
import bible.translationtools.desktop.converter.ui.mainmenu.MainMenuViewModel
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ChangeListener
import tornadofx.*

class BootViewModel :ViewModel() {

    private val mainMenuViewModel: MainMenuViewModel by inject()

    val rootDirectoryProperty = SimpleStringProperty()
    val shouldCloseProperty = SimpleBooleanProperty(false)

    private val rootDirectoryChangeListener = ChangeListener<String> { _, _, rootDir ->
        if (!rootDir.isNullOrEmpty()) {
            runLater {
                boot()
            }
        }
    }

    init {
        rootDirectoryProperty.addListener(rootDirectoryChangeListener)
        rootDirectoryProperty.bind(mainMenuViewModel.rootDirectoryProperty)
    }

    private fun boot() {
        workspace.header.removeFromParent()
        workspace.add(MainMenu())
        workspace.dock<MainView>()
        workspace.openWindow(owner = null)
        shouldCloseProperty.set(true)
        rootDirectoryProperty.removeListener(rootDirectoryChangeListener)
    }
}