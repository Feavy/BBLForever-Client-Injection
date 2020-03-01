package fr.feavy.bbl.proxy;

import java.io.ByteArrayInputStream;

public class ByteArray {

    public int[] bytes = {};


    public ByteArray() {
    }

    public ByteArray(int[] bytes) {
        this.bytes = bytes;
    }

    public int length() {

        return bytes.length;

    }

    public void writeByte(int b) {

        int[] newBytes = new int[bytes.length + 1];

        for (int i = 0; i < bytes.length; i++) {

            newBytes[i] = bytes[i];

        }

        newBytes[newBytes.length - 1] = b;
        bytes = newBytes;

    }

    public int get(int index) {

        return bytes[index];

    }

    public void writeBoolean(boolean b) {

        int[] newBytes = new int[bytes.length + 1];

        for (int i = 0; i < bytes.length; i++) {

            newBytes[i] = bytes[i];

        }

        newBytes[newBytes.length - 1] = (byte) ((b) ? 1 : 0);
        bytes = newBytes;

    }

}