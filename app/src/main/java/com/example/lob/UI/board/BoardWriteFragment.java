package com.example.lob.UI.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lob.R;

public class BoardWriteFragment extends Fragment {
    private BoardWirteViewModel boardwriteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        boardwriteViewModel =
                ViewModelProviders.of(this).get(BoardWirteViewModel.class);
        View root = inflater.inflate(R.layout.boardwrite_fragment, container, false);
        final TextView textView = root.findViewById(R.id.board_title);
        boardwriteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
