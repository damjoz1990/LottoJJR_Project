//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pl.lotto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import java.util.Objects;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest(
        classes = {LottoSpringBootApplication.class}
)
@ActiveProfiles({"integration"})
@AutoConfigureMockMvc
@Testcontainers
public class BaseIntegrationTest {
    public static final String WIRE_MOCK_HOST = "http://localhost";
    @Autowired
    public MockMvc mockMvc;
    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @Autowired
    public ObjectMapper objectMapper;
    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance().options(WireMockConfiguration.wireMockConfig().dynamicPort()).build();

    public BaseIntegrationTest() {
    }

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        MongoDBContainer var10002 = mongoDBContainer;
        Objects.requireNonNull(var10002);
        registry.add("spring.data.mongodb.uri", var10002::getReplicaSetUrl);
    }
}
