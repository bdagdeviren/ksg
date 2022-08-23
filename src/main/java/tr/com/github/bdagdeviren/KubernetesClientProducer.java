package tr.com.github.bdagdeviren;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import javax.inject.Singleton;
import javax.ws.rs.Produces;

@Singleton
public class KubernetesClientProducer {

    @Produces
    public static KubernetesClient kubernetesClient() {
        return new DefaultKubernetesClient();
    }
}
