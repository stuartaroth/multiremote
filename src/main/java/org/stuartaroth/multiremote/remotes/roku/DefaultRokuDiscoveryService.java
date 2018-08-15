package org.stuartaroth.multiremote.remotes.roku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultRokuDiscoveryService implements RokuDiscoveryService {
    private static Logger logger = LoggerFactory.getLogger(DefaultRokuDiscoveryService.class);

    private Pattern serialNumberRegex;
    private Pattern addressRegex;
    private String discoveryHost;
    private Integer discoveryPort;
    private String discoveryAddress;

    public DefaultRokuDiscoveryService() {
        serialNumberRegex = Pattern.compile("uuid:roku:ecp:([\\S]*)");
        addressRegex = Pattern.compile("LOCATION: (http[\\S]*)");
        discoveryHost = "239.255.255.250";
        discoveryPort = 1900;
        discoveryAddress = "239.255.255.250:1900";
    }

    @Override
    public List<RokuDevice> getRokuDevices() {
        List<RokuDevice> rokuDevices = new ArrayList<>();

        List<String> networkResponses = getNetworkResponses();

        logger.info("networkResponse.size(): {}", networkResponses.size());

        for (String networkResponse : networkResponses) {
            Matcher serialNumberMatcher = serialNumberRegex.matcher(networkResponse);
            boolean serialNumberFound = serialNumberMatcher.find();

            Matcher addressMatcher = addressRegex.matcher(networkResponse);
            boolean addressFound = addressMatcher.find();

            if (serialNumberFound && addressFound) {
                rokuDevices.add(new DefaultRokuDevice(serialNumberMatcher.group(1), addressMatcher.group(1)));
            }
        }

        return rokuDevices;
    }

    private List<String> getNetworkResponses() {
        List<String> networkResponses = new ArrayList<>();

        try {

            DatagramSocket datagramSocket = new DatagramSocket();
            SocketAddress socketAddress = new InetSocketAddress(discoveryHost, discoveryPort);

            datagramSocket.setSoTimeout(5000);

            String discoveryMessage = "M-SEARCH * HTTP/1.1\r\n" +
                    "Host: " + discoveryAddress + "\r\n" +
                    "Man: \"ssdp:discover\"\r\n" +
                    "ST: roku:ecp\r\n\r\n";

            byte[] sendData = discoveryMessage.getBytes();
            byte[] receiveData = new byte[1024];

            DatagramPacket sendPacket = new DatagramPacket(sendData, 0, sendData.length, socketAddress);
            datagramSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            for (int i = 0; i < 20; i++) {
                datagramSocket.receive(receivePacket);
                String networkResponse = new String(receivePacket.getData());
                networkResponses.add(networkResponse);
            }

            datagramSocket.close();

        } catch (Exception e) {
            logger.error("Exception: {}", e);
        }

        return networkResponses;
    }
}
