package org.stuartaroth.multiremote.services.remote;

import org.stuartaroth.multiremote.remotes.Remote;

import java.util.Map;

public interface RemoteService {
    Map<String, Remote> getRemotes();
    Map<String, Remote> getRemotes(Boolean rescan);
}
