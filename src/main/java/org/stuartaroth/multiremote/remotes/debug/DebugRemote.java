package org.stuartaroth.multiremote.remotes.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stuartaroth.multiremote.constants.RemoteConstants;
import org.stuartaroth.multiremote.remotes.DefaultRemoteInfo;
import org.stuartaroth.multiremote.remotes.Remote;
import org.stuartaroth.multiremote.remotes.RemoteInfo;

public class DebugRemote implements Remote {
    private static Logger logger = LoggerFactory.getLogger(DebugRemote.class);


    @Override
    public RemoteInfo getRemoteInfo() {
        return new DefaultRemoteInfo(RemoteConstants.DEBUG_KEY, RemoteConstants.DEBUG_DISPLAY_NAME);
    }

    @Override
    public void back() throws Exception {
        logger.info("back");
    }

    @Override
    public void info() throws Exception {
        logger.info("info");
    }

    @Override
    public void home() throws Exception {
        logger.info("home");
    }

    @Override
    public void up() throws Exception {
        logger.info("up");
    }

    @Override
    public void left() throws Exception {
        logger.info("left");
    }

    @Override
    public void enter() throws Exception {
        logger.info("enter");
    }

    @Override
    public void right() throws Exception {
        logger.info("right");
    }

    @Override
    public void down() throws Exception {
        logger.info("down");
    }

    @Override
    public void rewind() throws Exception {
        logger.info("rewind");
    }

    @Override
    public void playpause() throws Exception {
        logger.info("playpause");
    }

    @Override
    public void forward() throws Exception {
        logger.info("forward");
    }

    @Override
    public void mute() throws Exception {
        logger.info("mute");
    }

    @Override
    public void volumeDown() throws Exception {
        logger.info("volumeDown");
    }

    @Override
    public void volumeUp() throws Exception {
        logger.info("volumeUp");
    }

    @Override
    public void sendText(String text) throws Exception {
        logger.info("sendText: {}", text);
    }
}
