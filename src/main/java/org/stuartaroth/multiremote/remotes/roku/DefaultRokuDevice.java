package org.stuartaroth.multiremote.remotes.roku;

public class DefaultRokuDevice implements RokuDevice {
    private String serialNumber;
    private String address;

    public DefaultRokuDevice(String serialNumber, String address) {
        this.serialNumber = serialNumber;
        this.address = address;
    }

    @Override
    public String getSerialNumber() {
        return serialNumber;
    }

    @Override
    public String getAddress() {
        return address;
    }
}
