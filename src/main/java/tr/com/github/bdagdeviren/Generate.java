package tr.com.github.bdagdeviren;

public class Generate {
    private String name;
    private String namespace;
    private GeneratePort[] ports;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeneratePort[] getPorts() {
        return ports;
    }

    public void setPorts(GeneratePort[] ports) {
        this.ports = ports;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
