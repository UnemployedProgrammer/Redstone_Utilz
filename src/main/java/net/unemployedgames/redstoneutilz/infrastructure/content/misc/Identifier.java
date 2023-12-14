package net.unemployedgames.redstoneutilz.infrastructure.content.misc;

public class Identifier {
    private String namespace;
    private String object;
    public Identifier(String namespace, String object) {
        this.namespace = namespace;
        this.object = object;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getObject() {
        return object;
    }
}
