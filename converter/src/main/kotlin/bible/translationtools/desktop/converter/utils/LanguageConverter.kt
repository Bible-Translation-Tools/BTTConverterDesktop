package bible.translationtools.desktop.converter.utils

import bible.translationtools.desktop.common.data.LanguageData

class LanguageConverter : BaseStringConverter<LanguageData>() {
    override val factory: (String, String?, String?) -> LanguageData = { slug, name, angName ->
        LanguageData(slug, name, angName)
    }
}
