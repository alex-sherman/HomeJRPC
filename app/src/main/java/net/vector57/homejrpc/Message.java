package net.vector57.homejrpc;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by Vector on 10/13/2015.
 */
public class Message {
    public JSONObject jobj;
    public byte[] bytes;
    public static Message Request(int id, String method, Object[] args)
    {
        JSONObject jobj = new JSONObject();
        jobj.put("jsonrpc", "2.0");
        jobj.put("id", id);
        jobj.put("method", method);
        JSONArray fuckOff = new JSONArray();
        fuckOff.addAll(Arrays.asList(args));
        JSONArray worstAPIEver = new JSONArray();
        worstAPIEver.add(fuckOff);
        worstAPIEver.add(new JSONObject());
        jobj.put("params", worstAPIEver);
        return new Message(jobj);
    }
    public Message(JSONObject jobj)
    {
        this.jobj = jobj;
        this.bytes = jobj.toString().getBytes();
    }
}
