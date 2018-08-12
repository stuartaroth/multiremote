package org.stuartaroth.multiremote.remotes;

import org.stuartaroth.multiremote.constants.RemoteConstants;
import org.stuartaroth.multiremote.services.http.HttpService;

public class RokuRemote implements Remote {
    private HttpService httpService;

    public RokuRemote(HttpService httpService) {
        this.httpService = httpService;
    }

    @Override
    public RemoteInfo getRemoteInfo() {
        return new DefaultRemoteInfo(RemoteConstants.ROKU_KEY, RemoteConstants.ROKU_DISPLAY_NAME);
    }

    @Override
    public void up() {

    }

    @Override
    public void down() {

    }

    @Override
    public void left() {

    }

    @Override
    public void right() {

    }

    @Override
    public void enter() {

    }

    @Override
    public void play() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void back() {

    }

    @Override
    public void home() {

    }

    @Override
    public void mute() {

    }

    @Override
    public void volumeDown() {

    }

    @Override
    public void volumeUp() {

    }

    @Override
    public void sendKeys(String characters) {

    }

    @Override
    public void rewind() {

    }

    @Override
    public void forward() {

    }
}
