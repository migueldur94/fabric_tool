package dev.mike.core.search;

public class Summer {
    private final ObjectService objectService;
    private final Integration integration;

    public Summer(ObjectService objectService, Integration integration) {
        this.objectService = objectService;
        this.integration = integration;
    }

    public ObjectService getObjectService() {
        return objectService;
    }

    public Integration getIntegration() {
        return integration;
    }
}
