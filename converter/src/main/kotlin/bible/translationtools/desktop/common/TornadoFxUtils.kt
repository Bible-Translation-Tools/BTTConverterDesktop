package bible.translationtools.desktop.common

import javafx.beans.value.ObservableValue
import tornadofx.*

fun <T> ObservableValue<T>.onChangeAndDoNow(op: (T?) -> Unit) {
    op(this.value)
    this.onChange {
        op(it)
    }
}