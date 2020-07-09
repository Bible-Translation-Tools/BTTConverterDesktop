package bible.translationtools.desktop.converter

import bible.translationtools.desktop.converter.ui.main.MainView
import bible.translationtools.desktop.converter.ui.mainmenu.MainMenu
import tornadofx.*

class MainWorkspace : Workspace() {
    override fun onDock() {
        println("hfksjehkfjshekfjhskj")
        workspace.header.removeFromParent()
        workspace.add(MainMenu())
        workspace.dock<MainView>()
        workspace.openWindow(owner = null)
    }
}