package et.com.gebeya.servicediscovery;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest()
class ServiceDiscoveryApplicationTests {




	@Test
	void creationOfServiceDiscoveryApplication(){
		ServiceDiscoveryApplication application = new ServiceDiscoveryApplication();
		assertNotNull(application);
	}


}
