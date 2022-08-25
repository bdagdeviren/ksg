package tr.com.github.bdagdeviren;

import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.jboss.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

@Path("/generate")
public class GenerateResource {
//    private static final Logger LOG = Logger.getLogger(Generate.class);

    private final KubernetesClient kubernetesClient;

    public GenerateResource() {
        this.kubernetesClient = KubernetesClientProducer.kubernetesClient();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Status generate(Generate generate) {
        String namespace;
        if (generate.getNamespace() == null )
            namespace = "default";
        else
            namespace = generate.getNamespace();

        if (kubernetesClient.services().inNamespace(namespace).withName(generate.getName()).get() != null){
            return new Status(generate.getName()+" already exists!",500);
        }

        Collection<ServicePort> ports = new ArrayList<>();
        for(int i = 0; i < generate.getPorts().length; i++){
            GeneratePort port = generate.getPorts()[i];
            IntOrString portIntOrString = new IntOrString(port.getPort());
            ServicePort servicePort = new ServicePortBuilder().withName("port"+i).withProtocol(port.getType()).withPort(portIntOrString.getIntVal()).withTargetPort(portIntOrString).build();
            ports.add(servicePort);
        }
        ServiceSpec specBuilder = new ServiceSpecBuilder().addAllToPorts(ports).addToSelector(System.getenv("SELECTOR_TYPE".toLowerCase(Locale.ROOT)),System.getenv("SELECTOR_APP".toLowerCase(Locale.ROOT))).build();
        Service service = new ServiceBuilder().withNewMetadata().withName(generate.getName()).endMetadata().withSpec(specBuilder).build();

        kubernetesClient.services().inNamespace(namespace).create(service);
        return new Status("Successfully created!",204);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Status delete(Generate generate) {
        String namespace;
        if (generate.getNamespace() == null )
            namespace = "default";
        else
            namespace = generate.getNamespace();

        if (kubernetesClient.services().inNamespace(namespace).withName(generate.getName()).delete())
            return new Status("Removed service -> "+generate.getName(),204);
        else
            return new Status("Cannot find service -> "+generate.getName() ,204);
    }
}