package bible.translationtools.desktop.converter.assets

import org.apache.commons.configuration2.PropertiesConfiguration
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder
import org.apache.commons.configuration2.builder.fluent.Configurations
import java.io.File

object Properties {
    val builder: FileBasedConfigurationBuilder<PropertiesConfiguration>
    val config: PropertiesConfiguration

    init {
        val configs = Configurations()
        val propertiesFile = File("config.properties")

        // Create empty properties file if it doesn't exist
        if(!propertiesFile.exists()) {
            propertiesFile.outputStream().use {
                it.write("".toByteArray())
            }
        }

        builder = configs.propertiesBuilder(propertiesFile)
        config = builder.configuration
    }
}