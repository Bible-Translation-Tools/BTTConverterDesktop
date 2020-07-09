package bible.translationtools.desktop.converter.ui.messagedialog

import bible.translationtools.desktop.common.onChangeAndDoNow
import bible.translationtools.desktop.converter.assets.AppResources
import com.jfoenix.controls.JFXButton
import javafx.application.Platform
import javafx.beans.property.*
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.stage.Modality
import javafx.stage.StageStyle
import tornadofx.*

class MessageDialog : Fragment() {

    private val dialogTextProperty = SimpleStringProperty()
    private val showDialogProperty = SimpleBooleanProperty()
    private val onOkActionProperty = SimpleObjectProperty<EventHandler<ActionEvent>>()

    init {
        importStylesheet(AppResources.load("/css/message-dialog.css").toExternalForm())

        showDialogProperty.onChangeAndDoNow {
            it?.let {
                Platform.runLater {
                    if (it) open() else close()
                }
            }
        }
    }

    override val root = vbox {
        addClass("message-dialog")

        hbox {
            vgrow = Priority.ALWAYS
            alignment = Pos.CENTER
            addClass("message-dialog__message-area")

            label(dialogTextProperty).apply {
                addClass("message-dialog__message")
            }
        }

        hbox {
            alignment = Pos.BOTTOM_RIGHT
            addClass("message-dialog__button-area")

            add(
                JFXButton(messages["ok"]).apply {
                    addClass("btn", "btn--primary", "message-dialog__ok")
                    onActionProperty().bind(onOkActionProperty())
                }
            )
        }
    }

    fun dialogTextProperty(): StringProperty {
        return dialogTextProperty
    }

    fun showDialogProperty(): BooleanProperty {
        return showDialogProperty
    }

    fun onOkAction(op: () -> Unit) {
        onOkActionProperty.set(EventHandler { op.invoke() })
    }

    fun onOkActionProperty(): ObjectProperty<EventHandler<ActionEvent>> {
        return onOkActionProperty
    }

    private fun open() {
        openModal(
            stageStyle = StageStyle.UNDECORATED,
            modality = Modality.APPLICATION_MODAL,
            escapeClosesWindow = false
        )
    }
}

fun messagedialog(setup: MessageDialog.() -> Unit = {}): MessageDialog {
    val messageDialog = MessageDialog()
    messageDialog.setup()
    return messageDialog
}