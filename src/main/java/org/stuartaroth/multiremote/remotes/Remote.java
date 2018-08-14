package org.stuartaroth.multiremote.remotes;

public interface Remote {
    RemoteInfo getRemoteInfo();
    void back() throws Exception;
    void info() throws Exception;
    void home() throws Exception;

    void up() throws Exception;

    void left() throws Exception;
    void enter() throws Exception;
    void right() throws Exception;

    void down() throws Exception;

    void rewind() throws Exception;
    void playpause() throws Exception;
    void forward() throws Exception;

    void mute() throws Exception;
    void volumeDown() throws Exception;
    void volumeUp() throws Exception;

    void sendText(String text) throws Exception;
}
