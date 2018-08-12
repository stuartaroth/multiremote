package org.stuartaroth.multiremote.remotes;

public class DefaultRemoteInfo implements RemoteInfo {
    private String key;
    private String displayName;

    public DefaultRemoteInfo(String key, String displayName) {
        this.key = key;
        this.displayName = displayName;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
