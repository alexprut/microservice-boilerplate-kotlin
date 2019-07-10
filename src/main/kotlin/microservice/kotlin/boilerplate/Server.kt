package microservice.kotlin.boilerplate

import com.google.inject.Inject
import com.google.inject.Injector
import com.google.inject.name.Named
import io.undertow.Handlers
import io.undertow.Undertow
import io.undertow.server.handlers.BlockingHandler
import microservice.kotlin.boilerplate.handlers.ExampleHandler
import mu.KotlinLogging

class Server @Inject constructor(
    @Named("port") private val port: Int,
    @Named("hostname") private val hostname: String,
    injector: Injector
) {

    companion object {
        @JvmStatic
        private val logger = KotlinLogging.logger {}
    }

    private val undertow: Undertow

    init {
        val handler = Handlers.routing()
            .get("/api/example/{id}", BlockingHandler(injector.getInstance(ExampleHandler::class.java)))

        undertow = Undertow.builder()
            .addHttpListener(port, hostname)
            .setHandler(handler)
            .build()
    }

    fun start() {
        undertow.start()
        logger.info("Server listening on port $port")
    }
}