package org.stuartaroth.multiremote.services.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stuartaroth.multiremote.remotes.Remote;
import org.stuartaroth.multiremote.remotes.debug.DebugRemote;
import org.stuartaroth.multiremote.remotes.roku.DefaultRokuDiscoveryService;
import org.stuartaroth.multiremote.remotes.roku.RokuDevice;
import org.stuartaroth.multiremote.remotes.roku.RokuDiscoveryService;
import org.stuartaroth.multiremote.remotes.roku.RokuRemote;
import org.stuartaroth.multiremote.services.config.ConfigService;
import org.stuartaroth.multiremote.services.http.HttpService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultRemoteService implements RemoteService {
    private static Logger logger = LoggerFactory.getLogger(DefaultRemoteService.class);

    private ConfigService configService;
    private HttpService httpService;
    private Map<String, Remote> remotes;

    public DefaultRemoteService(ConfigService configService, HttpService httpService) {
        this.configService = configService;
        this.httpService = httpService;
        scan();
    }

    private void scan() {
        logger.info("starting scan");

        remotes = new HashMap<>();

        // DebugRemote initialization
        if (configService.isDebugMode()) {
            Remote debugRemote = new DebugRemote();
            remotes.put(debugRemote.getRemoteInfo().getKey(), debugRemote);
        }

        // RokuRemotes initialization
        RokuDiscoveryService rokuDiscoveryService = new DefaultRokuDiscoveryService();
        List<RokuDevice> rokuDevices = rokuDiscoveryService.getRokuDevices();
        for (RokuDevice rokuDevice : rokuDevices) {
            Remote rokuRemote = new RokuRemote(httpService, rokuDevice);
            remotes.put(rokuRemote.getRemoteInfo().getKey(), rokuRemote);
        }

        logger.info("found remotes: {}", remotes.keySet());
        logger.info("ending scan");
    }

    @Override
    public Map<String, Remote> getRemotes() {
        if (remotes == null) {
            scan();
        }

        return remotes;
    }

    @Override
    public Map<String, Remote> getRemotes(Boolean rescan) {
        if (rescan != null && rescan) {
            scan();
        }

        return remotes;
    }
}
