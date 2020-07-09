package bible.translationtools.desktop.converter.ui.projectdetails

import bible.translationtools.desktop.converter.ui.mainmenu.MainMenuViewModel
import javafx.geometry.Pos
import tornadofx.*

class ProjectDetailsView : View() {

    private val mainMenuViewModel: MainMenuViewModel by inject()

    init {
        title = messages["appName"]
    }

    override val root = vbox {
        prefWidth = 600.0
        prefHeight = 480.0

        alignment = Pos.CENTER

        label("Project Details")
    }

    override fun onDock() {
        mainMenuViewModel.disableSetRootDirectoryProperty.set(true)
        super.onDock()
    }

    override fun onUndock() {
        mainMenuViewModel.disableSetRootDirectoryProperty.set(false)
        super.onUndock()
    }
}