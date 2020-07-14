package bible.translationtools.desktop.converter

import bible.translationtools.desktop.assets.AppResources
import bible.translationtools.desktop.converter.ui.boot.BootView
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*

class MyApp: App(BootView::class) {
    override fun start(stage: Stage) {
        stage.icons.add(
            Image(AppResources.load("/launcher.png").openStream())
        )
        super.start(stage)
    }
}

fun main(args: Array<String>) {
    launch<MyApp>(args)
}
