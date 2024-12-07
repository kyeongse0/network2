package com.kyeongseo.network;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.PrintWriter;
import java.net.Socket;

public class PostWorker extends Worker {

    public PostWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            String postTitle = getInputData().getString("postTitle");
            String postAuthor = getInputData().getString("postAuthor");
            String postContent = getInputData().getString("postContent");
            String imageUri = getInputData().getString("imageUri");

            Socket socket = new Socket("172.30.1.23", 9999);
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            String message = "POST " + postTitle + "::" + postAuthor + "::" + postContent;
            if (imageUri != null) {
                message += " [이미지 URI: " + imageUri + "]";
            }

            output.println(message);
            socket.close();
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }
}