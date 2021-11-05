package com.example.board2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardDetailActivity extends AppCompatActivity {
    private TextView tvIboard;
    private TextView tvTitle;
    private TextView tvCtnt;
    private TextView tvWriter;
    private TextView tvRdt;
    private int iboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        tvIboard = findViewById(R.id.tvIboard);
        tvTitle = findViewById(R.id.tvTitle);
        tvCtnt = findViewById(R.id.tvCtnt);
        tvWriter = findViewById(R.id.tvWriter);
        tvRdt = findViewById(R.id.tvRdt);

        Intent intent = getIntent();
        iboard = intent.getIntExtra("iboard",0);
    }

    public void onStart(){
        super.onStart();
        getBoardDetail();
    }

    private void getBoardDetail() {
        Call<BoardVO> call = Network.getService().selBoardDetail(iboard);
        call.enqueue(new Callback<BoardVO>() {
            @Override
            public void onResponse(Call<BoardVO> call, Response<BoardVO> res) {
                if(res.isSuccessful()) {
                    BoardVO vo = res.body();
                    tvTitle.setText(vo.getTitle());
                    tvCtnt.setText(vo.getCtnt());
                    tvWriter.setText(vo.getWriter());
                    tvRdt.setText(vo.getRdt());
                } else {
                    Log.e("myLog","통신 실패");
                }
            }

            @Override
            public void onFailure(Call<BoardVO> call, Throwable t) {
                Log.e("myLog","통신 완전 실패");
            }
        });

    }
}