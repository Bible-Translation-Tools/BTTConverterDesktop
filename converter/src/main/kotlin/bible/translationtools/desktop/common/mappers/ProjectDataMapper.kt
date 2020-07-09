package bible.translationtools.desktop.common.mappers

import bible.translationtools.converterlib.Project
import bible.translationtools.desktop.common.data.ProjectData

class ProjectDataMapper: Mapper<Project, ProjectData>() {
    override fun mapFrom(from: Project): ProjectData {
        return ProjectData(
            from.mode,
            from.language,
            from.version,
            from.book,
            from.pending
        )
    }
}