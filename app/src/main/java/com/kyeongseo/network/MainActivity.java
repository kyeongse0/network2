package com.kyeongseo.network;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnBoard = findViewById(R.id.btnBoard);
        Button btnChatList = findViewById(R.id.btnChatList);

        // 기본 화면은 게시판 Fragment
        loadFragment(new BoardFragment());

        btnBoard.setOnClickListener(v -> loadFragment(new BoardFragment()));
        btnChatList.setOnClickListener(v -> loadFragment(new ChatListFragment()));
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}