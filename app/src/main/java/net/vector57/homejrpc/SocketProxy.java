package net.vector57.homejrpc;

import android.os.AsyncTask;

import net.vector57.homejrpc.Message;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SocketProxy {
    private int port;
    private String host;
    private int timeout;
    private Lock lock;
    private Socket socket;

    public SocketProxy(int port) {
        this(port, "localhost", 1);
    }

    public SocketProxy(int port, String host) {
        this(port, host, 1);
    }

    public SocketProxy(int port, String host, int timeout) {
        this.port = port;
        this.host = host;
        this.timeout = timeout;
        lock = new ReentrantLock();
    }

    public void call(JRPCResponseHandler handler, String name, Object... args) {
        AsyncRequestArgs requestArgs = new AsyncRequestArgs(this, handler, name, args);
        AsyncRequest task = new AsyncRequest();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, requestArgs);
    }


    public String callSynchronous(Message message) throws IOException {
        lock.lock();
        if(this.socket == null)
            socket = new Socket(host, port);
        if(!this.socket.isConnected())
            socket.connect(new InetSocketAddress(host, port));
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        byte[] bytes = message.bytes;
        output.write(ByteBuffer.allocate(4).putInt(bytes.length)
                .order(ByteOrder.nativeOrder()).array());
        output.write(bytes);
        output.flush();
        bytes = new byte[4];
        input.read(bytes, 0, 4);
        int recLength = ByteBuffer.wrap(bytes).getInt();
        bytes = new byte[recLength];
        input.read(bytes, 0, recLength);
        lock.unlock();
        return new String(bytes);
    }
}
