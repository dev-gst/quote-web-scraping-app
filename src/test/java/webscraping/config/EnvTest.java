package webscraping.config;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import webscraping.config.Env;

public class EnvTest {

    @BeforeAll
    static void setUpTestEnv() {
        System.setProperty("useTestConfig", "true");
    }

    @AfterAll
    static void tearDownTestEnv() {
        System.clearProperty("useTestConfig");
    }

    @Test
    void TestingEnv() {
        Assertions.assertNotNull(Env.getDbUrl());
        Assertions.assertNotNull(Env.getDbUser());
        Assertions.assertNotNull(Env.getDbPassword());
        Assertions.assertNotNull(Env.getDbDriver());
        Assertions.assertNotNull(Env.getDbSchema());
        Assertions.assertNotNull(Env.getScrapingUrl());
    }
}
