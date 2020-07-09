package bible.translationtools.desktop.converter

import bible.translationtools.desktop.converter.assets.AppResources
import bible.translationtools.desktop.converter.ui.boot.BootView
import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.*

class MyApp: App(BootView::class) {
    init {
        importStylesheet(AppResources.load("/css/main.css").toExternalForm())
    }
}

fun main(args: Array<String>) {
    launch<MyApp>(args)
}