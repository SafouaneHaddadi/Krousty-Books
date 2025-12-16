package com.biblio.backend;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test qui vérifie que l'application démarre correctement.
 * Grâce au profil "test", on utilise H2 en mémoire au lieu de PostgreSQL.
 */
@SpringBootTest
@ActiveProfiles("test")
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
