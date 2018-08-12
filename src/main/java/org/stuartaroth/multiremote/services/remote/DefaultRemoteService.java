package org.stuartaroth.multiremote.services.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stuartaroth.multiremote.remotes.Remote;
import org.stuartaroth.multiremote.remotes.roku.RokuRemote;
import org.stuartaroth.multiremote.services.http.HttpService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class DefaultRemoteService implements RemoteService {
    private static Logger logger = LoggerFactory.getLogger(DefaultRemoteService.class);

    private HttpService httpService;
    private Map<String, Remote> remotes;

    public DefaultRemoteService(HttpService httpService) {
        this.httpService = httpService;
        scan();
    }

    private void scan() {
        logger.info("starting scan");

        remotes = new HashMap<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

        // RokuRemote initialization
        Callable<Remote> callableRokuRemote = () -> new RokuRemote(httpService);
        Future<Remote> futureRokuRemote = executorService.submit(callableRokuRemote);
        try {
            Remote rokuRemote = futureRokuRemote.get(5, TimeUnit.SECONDS);
            remotes.put(rokuRemote.getRemoteInfo().getKey(), rokuRemote);
        } catch (Exception e) {
            logger.error("Exception creaking roku remote");
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
