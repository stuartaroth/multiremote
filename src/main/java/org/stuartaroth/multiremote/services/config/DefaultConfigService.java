package org.stuartaroth.multiremote.services.config;

public class DefaultConfigService implements ConfigService {

    @Override
    public Integer getHttpPort() {
        return 8834;
    }
}
