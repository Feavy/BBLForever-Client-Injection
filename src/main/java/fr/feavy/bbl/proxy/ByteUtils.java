package fr.feavy.bbl.proxy;

import java.util.List;

public class ByteUtils {

    public static int[] ByteListTobyteArray(List<Integer> in) {

        int[] bytes = new int[in.size()];
        int i = 0;
        for (int b : in.toArray(new Integer[in.size()])) {

            bytes[i] = b;
            i++;

        }

        return bytes;

    }

    public static int[] ByteArrayTobyteArray(int[] bytes){

        int[] response = new int[bytes.length];

        int i = 0;

        for(int b : bytes){

            response[i] = b;
            i++;

        }

        return response;

    }

    public static byte[] intArrayToByteArray(List<Integer> in){

        byte[] response = new byte[in.size()];
        int i = 0;
        for(int integer : in.toArray(new Integer[in.size()])){
            response[i] = (byte)integer;
            i++;
        }

        return response;

    }

}