package bible.translationtools.desktop.common.mappers

import bible.translationtools.converterlib.Project
import bible.translationtools.desktop.common.data.ProjectData

class ProjectMapper : Mapper<ProjectData, Project>() {
    override fun mapFrom(from: ProjectData): Project {
        return Project(
            from.modeProperty.value,
            from.languageProperty.value,
            from.versionProperty.value,
            from.bookProperty.value,
            from.pendingProperty.value
        )
    }
}