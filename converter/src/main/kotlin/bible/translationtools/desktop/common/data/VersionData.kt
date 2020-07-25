package bible.translationtools.desktop.common.data

data class VersionData(var slug: String, var name: String? = "") {
    override fun equals(other: Any?): Boolean {
        return (other is VersionData) && slug == other.slug
    }

    override fun toString(): String {
        return String.format("[ %s ] %s", slug, name)
    }

    override fun hashCode(): Int {
        return slug.hashCode()
    }
}
