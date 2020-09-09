package ro.cadeca.weasylearn

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.PostgreSQLContainer
import ro.cadeca.vitele.ViteleBeApplication


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [ViteleBeApplication::class], initializers = [BasePostgreSQLContainerIT.Initializer::class])
@ActiveProfiles(profiles = ["prod"])
abstract class BasePostgreSQLContainerIT {
	companion object {
		val postgres = PostgreSQLContainer<Nothing>().apply {
			withDatabaseName("postgres")
			withUsername("integrationUser")
			withPassword("testPass")
		}
	}

	internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
		override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
			postgres.start()
			TestPropertyValues.of(
					"spring.datasource.url=" + postgres.jdbcUrl,
					"spring.datasource.username=" + postgres.username,
					"spring.datasource.password=" + postgres.password
			).applyTo(configurableApplicationContext.environment)
		}
	}
}