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
            // WorkManager에서 전달받은 데이터 가져오기
            String postContent = getInputData().getString("postContent");
            String imageUri = getInputData().getString("imageUri");

            // 서버와 소켓 연결 및 데이터 전송
            Socket socket = new Socket("172.30.1.23", 9999); // 서버 IP와 포트 설정
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            if (postContent != null && imageUri != null) {
                String message = "POST " + postContent + " [이미지 URI: " + imageUri + "]";
                output.println(message); // 서버에 메시지 전송
            }

            socket.close(); // 소켓 연결 종료

            return Result.success(); // 성공적으로 작업 완료 시 반환
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(); // 작업 실패 시 반환
        }
    }
}