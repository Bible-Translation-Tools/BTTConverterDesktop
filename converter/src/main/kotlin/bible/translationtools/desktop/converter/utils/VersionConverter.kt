package bible.translationtools.desktop.converter.utils

import bible.translationtools.desktop.common.data.VersionData

class VersionConverter : BaseStringConverter<VersionData>() {
    override val factory: (String, String?, String?) -> VersionData = { slug, name, _ ->
        VersionData(slug, name)
    }
}
