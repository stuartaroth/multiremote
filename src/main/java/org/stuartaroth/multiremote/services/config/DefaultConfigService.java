package org.stuartaroth.multiremote.services.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultConfigService implements ConfigService {
    private static Logger logger = LoggerFactory.getLogger(DefaultConfigService.class);

    @Override
    public Integer getHttpPort() {
        return 8834;
    }
}
