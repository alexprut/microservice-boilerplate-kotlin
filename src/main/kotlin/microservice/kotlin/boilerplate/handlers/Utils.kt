package microservice.kotlin.boilerplate.handlers

import com.google.gson.Gson
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers
import io.undertow.util.HttpString
import microservice.kotlin.boilerplate.handlers.exceptions.NotFoundException
import microservice.kotlin.boilerplate.handlers.exceptions.ValidationException
import mu.KotlinLogging

object Utils {
    private val logger = KotlinLogging.logger { }

    fun handleRequest(exchange: HttpServerExchange, gson: Gson, response: (() -> String)) {
        exchange.responseHeaders.put(HttpString("X-FRAME-OPTIONS"), "SAMEORIGIN")
        exchange.responseHeaders.put(HttpString("X-XSS-Protection"), "1; mode=block")
        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "application/json")

        try {
            exchange.responseSender.send(response())
        } catch (e: ValidationException) {
            exchange.statusCode = 400
            exchange.responseSender.send(errorToJson(gson, e))
        } catch (e: NotFoundException) {
            exchange.statusCode = 404
            exchange.responseSender.send(errorToJson(gson, e))
        } catch (e: Exception) {
            logger.error("Service error", e.message)
            e.printStackTrace()
            exchange.statusCode = 500
            // Important: do not expose the Exception message externally
            exchange.responseSender.send(errorToJson(gson, "server error"))
        }
    }

    private fun errorToJson(gson: Gson, e: Exception): String = gson.toJson(mapOf("error" to e.message))

    private fun errorToJson(gson: Gson, error: String): String = gson.toJson(mapOf("error" to error))
}