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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RokuRemote implements Remote {
    private static Logger logger = LoggerFactory.getLogger(RokuRemote.class);

    private HttpService httpService;
    private RokuDevice rokuDevice;
    private RemoteInfo remoteInfo;

    public RokuRemote(HttpService httpService, RokuDevice rokuDevice) {
        this.httpService = httpService;
        this.rokuDevice = rokuDevice;
        this.remoteInfo = setRemoteInfo(rokuDevice);
    }

    private RemoteInfo setRemoteInfo(RokuDevice rokuDevice) {
        String key = RemoteConstants.ROKU_KEY + "-" + rokuDevice.getSerialNumber();
        String displayName = RemoteConstants.ROKU_DISPLAY_NAME + " (" + rokuDevice.getSerialNumber() + ")";
        return new DefaultRemoteInfo(key, displayName);
    }

    @Override
    public RemoteInfo getRemoteInfo() {
        return remoteInfo;
    }

    private void makeRequest(String command) throws Exception {
        HttpRequest httpRequest = new DefaultHttpRequest().setMethod(HttpConstants.POST).setUrl(rokuDevice.getAddress() + "keypress/" + command);
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

    private String getRequestCharacter(String character) {
        return "Lit_" + character;
    }

    @Override
    public void sendText(String text) throws Exception {
        String sanitized = text.replace(" ", "+");
        List<String> characters = Arrays.asList(sanitized.split(""));
        List<String> requestCharacters = characters.stream().map(this::getRequestCharacter).collect(Collectors.toList());
        for (String requestCharacter : requestCharacters) {
            makeRequest(requestCharacter);
        }
    }
}
