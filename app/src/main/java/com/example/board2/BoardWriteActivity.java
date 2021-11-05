package com.example.board2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BoardWriteActivity extends AppCompatActivity {
    private EditText etTitle;
    private EditText etCtnt;
    private EditText etWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        // 한번만 실행해도 됨, 여기서 선언 oncreate
        etTitle = findViewById(R.id.etTitle);
        etCtnt = findViewById(R.id.etCtnt);
        etWriter = findViewById(R.id.etWriter);

    }

    public void clkSave(View v) {
        String title = etTitle.getText().toString();
        String ctnt = etCtnt.getText().toString();
        String writer = etWriter.getText().toString();

        BoardVO data = new BoardVO();
        data.setTitle(title);
        data.setCtnt(ctnt);
        data.setWriter(writer);

        BoardService service = Network.getService();
        Call<Map<String, Integer>> call = service.insBoard(data);
        call.enqueue(new Callback<Map<String, Integer>>() {
            @Override
            public void onResponse(Call<Map<String, Integer>> call, Response<Map<String, Integer>> response) {
                if(response.isSuccessful()) {
                    Map<String,Integer> map = response.body();
                    int result = map.get("result");

                    switch (result) {
                        case 1 :
                            finish();
                            break;
                        default:
                            //키보드 내리는 코드 추가
                            InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                            Snackbar.make(v, R.string.msg_fail, Snackbar.LENGTH_SHORT);
                            break;
                    }
                    Log.i("myLog", "result : " + result);
                }
            }


            @Override
            public void onFailure(Call<Map<String, Integer>> call, Throwable t) {
                Snackbar.make(v, R.string.msg_fail, Snackbar.LENGTH_SHORT);
            }
        });
    }

    public void clkCancle(View v) {
        finish();
    }
}