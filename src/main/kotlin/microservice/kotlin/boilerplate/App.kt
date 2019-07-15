package microservice.kotlin.boilerplate

import com.google.inject.Guice
import com.google.inject.Inject
import com.google.inject.name.Named
import microservice.kotlin.boilerplate.database.Migration
import mu.KotlinLogging
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

fun main() {
    try {
        logger.info("Initializing Service")
        val injector = Guice.createInjector(ApplicationModule())
        val application = injector.getInstance(Application::class.java)

        application.start()

        logger.info("Service started")
    } catch (e: Exception) {
        logger.error("Service failed to initialize", e)
        exitProcess(1)
    }
}

class Application @Inject constructor(
    private val server: Server,
    @Named("databaseUser") private val databaseUser: String,
    @Named("databasePassword") private val databasePassword: String,
    @Named("databaseHost") private val databaseHost: String,
    @Named("databaseName") private val databaseName: String
) {
    companion object {
        @JvmStatic
        private val logger = KotlinLogging.logger {}
    }

    /**
     * Setup, initiate and starts the Application
     */
    fun start() {
        registerHooks()
        recordRuntimeConfig()
        Migration().migrate("jdbc:postgresql://$databaseHost/$databaseName", databaseUser, databasePassword)

        server.start()
    }

    private fun registerHooks() {
        Thread.currentThread().uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { t, e ->
            logger.error("Uncaught exception ${e.message} on thread ${t.id}")
            logger.error("Error:", e)
            logger.info("Shutting down.")
            exitProcess(1)
        }
    }

    private fun recordRuntimeConfig() {
        val runtime = Runtime.getRuntime()
        logger.info(
            String.format("System XMX=%smb, XMS=%smb, Processor=%s",
                runtime.maxMemory() / 1024 / 1024,
                runtime.totalMemory() / 1024 / 1024,
                runtime.availableProcessors()))
    }
}