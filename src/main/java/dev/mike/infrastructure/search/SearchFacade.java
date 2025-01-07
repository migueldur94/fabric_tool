package dev.mike.infrastructure.search;

import dev.mike.core.configuration.Configuration;
import dev.mike.core.search.Integration;
import dev.mike.core.search.ObjectService;
import dev.mike.core.search.Summer;
import dev.mike.infrastructure.authentication.repository.ConfigurationRepository;
import dev.mike.infrastructure.inMemory.UrlTemenos;

public class SearchFacade {

    private final ConfigurationRepository configurationRepository;

    public SearchFacade(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    public Summer search() {
        Configuration configuration = this.configurationRepository.getConfiguration();

        ObjectService objectService = new ObjectService();
        objectService.setName(UrlTemenos.VERB_NAME);

        Integration integration = new Integration();

        String urlMapping = new FetchObjectService().execute(configuration);
        String urlVerb = new FetchOperationObject().execute(urlMapping, objectService, configuration);
        String urlIntegration = FetchVerb.execute(urlVerb, objectService, integration, configuration);
        new FetchIntegration().execute(urlIntegration, integration);

        return new Summer(objectService, integration);
    }
}
