package com.homg.picgenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.homg.picgen.GeneratePictureManager;

public class MainActivity extends AppCompatActivity {
    private Button mStartBtn;
    private ImageView mResultIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartBtn = findViewById(R.id.start_btn);
        mResultIv = findViewById(R.id.result_iv);
        mStartBtn.setOnClickListener(v -> {
            generate();
        });
    }

    private void generate() {
        SharePicModel sharePicModel = new SharePicModel((ViewGroup) getWindow().getDecorView());
        sharePicModel.setAvatarResId(R.mipmap.ic_launcher);
        GeneratePictureManager.getInstance().generate(sharePicModel, (throwable, bitmap) -> {
            if (throwable != null || bitmap == null) {
                Toast.makeText(this, getString(R.string.generate_pic_error), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.generate_pic_success), Toast.LENGTH_SHORT).show();
                mResultIv.setImageBitmap(bitmap);
            }
        });
    }
}
