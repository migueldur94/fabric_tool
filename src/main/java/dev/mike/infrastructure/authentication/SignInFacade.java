package dev.mike.infrastructure.authentication;

import dev.mike.core.authentication.User;
import dev.mike.core.configuration.Configuration;
import dev.mike.infrastructure.authentication.repository.ConfigurationRepository;
import dev.mike.infrastructure.authentication.repository.UserRepository;
import dev.mike.infrastructure.inMemory.Cookie;
import dev.mike.infrastructure.inMemory.Token;

public class SignInFacade {

    private final UserRepository userRepository;
    private final ConfigurationRepository configurationRepository;

    public SignInFacade(UserRepository userRepository, ConfigurationRepository configurationRepository) {
        this.userRepository = userRepository;
        this.configurationRepository = configurationRepository;
    }

    public void signIn(User user) {
        Configuration configuration = configurationRepository.getConfiguration();
        new FetchCookie().execute(configuration, user);
        new FetchLogin().execute(configuration, user);
        new FetchAuthentication().execute(configuration);
        userRepository.updateToken(Token.TOKEN_AUTHENTICATION, Cookie.COOKIE);
    }
}
