package com.example.board2;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BoardService {

    @GET("list")
    Call<List<BoardVO>> selBoardList();

    @POST("ins")
    Call<Map<String, Integer>> insBoard(@Body BoardVO p);
    //값 넣을 때 쓰는 키 값, 값의 타입

    @GET("one")
    Call<BoardVO> selBoardDetail(@Query("iboard") int iboard);
}
