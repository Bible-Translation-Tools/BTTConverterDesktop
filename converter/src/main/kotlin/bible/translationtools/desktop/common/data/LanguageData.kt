package bible.translationtools.desktop.common.data

data class LanguageData(var slug: String, var name: String? = "", var angName: String? = "") {
    override fun equals(other: Any?): Boolean {
        return (other is LanguageData) && slug == other.slug
    }

    override fun toString(): String {
        return String.format("[ %s ] %s", slug, name) +
                if (angName != name && angName != "") " ( $angName )" else ""
    }

    override fun hashCode(): Int {
        return slug.hashCode()
    }
}
