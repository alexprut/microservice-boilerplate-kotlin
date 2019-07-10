package microservice.kotlin.boilerplate

import com.google.inject.Guice
import com.google.inject.Inject
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

class Application @Inject constructor(private val server: Server) {
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