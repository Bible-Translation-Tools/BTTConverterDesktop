package bible.translationtools.desktop.converter.ui.projectdetails

import bible.translationtools.desktop.assets.AppResources
import bible.translationtools.desktop.common.data.LanguageData
import bible.translationtools.desktop.common.data.ProjectData
import bible.translationtools.desktop.common.data.VersionData
import bible.translationtools.desktop.common.onChangeAndDoNow
import bible.translationtools.desktop.common.repository.LanguageRepository
import bible.translationtools.desktop.common.repository.VersionRepository
import bible.translationtools.desktop.converter.ui.mainmenu.MainMenuViewModel
import bible.translationtools.desktop.converter.ui.messagedialog.messagedialog
import bible.translationtools.desktop.converter.utils.InputFilter
import bible.translationtools.desktop.converter.utils.LanguageConverter
import bible.translationtools.desktop.converter.utils.VersionConverter
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXCheckBox
import com.jfoenix.controls.JFXComboBox
import javafx.beans.binding.Bindings
import javafx.beans.binding.StringBinding
import javafx.collections.transformation.FilteredList
import javafx.scene.layout.Priority
import org.kordamp.ikonli.javafx.FontIcon
import tornadofx.*
import java.text.MessageFormat
import java.util.concurrent.Callable

class ProjectEditorView(project: ProjectData) : View() {

    private val viewModel: ProjectEditorViewModel by inject()
    private val mainMenuViewModel: MainMenuViewModel by inject()

    private val languages = observableListOf<LanguageData>()
    private val filteredLanguages = FilteredList(languages)

    private val versions = observableListOf<VersionData>()
    private val filteredVersions = FilteredList(versions)

    init {
        title = messages["appName"]
        importStylesheet(AppResources.load("/css/project-editor.css").toExternalForm())

        viewModel.project = project

        VersionRepository.list()
            .subscribe { list ->
                versions.setAll(list)
            }

        LanguageRepository.list()
            .subscribe { list ->
                languages.setAll(list)
            }

        initializeMessageDialog()
    }

    override val root = vbox {
        addClass("project-editor")

        hbox {
            addClass("project-editor__header")
            add(
                JFXButton(messages["back"].toUpperCase()).apply {
                    addClass("btn", "btn--secondary", "project-editor__back-button")
                    graphic = FontIcon("gmi-arrow-back")

                    setOnAction {
                        viewModel.navigateBack()
                    }
                }
            )
        }

        hbox {
            addClass("project-editor__title")
            label().apply {
                textProperty().bind(projectNameBinding())
            }
        }

        vbox {
            addClass("project-editor__body")
            vgrow = Priority.ALWAYS

            add(
                JFXComboBox<LanguageData>(filteredLanguages).apply {
                    addClass("project-editor__drop-list")
                    val comboBox = this

                    isEditable = true
                    converter = LanguageConverter()

                    cellFormat {
                        text = it.toString()
                        listView.prefWidthProperty().bind(comboBox.widthProperty())
                    }

                    // This lets open dropdown quickly
                    properties["comboBoxRowsToMeasureWidth"] = 10;

                    editor.textProperty().addListener(
                        InputFilter(
                            this,
                            filteredLanguages
                        )
                    )

                    viewModel.project.languageProperty.onChangeAndDoNow {
                        it?.let {
                            selectionModel.select(it)
                        }
                    }

                    viewModel.selectedLanguageProperty.bind(selectionModel.selectedItemProperty())
                }
            )

            region {
                addClass("project-editor__v-spacer")
                vgrow = Priority.ALWAYS
            }

            add(
                JFXComboBox(filteredVersions).apply {
                    addClass("project-editor__drop-list")
                    val comboBox = this

                    isEditable = true
                    converter = VersionConverter()

                    cellFormat {
                        text = it.toString()
                        listView.prefWidthProperty().bind(comboBox.widthProperty())
                    }

                    editor.textProperty().addListener(
                        InputFilter(
                            this,
                            filteredVersions
                        )
                    )

                    viewModel.project.versionProperty.onChangeAndDoNow {
                        it?.let {
                            selectionModel.select(it)
                        }
                    }

                    viewModel.selectedVersionProperty.bind(selectionModel.selectedItemProperty())
                }
            )

            region {
                addClass("project-editor__v-spacer")
                vgrow = Priority.ALWAYS
            }

            add(
                JFXCheckBox(messages["transformAll"]).apply {
                    addClass("project-editor__transform-all")

                    textProperty().bind(transformAllTextBinding())
                    viewModel.transformAllProperty.bind(selectedProperty())
                }
            )
        }

        hbox {
            addClass("project-editor__footer")

            add(
                JFXButton().apply {
                    addClass("btn", "btn--primary", "project-editor__footer-button")

                    textProperty().bind(transformTextBinding())

                    disableWhen(viewModel.isProcessingProperty)
                    managedWhen(visibleProperty())

                    setOnAction {
                        viewModel.transform()
                    }
                }
            )
        }
    }

    override fun onDock() {
        mainMenuViewModel.disableSetRootDirectoryProperty.set(true)
        super.onDock()
    }

    override fun onUndock() {
        mainMenuViewModel.disableSetRootDirectoryProperty.set(false)
        super.onUndock()
    }

    private fun projectNameBinding(): StringBinding {
        return Bindings.createStringBinding(
            Callable {
                viewModel.project.toString()
            },
            viewModel.project.languageProperty,
            viewModel.project.versionProperty
        )
    }

    private fun transformAllTextBinding(): StringBinding {
        return Bindings.createStringBinding(
            Callable {
                val entity = StringBuilder()
                    .append(viewModel.project.languageProperty.value?.slug)
                    .append(" | ")
                    .append(viewModel.project.versionProperty.value?.slug)
                    .toString()

                MessageFormat.format(
                    messages["transformAll"],
                    entity
                )
            },
            viewModel.project.languageProperty,
            viewModel.project.versionProperty
        )
    }

    private fun transformTextBinding(): StringBinding {
        return Bindings.createStringBinding(
            Callable {
                if (viewModel.isProcessingProperty.value) {
                    messages["transforming"].toUpperCase()
                } else {
                    messages["transform"].toUpperCase()
                }
            },
            viewModel.isProcessingProperty
        )
    }

    private fun initializeMessageDialog() {
        messagedialog {
            dialogTextProperty().bind(viewModel.messageDialogTextProperty)

            viewModel.showMessageDialogProperty.onChange {
                showDialogProperty().set(it)
            }

            onOkAction {
                viewModel.showMessageDialogProperty.set(false)
            }
        }
    }
}
