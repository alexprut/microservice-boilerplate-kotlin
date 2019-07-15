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
        bindConstant().annotatedWith(Names.named("databaseUser"))
            .to(System.getenv("DATABASE_USER"))
        bindConstant().annotatedWith(Names.named("databasePassword"))
            .to(System.getenv("DATABASE_PASSWORD"))
        bindConstant().annotatedWith(Names.named("databaseHost"))
            .to(System.getenv("DATABASE_HOST") ?: "0.0.0.0")
        bindConstant().annotatedWith(Names.named("databaseName"))
            .to(System.getenv("DATABASE_NAME"))
    }

    private fun configInstances() {
        bind(Gson::class.java).toInstance(GsonBuilder().create())
    }
}