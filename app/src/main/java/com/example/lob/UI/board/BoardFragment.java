package com.example.lob.UI.board;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.lob.R;
import com.example.lob.UserActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class BoardFragment extends Fragment {
    public static BoardFragment newInstance() {
        return new BoardFragment();
    }
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

        boardList.add(new Board("공지사항입니다.","배현지","200=05-20"));
        boardList.add(new Board("공지사항입니다.","배현지","200=sdafs-20"));
        boardList.add(new Board("공지사항입니다.","asdafd","200=05-20"));
        boardList.add(new Board("공지사항입니다.","배현지","200=05-20"));
        boardList.add(new Board("공지사항입니다.","배현지","200=05-20"));
        boardList.add(new Board("공지사항입니다.","배현지","200=05-20"));
        boardList.add(new Board("공지사항입니다.","배현지","200=05-20"));
        boardList.add(new Board("공지사항입니다.","배현지","200=05-20"));
        boardList.add(new Board("공지사항입니다.","배현지","200=05-20"));
        boardList.add(new Board("공지사항입니다.","배현지","200=05-20"));
        boardList.add(new Board("공지사항입니다.","배현지","200=05-20"));
        Adapter = new BoardListAdapter(getContext(),boardList);
       // Adapter = new BoardListAdapter(getApplicationContext(),boardList);
        boardListView.setAdapter(Adapter);


        boardListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        return root;
    }
}
