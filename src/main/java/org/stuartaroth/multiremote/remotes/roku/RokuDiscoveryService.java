package org.stuartaroth.multiremote.remotes.roku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RokuDiscoveryService {
    private static Logger logger = LoggerFactory.getLogger(RokuDiscoveryService.class);

    // todo discovery needs to be made more error proof

    private Pattern addressRegex;

    public RokuDiscoveryService() {
        addressRegex = Pattern.compile("LOCATION: (http[\\S]*)");
    }

    public String getRokuAddress() throws Exception {
        String receiveBody = getReceiveBody();

        logger.info("receiveBody: {}", receiveBody);

        Matcher matcher = addressRegex.matcher(receiveBody);
        boolean matchFound = matcher.find();
        if (!matchFound) {
            throw new Exception("No usable roku");
        }

        return matcher.group(1);
    }

    private String getReceiveBody() throws Exception {
        String discoveryHost = "239.255.255.250";
        Integer discoveryPort = 1900;
        String discoveryAddress = discoveryHost + ":" + discoveryPort;

        DatagramSocket datagramSocket = new DatagramSocket();
        SocketAddress socketAddress = new InetSocketAddress(discoveryHost, discoveryPort);

        String discoveryMessage = "M-SEARCH * HTTP/1.1\r\n" +
                        "Host: " + discoveryAddress + "\r\n" +
                        "Man: \"ssdp:discover\"\r\n" +
                        "ST: roku:ecp\r\n\r\n";

        byte[] sendData = discoveryMessage.getBytes();
        byte[] receiveData = new byte[1024];

        DatagramPacket sendPacket = new DatagramPacket(sendData, 0, sendData.length, socketAddress);
        datagramSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        datagramSocket.receive(receivePacket);
        String receiveBody = new String(receivePacket.getData());
        datagramSocket.close();

        return receiveBody;
    }
}
