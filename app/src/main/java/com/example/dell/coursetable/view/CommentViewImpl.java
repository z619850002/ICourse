package com.example.dell.coursetable.view;

import com.example.dell.coursetable.gson.Remark;

import java.util.List;

/**
 * Created by 田雍恺 on 2018/2/12.
 */

public interface CommentViewImpl {
    public void showData(List<Remark> remarkList);
    public void showError();
}
