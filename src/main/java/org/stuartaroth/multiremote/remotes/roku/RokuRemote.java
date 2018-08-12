package org.stuartaroth.multiremote.remotes.roku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stuartaroth.multiremote.constants.HttpConstants;
import org.stuartaroth.multiremote.constants.RemoteConstants;
import org.stuartaroth.multiremote.remotes.DefaultRemoteInfo;
import org.stuartaroth.multiremote.remotes.Remote;
import org.stuartaroth.multiremote.remotes.RemoteInfo;
import org.stuartaroth.multiremote.services.http.DefaultHttpRequest;
import org.stuartaroth.multiremote.services.http.HttpRequest;
import org.stuartaroth.multiremote.services.http.HttpService;

public class RokuRemote implements Remote {
    private static Logger logger = LoggerFactory.getLogger(RokuRemote.class);

    private HttpService httpService;
    private String rokuAddress;

    public RokuRemote(HttpService httpService) throws Exception {
        this.httpService = httpService;

        RokuDiscoveryService rokuDiscoveryService = new RokuDiscoveryService();
        this.rokuAddress = rokuDiscoveryService.getRokuAddress();
    }

    @Override
    public RemoteInfo getRemoteInfo() {
        return new DefaultRemoteInfo(RemoteConstants.ROKU_KEY, RemoteConstants.ROKU_DISPLAY_NAME);
    }

    private void makeRequest(String command) throws Exception {
        HttpRequest httpRequest = new DefaultHttpRequest().setMethod(HttpConstants.POST).setUrl(rokuAddress + "keypress/" + command);
        httpService.makeRequest(httpRequest);
    }

    @Override
    public void back() throws Exception {
        makeRequest("Back");
    }

    @Override
    public void info() throws Exception {
        makeRequest("Info");
    }

    @Override
    public void home() throws Exception {
        makeRequest("Home");
    }

    @Override
    public void up() throws Exception {
        makeRequest("Up");
    }

    @Override
    public void left() throws Exception {
        makeRequest("Left");
    }

    @Override
    public void enter() throws Exception {
        makeRequest("Select");
    }

    @Override
    public void right() throws Exception {
        makeRequest("Right");
    }

    @Override
    public void down() throws Exception {
        makeRequest("Down");
    }

    @Override
    public void rewind() throws Exception {
        makeRequest("Rev");
    }

    @Override
    public void playpause() throws Exception {
        makeRequest("Play");
    }

    @Override
    public void forward() throws Exception {
        makeRequest("Fwd");
    }

    @Override
    public void mute() throws Exception {
        makeRequest("VolumeMute");
    }

    @Override
    public void volumeDown() throws Exception {
        makeRequest("VolumeDown");
    }

    @Override
    public void volumeUp() throws Exception {
        makeRequest("VolumeUp");
    }
}
