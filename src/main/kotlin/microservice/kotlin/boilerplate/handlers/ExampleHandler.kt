package microservice.kotlin.boilerplate.handlers

import com.google.gson.Gson
import com.google.inject.Inject
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import microservice.kotlin.boilerplate.handlers.exceptions.ValidationException

class ExampleHandler @Inject constructor(
    private val gson: Gson
) : HttpHandler {
    override fun handleRequest(exchange: HttpServerExchange) = Utils.handleRequest(exchange, gson) {
        val id = exchange.queryParameters["id"]?.first

        when {
            id.isNullOrEmpty() -> throw ValidationException("id parameter not provided")
            else -> {
                val response = mapOf(
                    "example" to id
                )

                gson.toJson(response)
            }
        }
    }
}