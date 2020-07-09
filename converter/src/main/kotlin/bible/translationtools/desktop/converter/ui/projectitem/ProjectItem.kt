package bible.translationtools.desktop.converter.ui.projectitem

import bible.translationtools.desktop.common.data.ProjectData
import bible.translationtools.desktop.common.onChangeAndDoNow
import bible.translationtools.desktop.converter.assets.AppResources
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXRadioButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import tornadofx.*
import tornadofx.FX.Companion.messages

class ProjectItem(project: ProjectData) : HBox() {

    init {
        importStylesheet(AppResources.load("/css/project-item.css").toExternalForm())
        addClass("project-item")

        label(project.toString()).apply {
            project.pendingProperty.onChangeAndDoNow {
                it?.let {
                    if (it) {
                        addClass("project-item__title--pending")
                    } else {
                        removeClass("project-item__title--pending")
                    }
                }
            }
        }

        region {
            hgrow = Priority.ALWAYS
        }

        hbox {
            addClass("project-item__radio-group")

            val group = ToggleGroup()
            add(
                JFXRadioButton("verse").apply {
                    addClass("project-item__radio-button")
                    toggleGroup = group
                    isSelected = project.modeProperty.value == "verse"

                    project.modeProperty.onChangeAndDoNow {
                        if (it.isNullOrEmpty()) {
                            addClass("project-item__radio-button--error")
                        } else {
                            removeClass("project-item__radio-button--error")
                        }
                    }

                    selectedProperty().onChange {
                        if (it) {
                            project.modeProperty.set("verse")
                            project.pendingProperty.set(true)
                        }
                    }
                }
            )
            add(
                JFXRadioButton("chunk").apply {
                    addClass("project-item__radio-button")
                    toggleGroup = group
                    isSelected = project.modeProperty.value == "chunk"

                    project.modeProperty.onChangeAndDoNow {
                        if (it.isNullOrEmpty()) {
                            addClass("project-item__radio-button--error")
                        } else {
                            removeClass("project-item__radio-button--error")
                        }
                    }

                    selectedProperty().onChange {
                        if (it) {
                            project.modeProperty.set("chunk")
                            project.pendingProperty.set(true)
                        }
                    }
                }
            )
        }

        region {
            hgrow = Priority.ALWAYS
            maxWidth = 10.0
        }

        add(
            JFXButton(messages["edit"].toUpperCase()).apply {
                addClass("btn", "btn--secondary", "project-item__edit-button")
            }
        )
    }
}

fun projectitem(
    project: ProjectData,
    init: ProjectItem.() -> Unit = {}
): ProjectItem {
    val item = ProjectItem(project)
    item.init()
    return item
}