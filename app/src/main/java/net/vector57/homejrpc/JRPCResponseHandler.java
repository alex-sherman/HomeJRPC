package net.vector57.homejrpc;

/**
 * Created by Vector on 10/25/2015.
 */
public interface JRPCResponseHandler {
    void handle(Exception e, Object result);
}
