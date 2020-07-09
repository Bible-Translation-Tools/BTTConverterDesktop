package bible.translationtools.desktop.converter.ui.mainmenu

import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import org.kordamp.ikonli.javafx.FontIcon
import tornadofx.*
import tornadofx.FX.Companion.messages


class MainMenu : MenuBar() {

    private val viewModel: MainMenuViewModel = find()

    private fun Menu.setRootMenuItem(message: String): MenuItem {
        return item(message) {
            graphic = FontIcon("gmi-home")
            disableProperty().bind(viewModel.disableSetRootDirectoryProperty)
        }
    }

    init {
        with(this) {
            menu(messages["file"]) {
                setRootMenuItem(messages["setRootDirectory"])
                    .setOnAction {
                        val file = chooseDirectory(messages["setRootDirectory"])
                        file?.let {
                            viewModel.setRootDirectory(file)
                        }
                    }
            }
        }
    }
}