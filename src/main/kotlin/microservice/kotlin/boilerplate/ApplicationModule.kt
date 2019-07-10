package microservice.kotlin.boilerplate

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.inject.AbstractModule
import com.google.inject.name.Names

class ApplicationModule : AbstractModule() {
    override fun configure() {
        super.configure()
        configConstants()
        configInstances()
    }

    private fun configConstants() {
        bindConstant().annotatedWith(Names.named("port"))
            .to(System.getenv("HTTP_PORT") ?: "8080")
        bindConstant().annotatedWith(Names.named("hostname"))
            .to(System.getenv("HTTP_HOST") ?: "0.0.0.0")
    }

    private fun configInstances() {
        bind(Gson::class.java).toInstance(GsonBuilder().create())
    }
}