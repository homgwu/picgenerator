package com.homg.picgen;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by HomgWu on 2017/11/29.
 */

public abstract class GenerateModel {
    protected Context mContext;
    protected String mSavePath;
    protected ViewGroup mRootView;
    private GeneratePictureManager mGeneratePictureManager;

    public GenerateModel(ViewGroup rootView) {
        mContext = rootView.getContext();
        mRootView = rootView;
        mGeneratePictureManager = GeneratePictureManager.getInstance();
    }

    protected abstract void startPrepare(GeneratePictureManager.OnGenerateListener listener) throws Exception;

    public abstract View getView();

    protected void prepared(GeneratePictureManager.OnGenerateListener listener) {
        mGeneratePictureManager.prepared(this, listener);
    }

    public String getSavePath() {
        return mSavePath;
    }

    public void setSavePath(String savePath) {
        mSavePath = savePath;
    }
}
