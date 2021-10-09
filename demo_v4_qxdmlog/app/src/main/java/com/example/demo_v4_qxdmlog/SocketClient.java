package com.example.demo_v4_qxdmlog;

import android.net.LocalSocketAddress;
import android.net.LocalSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;



/**
 * @hide
 *
 * The socket connects to logd2
 */
public class SocketClient {

    private static final String LOGD2_SOCKET = "/dev/socket/logd2";

    // default socket connection timeout
    public static final int SOCKET_TIMEOUT = 1000;

    private BufferedReader mServerReader = null;
    private PrintStream mServerWriter = null;
    private LocalSocket mSkt = null;

    public SocketClient() {
    }

    public void connect() throws UnknownHostException, IOException {
        connect(SOCKET_TIMEOUT);
    }

    public void connect(int timeout) throws UnknownHostException, IOException {
        mSkt = new LocalSocket();
        LocalSocketAddress address = new LocalSocketAddress(LOGD2_SOCKET, LocalSocketAddress.Namespace.FILESYSTEM);

        mSkt.connect(address);

        if (timeout != 0) {
            mSkt.setSoTimeout(timeout);
        }

        mServerReader = new BufferedReader(new InputStreamReader(mSkt.getInputStream()));
        mServerWriter = new PrintStream(mSkt.getOutputStream());
    }

    public void close() {
        try {
            if (mSkt != null) {
                mSkt.close();
                mSkt = null;
            }
            mServerReader = null;
            mServerWriter = null;
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public String getServerMessage() throws IOException {
        return mServerReader.readLine();
    }

    public int read() throws IOException {
        return mServerReader.read();
    }

    public void sendMessageToServer(String message) {
        mServerWriter.println(message);
    }

}
