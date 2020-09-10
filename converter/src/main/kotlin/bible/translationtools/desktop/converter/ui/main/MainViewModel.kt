package bible.translationtools.desktop.converter.ui.main

import bible.translationtools.converterlib.Converter
import bible.translationtools.desktop.common.onChangeAndDoNow
import bible.translationtools.desktop.converter.ui.mainmenu.MainMenuViewModel
import bible.translationtools.desktop.common.data.ProjectData
import bible.translationtools.desktop.common.mappers.ProjectDataMapper
import bible.translationtools.desktop.common.mappers.ProjectMapper
import bible.translationtools.desktop.converter.ui.projectdetails.ProjectEditorView
import com.github.thomasnield.rxkotlinfx.observeOnFx
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.io.File
import java.text.MessageFormat

class MainViewModel : ViewModel() {

    private val mainMenuViewModel: MainMenuViewModel by inject()

    val isProcessingProperty = SimpleBooleanProperty(false)
    val showMessageDialogProperty = SimpleBooleanProperty(false)
    val messageDialogTextProperty = SimpleStringProperty()

    val projects = observableListOf<ProjectData>()
    val projectsProperty = SimpleListProperty<ProjectData>(projects)

    private lateinit var converter: Converter

    init {
        mainMenuViewModel.rootDirectoryProperty.onChangeAndDoNow {
            it?.let {
                if (File(it).exists()) {
                    converter = Converter(it)
                    analyze()
                }
            }
        }
    }

    fun analyze() {
        if (!this::converter.isInitialized) return

        isProcessingProperty.set(true)
        mainMenuViewModel.disableSetRootDirectoryProperty.set(true)
        converter.projects.clear()
        projects.clear()
        analyzeProjects()
            .subscribeOn(Schedulers.io())
            .observeOnFx()
            .subscribe {
                isProcessingProperty.set(false)
                mainMenuViewModel.disableSetRootDirectoryProperty.set(false)
                projects.setAll(
                    converter.projects.map {
                        ProjectDataMapper().mapFrom(it)
                    }
                )
            }
    }

    private fun analyzeProjects(): Completable {
        return Completable.fromCallable {
            converter.analyze()
        }
    }

    fun convert() {
        if (!hasEmptyModes()) {
            isProcessingProperty.set(true)
            mainMenuViewModel.disableSetRootDirectoryProperty.set(true)
            convertProjects()
                .subscribeOn(Schedulers.io())
                .observeOnFx()
                .subscribe {
                    val message = StringBuilder()
                    message.append(messages["conversionComplete"])
                    message.append(" ")
                    message.append(MessageFormat.format(messages["filesUpdated"], it))

                    messageDialogTextProperty.set(message.toString())
                    showMessageDialogProperty.set(true)

                    projects.clear()
                    converter.projects.clear()
                    isProcessingProperty.set(false)
                    mainMenuViewModel.disableSetRootDirectoryProperty.set(false)
                }
        } else {
            messageDialogTextProperty.set(messages["modeErrorMessage"])
            showMessageDialogProperty.set(true)
        }
    }

    private fun convertProjects(): Maybe<Int> {
        return Maybe.fromCallable {
            converter.projects.clear()
            converter.projects.addAll(
                projects.map {
                    ProjectMapper().mapFrom(it)
                }
            )
            converter.execute()
        }
    }

    fun openEditor(project: ProjectData) {
        workspace.dock(ProjectEditorView(project))
    }

    private fun hasEmptyModes(): Boolean {
        return projects.any {
            it.modeProperty.value.isNullOrEmpty()
        }
    }
}
