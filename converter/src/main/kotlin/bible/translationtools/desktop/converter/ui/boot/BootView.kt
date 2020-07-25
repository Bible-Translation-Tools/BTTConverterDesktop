package bible.translationtools.desktop.converter.ui.boot

import bible.translationtools.desktop.assets.AppResources
import bible.translationtools.desktop.converter.ui.mainmenu.MainMenu
import javafx.scene.layout.Priority
import tornadofx.*

class BootView : View() {

    private val viewModel: BootViewModel by inject()

    init {
        importStylesheet(AppResources.load("/css/boot.css").toExternalForm())

        viewModel.shouldCloseProperty.onChange {
            if (it) close()
        }
    }

    override val root = vbox {
        addClass("boot")

        add(MainMenu())

        vbox {
            vgrow = Priority.ALWAYS
            addClass("boot__content")

            label(messages["setRootDirectoryTip"]) {
                addClass("label--message")
            }
        }
    }
}
