package com.example.lob.UI.board;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lob.DTO.BoardDto;
import com.example.lob.R;
import com.example.lob.UserActivity;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BoardFragment extends Fragment {
    public static BoardFragment newInstance() {
        return new BoardFragment();
    }
    private final  String TAG = getClass().getSimpleName(); //나중에 지워도됨
    private final String BASE_URL = "http://34.64.192.192";
    private BoardAPI BAPI;

    private BoardViewModel boardViewModel;
    private ListView boardListView;
    private BoardListAdapter Adapter;
    private List<Board> boardList;
    private ScrollView scrollView;
    private boolean lastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수
    private int page = 0;                           // 페이징변수. 초기 값은 0 이다.
    private final int OFFSET = 5;                  // 한 페이지마다 로드할 데이터 갯수.
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수
    private ProgressBar progressBar;                // 데이터 로딩중을 표시할 프로그레스바

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        boardViewModel =
                ViewModelProviders.of(this).get(BoardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_board, container, false);



        boardListView = root.findViewById(R.id.boardListView);
        boardList = new ArrayList<>();
        scrollView = root.findViewById(R.id.scrollview_board);

        initBoardAPI(BASE_URL);
        try {
            Log.d(TAG,"GET");
            Call<List<BoardDto>> getCall = BAPI.get_posts();
            getCall.enqueue(new Callback<List<BoardDto>>() {
                @Override
                public void onResponse(Call<List<BoardDto>> call, Response<List<BoardDto>> response) {
                    if( response.isSuccessful()){
                        List<BoardDto> mList = response.body();
                        for( BoardDto item : mList){
                            String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault()).format(item.getBoard_date());
                            boardList.add(new Board(item.getBoard_title(),item.getBoard_writer(),date_text));
                            Adapter = new BoardListAdapter(getContext(),boardList);
                            //Adapter = new BoardListAdapter(getApplicationContext(),boardList);
                            boardListView.setAdapter(Adapter);
                        }
                    }else {
                        Log.d(TAG,"Status Code : " + response.code());
                        Log.i(TAG,"Status Code : " + response.code());
                        Log.v(TAG,"Status Code : " + response.code());
                        Log.e(TAG,"Status Code : " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<BoardDto>> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());
                }
            });
        }catch (Exception e){}


        boardListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        return root;
    }
    private void initBoardAPI(String baseUrl){
        Log.d(TAG,"initMyAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BAPI = retrofit.create(BoardAPI.class);
    }
}
