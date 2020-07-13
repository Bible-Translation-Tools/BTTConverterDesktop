package bible.translationtools.desktop.converter

import bible.translationtools.desktop.converter.ui.boot.BootView
import tornadofx.*

class MyApp: App(BootView::class)

fun main(args: Array<String>) {
    launch<MyApp>(args)
}
