package bible.translationtools.desktop.converter.ui.main

import bible.translationtools.desktop.assets.AppResources
import bible.translationtools.desktop.converter.ui.messagedialog.messagedialog
import bible.translationtools.desktop.converter.ui.projectitem.projectitem
import com.jfoenix.controls.JFXButton
import javafx.beans.binding.Bindings
import javafx.beans.binding.BooleanBinding
import javafx.beans.binding.StringBinding
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import tornadofx.*
import java.util.concurrent.Callable

class MainView : View() {
    private val mainViewModel: MainViewModel by inject()

    init {
        title = messages["appName"]
        importStylesheet(AppResources.load("/css/main.css").toExternalForm())
        initializeMessageDialog()
    }

    override val root = vbox {
        addClass("main")

        alignment = Pos.CENTER

        listview(mainViewModel.projects) {
            isFocusTraversable = false
            disableWhen(mainViewModel.isProcessingProperty)

            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS

            cellFormat {
                graphic = projectitem(item).apply {
                    onEditAction {
                        mainViewModel.openEditor(item)
                    }
                }
            }
        }

        hbox {
            addClass("main__mode_error")

            label(messages["modeErrorMessage"]).apply {
                visibleWhen(errorMessageShowBinding())
                managedWhen(visibleProperty())
            }
        }

        region {
            prefHeight  = 20.0
        }

        hbox {
            addClass("main__footer")

            add(
                JFXButton().apply {
                    addClass("btn", "btn--primary", "main__footer-button")

                    textProperty().bind(convertTextBinding())

                    disableWhen(mainViewModel.isProcessingProperty)
                    hiddenWhen(mainViewModel.projectsProperty.emptyProperty())
                    managedWhen(visibleProperty())

                    setOnAction {
                        mainViewModel.convert()
                    }
                }
            )

            add(
                JFXButton(messages["analyze"].toUpperCase()).apply {
                    addClass("btn", "btn--primary", "main__footer-button")

                    textProperty().bind(analyzeTextBinding())

                    disableWhen(mainViewModel.isProcessingProperty)
                    visibleWhen(mainViewModel.projectsProperty.emptyProperty())
                    managedWhen(visibleProperty())

                    setOnAction {
                        mainViewModel.analyze()
                    }
                }
            )
        }
    }

    private fun initializeMessageDialog() {
        messagedialog {
            dialogTextProperty().bind(mainViewModel.messageDialogTextProperty)

            mainViewModel.showMessageDialogProperty.onChange {
                showDialogProperty().set(it)
            }

            onOkAction {
                mainViewModel.showMessageDialogProperty.set(false)
            }
        }
    }

    private fun convertTextBinding(): StringBinding {
        return Bindings.createStringBinding(
            Callable {
                if (mainViewModel.isProcessingProperty.value) {
                    messages["converting"].toUpperCase()
                } else {
                    messages["convert"].toUpperCase()
                }
            },
            mainViewModel.isProcessingProperty
        )
    }

    private fun analyzeTextBinding(): StringBinding {
        return Bindings.createStringBinding(
            Callable {
                if (mainViewModel.isProcessingProperty.value) {
                    messages["analyzing"].toUpperCase()
                } else {
                    messages["analyze"].toUpperCase()
                }
            },
            mainViewModel.isProcessingProperty
        )
    }

    private fun errorMessageShowBinding(): BooleanBinding {
        return Bindings.createBooleanBinding(
            Callable {
                mainViewModel.projectsProperty.any {
                    it.modeProperty.value.isNullOrEmpty()
                }
            },
            mainViewModel.projectsProperty
        )
    }

    override fun onDock() {
        mainViewModel.analyze()
    }
}
