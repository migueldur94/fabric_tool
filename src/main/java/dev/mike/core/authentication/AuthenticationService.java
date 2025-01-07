package dev.mike.core.authentication;

import dev.mike.infrastructure.authentication.FetchAccountInfo;
import dev.mike.infrastructure.authentication.SignInFacade;
import dev.mike.infrastructure.authentication.repository.ConfigurationRepository;
import dev.mike.infrastructure.authentication.repository.UserRepository;

public class AuthenticationService {

    private final UserRepository userRepository;

    private final SignInFacade signInFacade;
    
    private final ConfigurationRepository configurationRepository;

    public AuthenticationService(UserRepository userRepository, SignInFacade signInFacade, ConfigurationRepository configurationRepository) {
        this.userRepository = userRepository;
        this.signInFacade = signInFacade;
        this.configurationRepository = configurationRepository;
    }

    public void signIn(User user, boolean rememberMe) {
        signInFacade.signIn(user);
        if (rememberMe) {
            userRepository.updateUser(user);
        } else {
            userRepository.updateUser(User.empty());
        }
    }
    
    public boolean validateToken(){
        return new FetchAccountInfo().execute(configurationRepository.getConfiguration());
    }

}
