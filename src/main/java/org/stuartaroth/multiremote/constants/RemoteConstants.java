package org.stuartaroth.multiremote.constants;

import java.util.Arrays;
import java.util.List;

public class RemoteConstants {
    public static final String DEBUG_KEY = "debug";
    public static final String DEBUG_DISPLAY_NAME = "Debug Remote";

    public static final String ROKU_KEY = "roku";
    public static final String ROKU_DISPLAY_NAME = "Roku Remote";

    public static final List<String> VALID_COMMANDS = Arrays.asList(
            "back",
            "info",
            "home",
            "up",
            "left",
            "enter",
            "right",
            "down",
            "rewind",
            "playpause",
            "forward",
            "mute",
            "volumeDown",
            "volumeUp",
            "sendText"
    );
}
