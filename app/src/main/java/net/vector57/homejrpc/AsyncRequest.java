package net.vector57.homejrpc;

import android.os.AsyncTask;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.Socket;

/**
 * Created by Vector on 10/25/2015.
 */
public class AsyncRequest extends AsyncTask<AsyncRequestArgs, Void, JSONObject> {
    private JRPCResponseHandler handler;
    @Override
    protected JSONObject doInBackground(AsyncRequestArgs... params) {
        AsyncRequestArgs args = params[0];
        this.handler = args.handler;
        JSONObject response = null;
        try {
            String fuckShit = args.proxy.callSynchronous(args.request);
            Object fuckValue = JSONValue.parse(fuckShit);
            JSONObject fuckOff = (JSONObject) fuckValue;
            return fuckOff;
        } catch (Exception e) {
            JSONObject output = new JSONObject();
            JSONObject error = new JSONObject();
            error.put("message", e.getMessage());
            output.put("error", error);
            return output;
        }
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        if(response.containsKey("error")) {
            handler.handle(new Exception((String)((JSONObject)response.get("error")).get("message")), null);
        }
        handler.handle(null, response.get("result"));
    }
}
