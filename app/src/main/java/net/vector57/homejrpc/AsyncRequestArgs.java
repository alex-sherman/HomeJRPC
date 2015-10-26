package net.vector57.homejrpc;

import java.net.Socket;
import java.util.concurrent.locks.Lock;

/**
 * Created by Vector on 10/25/2015.
 */
public class AsyncRequestArgs {
    public Message request;
    public JRPCResponseHandler handler;
    public SocketProxy proxy;
    public AsyncRequestArgs(SocketProxy proxy, JRPCResponseHandler handler, String name, Object... args) {
        request = Message.Request(0, name, args);
        this.handler = handler;
        this.proxy = proxy;
    }
}
