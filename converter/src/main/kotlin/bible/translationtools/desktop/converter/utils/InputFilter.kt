package bible.translationtools.desktop.converter.utils

import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.transformation.FilteredList
import javafx.scene.control.ComboBox

class InputFilter<in T>(
    private val box: ComboBox<T>,
    private val items: FilteredList<T>
) : ChangeListener<String> {

    override fun changed(
        observable: ObservableValue<out String>,
        oldValue: String,
        newValue: String
    ) {
        val value: StringProperty = SimpleStringProperty(newValue)

        val selected =
            if (box.selectionModel.selectedItem != null) box.selectionModel.selectedItem else null
        var selectedText: String? = null

        if (selected != null) {
            selectedText = selected.toString()
        }

        if (selected != null && value.get() == selectedText) {
            Platform.runLater { box.editor.end() }
        } else {
            items.setPredicate { item: T ->
                return@setPredicate item.toString().toLowerCase().contains(value.get().toLowerCase())
            }
        }

        if (!box.isShowing) {
            if (newValue.isNotEmpty() && box.isFocused) {
                box.show()
            }
        } else {
            if (items.size == 1) {
                val item = items[0]

                if (value.get() == item.toString()) {
                    Platform.runLater { box.hide() }
                }
            }
        }
        box.editor.text = value.get()
    }
}
