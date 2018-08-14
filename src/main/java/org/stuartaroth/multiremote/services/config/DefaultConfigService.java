package org.stuartaroth.multiremote.services.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultConfigService implements ConfigService {
    private static Logger logger = LoggerFactory.getLogger(DefaultConfigService.class);

    @Override
    public Integer getHttpPort() {
        try {
            String httpPort = System.getenv("MULTIREMOTE_HTTP_PORT");
            if (httpPort != null) {
                return Integer.valueOf(httpPort);
            }
        } catch (Exception e) {
            logger.error("Error parsing 'MULTIREMOTE_HTTP_PORT': {}", e);
        }

        return 8834;
    }

    @Override
    public Boolean isDebugMode() {
        try {
            String debug = System.getenv("MULTIREMOTE_DEBUG");
            if (debug != null && debug.toLowerCase().equals("true")) {
                return true;
            }
        } catch (Exception e) {
            logger.error("Error parsing 'MULTIREMOTE_DEBUG': {}", e);
        }

        return false;
    }
}
