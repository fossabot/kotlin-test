package org.andtho.kotlin.web.restkotlin.classesAndInterfaceAndInheritance

import com.mongodb.annotations.ThreadSafe
import org.andtho.kotlin.web.restkotlin.MongoServerResource
import org.andtho.kotlin.web.restkotlin.classesAndInterfaceAndInheritance.data.Request
import org.bson.types.ObjectId
import org.junit.Assert.assertNotNull
import org.junit.ClassRule
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertTrue

@ThreadSafe
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RequestRepositoryTest {

    companion object {
        @JvmField @ClassRule
        val mongoServer = MongoServerResource
    }
    // @Rule
    // @JvmField
    // @get:Rule val mongoServer = MongoServerPerTestResource

    @Autowired lateinit var requestRepository: RequestRepository
    @Autowired lateinit var restTemplate: TestRestTemplate;

    @Ignore
    @Test
    fun store() {


        val id = requestRepository.store(Request(requestId = "123"))
        assertNotNull( id )
        assertTrue { ObjectId.isValid(id) }

        val entity = restTemplate.getForEntity("/request/$id", Request::class.java)
        assertNotNull( entity )
    }
}