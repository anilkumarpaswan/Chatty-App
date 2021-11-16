package com.chatty.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Akhil on 18-May-2018.
 */

public class ChatService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ChatService(String name) {
        super(name);
    }
    public ChatService() {
        super("");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        System.out.println("ChatService.onHandleIntent performing task");
    }


}
