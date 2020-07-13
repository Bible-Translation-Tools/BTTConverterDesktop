package bible.translationtools.desktop.common.mappers

import bible.translationtools.converterlib.Project
import bible.translationtools.desktop.common.data.LanguageData
import bible.translationtools.desktop.common.data.ProjectData
import bible.translationtools.desktop.common.data.VersionData

class ProjectDataMapper: Mapper<Project, ProjectData>() {
    override fun mapFrom(from: Project): ProjectData {
        return ProjectData(
            from.mode,
            LanguageData(from.language),
            VersionData(from.version),
            from.book,
            from.shouldFix,
            from.shouldUpdate
        )
    }
}
