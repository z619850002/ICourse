package com.example.dell.coursetable.presenter;

import com.example.dell.coursetable.model.RemarkInfoModel;
import com.example.dell.coursetable.view.CommentViewImpl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by 田雍恺 on 2018/2/12.
 */

public class CommentPresenter implements CommentPresenterImpl{

    CommentViewImpl cView;
    RemarkInfoModel rModel;

    public CommentPresenter(CommentViewImpl cView){
        this.cView = cView;
        rModel = new RemarkInfoModel();
    }
    @Override
    public void update(final String name, final String teacher) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    rModel.getCommentListByCourse(name, teacher);
                    show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void show() {
        cView.showData(rModel.getRemarkList());
    }
}
