package com.homg.picgenerator;

import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.homg.picgen.GenerateModel;
import com.homg.picgen.GeneratePictureManager;


/**
 * Created by HomgWu on 2017/11/29.
 */

public class SharePicModel extends GenerateModel {
    private ImageView mTitleAvatarIv;
    private View mSharePicView;
    private int mAvatarResId;

    public SharePicModel(ViewGroup rootView) {
        super(rootView);
    }

    @Override
    protected void startPrepare(GeneratePictureManager.OnGenerateListener listener) throws Exception {
        mSharePicView = LayoutInflater.from(mContext).inflate(R.layout.layout_share_pic_model, mRootView, false);
        mTitleAvatarIv = mSharePicView.findViewById(R.id.invitation_share_link_pic_avatar_iv);
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(mContext.getResources(), BitmapFactory.decodeResource(mContext.getResources(), mAvatarResId));
        circularBitmapDrawable.setCircular(true);
        mTitleAvatarIv.setImageDrawable(circularBitmapDrawable);
        prepared(listener);
    }

    @Override
    public View getView() {
        return mSharePicView;
    }

    public void setAvatarResId(int avatarResId) {
        mAvatarResId = avatarResId;
    }
}
