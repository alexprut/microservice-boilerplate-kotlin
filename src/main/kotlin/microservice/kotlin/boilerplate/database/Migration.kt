package microservice.kotlin.boilerplate.database

import org.flywaydb.core.Flyway

class Migration {
    fun migrate(url: String, user: String, password: String) {
        val flyway = Flyway.configure().dataSource(url, user, password).load()

        // Start the migration
        flyway.migrate()
    }
}