package bible.translationtools.desktop.converter.ui.projectdetails

import bible.translationtools.converterlib.Transformer
import bible.translationtools.desktop.common.data.LanguageData
import bible.translationtools.desktop.common.data.ProjectData
import bible.translationtools.desktop.common.data.VersionData
import bible.translationtools.desktop.converter.ui.mainmenu.MainMenuViewModel
import com.github.thomasnield.rxkotlinfx.observeOnFx
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.text.MessageFormat

class ProjectEditorViewModel : ViewModel() {

    private val mainMenuViewModel: MainMenuViewModel by inject()

    lateinit var project: ProjectData

    val isProcessingProperty = SimpleBooleanProperty(false)
    val selectedLanguageProperty = SimpleObjectProperty<LanguageData>()
    val selectedVersionProperty = SimpleObjectProperty<VersionData>()
    val transformAllProperty = SimpleBooleanProperty(false)

    val showMessageDialogProperty = SimpleBooleanProperty(false)
    val messageDialogTextProperty = SimpleStringProperty()

    fun navigateBack() {
        workspace.navigateBack()
    }

    fun transform() {
        if (mainMenuViewModel.rootDirectoryProperty.value.isNotEmpty()) {
            isProcessingProperty.set(true)
            transformProject()
                .subscribeOn(Schedulers.io())
                .observeOnFx()
                .subscribe {
                    val message = StringBuilder()
                    message.append(messages["transformationComplete"])
                    message.append(" ")
                    message.append(MessageFormat.format(messages["filesUpdated"], it))

                    messageDialogTextProperty.set(message.toString())
                    showMessageDialogProperty.set(true)

                    project.languageProperty.set(selectedLanguageProperty.value)
                    project.versionProperty.set(selectedVersionProperty.value)

                    isProcessingProperty.set(false)
                }
        }
    }

    private fun transformProject(): Maybe<Int> {
        return Maybe.fromCallable {
            val newLanguage =
                if (selectedLanguageProperty.value != project.languageProperty.value) {
                    selectedLanguageProperty.value.slug
                } else {
                    null
                }

            val newVersion =
                if (selectedVersionProperty.value != project.versionProperty.value) {
                    selectedVersionProperty.value.slug
                } else {
                    null
                }

            val book =
                if (!transformAllProperty.value) project.bookProperty.value else null

            val transformer = Transformer(
                mainMenuViewModel.rootDirectoryProperty.value,
                project.languageProperty.value.slug,
                project.versionProperty.value.slug,
                book,
                newLanguage,
                null,
                newVersion
            )

            transformer.execute()
        }
    }
}
