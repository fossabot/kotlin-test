package org.andtho.kotlin.web.restkotlin

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.mongodb.MongoClient
import org.andtho.kotlin.web.restkotlin.classesAndInterfaceAndInheritance.RequestRepository
import org.andtho.kotlin.web.restkotlin.classesAndInterfaceAndInheritance.RequestResource
import org.andtho.kotlin.web.restkotlin.classesAndInterfaceAndInheritance.data.Request
import org.andtho.kotlin.web.restkotlin.classesAndInterfaceAndInheritance.data.Response
import org.andtho.kotlin.web.restkotlin.person.Person
import org.andtho.kotlin.web.restkotlin.person.PersonResource
import org.glassfish.jersey.server.ResourceConfig
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Morphia
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import javax.ws.rs.ext.ContextResolver
import javax.ws.rs.ext.Provider

@Component
final class JerseyConfig : ResourceConfig() {

    init {
        registerEndpoints()
    }

    fun registerEndpoints() {
        register(PersonResource::class.java)
        register(RequestResource::class.java)
        register(MyObjectMapper::class.java)
    }
}

@Component
@Provider
class MyObjectMapper : ContextResolver<ObjectMapper> {
    override fun getContext(p0: Class<*>?): ObjectMapper {
        return ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
                .findAndRegisterModules()
    }

}


@Component
@Configuration
class ConfigureMorphia @Autowired constructor(val env : Environment) {
    @Bean
    fun datastore() : Datastore {
        val morphia = Morphia()
        morphia.map(Request::class.java)
        morphia.map(Person::class.java)

        val mongoClient = MongoClient( env.getProperty("mongodb.host"), env.getProperty("mongodb.port").toInt() )
        return morphia.createDatastore(mongoClient, "test")
    }
}