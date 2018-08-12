package org.stuartaroth.multiremote.remotes;

public interface Remote {
    RemoteInfo getRemoteInfo();
    void up();
    void down();
    void left();
    void right();
    void enter();
    void play();
    void pause();
    void back();
    void home();
    void mute();
    void volumeDown();
    void volumeUp();
    void sendKeys(String characters);
    void rewind();
    void forward();
}
