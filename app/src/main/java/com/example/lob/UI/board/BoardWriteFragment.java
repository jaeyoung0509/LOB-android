package com.example.lob.UI.board;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardWriteFragment extends Fragment {
    private BoardWirteViewModel boardwriteViewModel;
    private final  String TAG = getClass().getSimpleName();
    private final String BASE_URL = "http://e15010c2514f.ngrok.io";
    private BoardAPI BAPI;
    public static BoardWriteFragment newInstance() {
        return new BoardWriteFragment();
    }

    private EditText bTitle;
    private EditText bContents;
    private Button insertButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        boardwriteViewModel =
                ViewModelProviders.of(this).get(BoardWirteViewModel.class);
        View root = inflater.inflate(R.layout.boardwrite_fragment, container, false);
        bTitle = root.findViewById(R.id.board_title);
        bContents = root.findViewById(R.id.board_content);
        insertButton = root.findViewById(R.id.board_insertButton);
        initBoardAPI(BASE_URL);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = bTitle.getText().toString();
                String contents = bContents.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
//            String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault()).format(currentTime);

                BoardDto item = new BoardDto();
                item.setBoard_title(title);
                item.setBoard_contents(contents);
                item.setBoard_writer(title);
                item.setBoard_date(currentTime);

                Call<BoardDto> postCall = BAPI.post_boards(item);
                postCall.enqueue(new Callback<BoardDto>() {
                    @Override
                    public void onResponse(Call<BoardDto> call, Response<BoardDto> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG,"등록 완료");
                            ((UserActivity)getActivity()).replaceFragment(BoardFragment.newInstance());

                        }else {
                            Log.d(TAG,"Status Code : " + response.code());
                            Log.d(TAG,response.errorBody().toString());
                            Log.d(TAG,call.request().body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<BoardDto> call, Throwable t) {
                        Log.d(TAG,"Fail msg : " + t.getMessage());
                    }
                });
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
