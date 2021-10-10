package com.example.demo_v4_qxdmlog;


import android.util.Log;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;

/**
 * @hide
 *
 * Logd2 Java interface.
 */
public class Executor extends Observable {

    private static final String TAG = "LogdJ";

    public static final String TERMINATE_BY_REMOTE = "terMiNaTeByReMote";

    private SocketClient mSktClient = null;
    private volatile boolean mStopByLocal = false;

    public Executor() {
    }

    // execute shell command and return with socket connected. (Closed by UI)
    public boolean shellAsync(String shellCmd) {
        boolean ret = true;
        mSktClient = new SocketClient();
        try {
            mSktClient.connect();
            mSktClient.sendMessageToServer(shellCmd);
            new SocketMonitor().start();
        } catch (UnknownHostException uhe) {
            // connect fail.
            Log.w(TAG, "leave shellSync with UnknownHostException");
            stop();
            ret = false;
        } catch (IOException ie) {
            // done.
            Log.w(TAG, "leave shellSync with IOException, " + ie.toString());
            stop(false);
            ret = true;
        } catch (Exception e) {
            // exception.
            Log.w(TAG, "leave shellSync with Exception");
            stop();
            ret = false;
        }

        return ret;
    }

    // execute shell command and return with socket connected. (Closed by UI)
    public boolean oemAsync(String shellCmd) {
        boolean ret = true;
        mSktClient = new SocketClient();
        String serverMessage = "";
        try {
            mSktClient.connect(0);
            mSktClient.sendMessageToServer(shellCmd);
            serverMessage = mSktClient.getServerMessage();
            Log.i(TAG, "[Server] said: " + serverMessage);
            setChanged();
            notifyObservers(serverMessage);
            new SocketMonitor().start();
        } catch (UnknownHostException uhe) {
            // connect fail.
            Log.w(TAG, "leave oemAsync with UnknownHostException");
            stop();
            ret = false;
        } catch (IOException ie) {
            // done.
            Log.w(TAG, "leave oemAsync with IOException, " + ie.toString());
            stop(false);
            ret = true;
        } catch (Exception e) {
            // exception.
            Log.w(TAG, "leave oemAsync with Exception");
            stop();
            ret = false;
        }

        return ret;
    }

    // execute shell command and return when socket closed. (Closed by logd2)
    public boolean shellSync(String shellCmd) {
        return shellSync(shellCmd, SocketClient.SOCKET_TIMEOUT);
    }

    public boolean shellSync(String shellCmd, int timeout) {
        Log.w(TAG, "enter shellSync, timeout:" + timeout);
        boolean ret = true;
        mSktClient = new SocketClient();
        try {
            mSktClient.connect(timeout);
            mSktClient.sendMessageToServer(shellCmd);

            String serverMessage;
            while ((serverMessage = mSktClient.getServerMessage()) != null) {
                Log.i(TAG, "[Server] said: " + serverMessage);
                setChanged();
                notifyObservers(serverMessage);
                // break;
            }
        } catch (UnknownHostException uhe) {
            // connect fail.
            Log.w(TAG, "leave shellSync with UnknownHostException");
            ret = false;
        } catch (IOException ie) {
            // done.
            Log.w(TAG, "leave shellSync with IOException, " + ie.toString());
            ret = true;
        } catch (Exception e) {
            // exception.
            Log.w(TAG, "leave shellSync with Exception");
            e.printStackTrace();
            ret = false;
        }
        stop();
        return ret;
    }

    public void requestStop() {
        Log.d(TAG,"requestStop");
        mSktClient.sendMessageToServer("terminate");
    }

    public void stop() {
        stop(true);
    }

    public void stop(boolean byLocal) {
        mStopByLocal = byLocal;
        if (mSktClient != null) {
            mSktClient.close();
            mSktClient = null;
        }
    }

    public class SocketMonitor extends Thread {
        public SocketMonitor() {
        }

        @Override
        public void run() {
            try {
                if (mSktClient != null) {
                    int serverMessage = 0;
                    do {
                        try {
                            serverMessage = mSktClient.read();
                            Log.d(TAG,"SocketMonitor serverMessage: "+serverMessage);
                            // Log.d(TAG,
                            // "[SocketMonitor] getServerMessage() serverMessage:\""+serverMessage+"\"");
                        } catch (UnknownHostException uhe) {
                            // connect fail.
                            Log.w(TAG, "[SocketMonitor] Connection failed.", uhe);
                            break;
                        } catch (IOException ie) {
                            // done.
                            if (!"Try again".equals(ie.getMessage())) {
                                Log.w(TAG, "[SocketMonitor] Done.", ie);
                                break;
                            }
                        }
                    } while (serverMessage != -1);
                    if (!mStopByLocal) {
                        Log.d(TAG,"notifyObservers TERMINATE_BY_REMOTE");
                        setChanged();
                        notifyObservers(TERMINATE_BY_REMOTE);
                    }
                    // Log.d(TAG, "[SocketMonitor] getServerMessage() END");
                }
            } catch (Exception e) {
                // exception.
                Log.w(TAG, "[SocketMonitor] Exception.", e);
                Executor.this.stop();
            }
        }
    }
}
