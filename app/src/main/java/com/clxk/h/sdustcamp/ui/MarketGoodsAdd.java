package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.MarketGoods;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.operator.BmobOperatorMarketGoods;
import com.clxk.h.sdustcamp.utils.PathGet;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MarketGoodsAdd extends AppCompatActivity {

    private TextView tv_addGoodsTitle;
    private Button btn_modifyAdd;
    private ImageView iv_addGoods1;
    private ImageView iv_addGoods2;
    private ImageView iv_addGoods3;
    private MarketGoods marketGoods;
    private EditText et_goodsName;
    private EditText et_goodsPrice;
    private EditText et_goodsDetail;
    private BmobOperatorMarketGoods bmobOperatorMarketGoods;
    private MarketGoods mg_cur;
    private String URLPATH1;
    private String URLPATH2;
    private String URLPATH3;
    private String OBJECTID;
    private int ImageId = 1;
    private User user;
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_add_goods);

        //find
        tv_addGoodsTitle = findViewById(R.id.tv_addGoodsTitle);
        btn_modifyAdd = findViewById(R.id.btn_modifyAdd);
        iv_addGoods1 = findViewById(R.id.iv_addGoods1);
        iv_addGoods2 = findViewById(R.id.iv_addGoods2);
        iv_addGoods3 = findViewById(R.id.iv_addGoods3);
        et_goodsDetail = findViewById(R.id.et_goodDetail);
        et_goodsName = findViewById(R.id.et_goodsName);
        et_goodsPrice = findViewById(R.id.et_goodsPrice);
        bmobOperatorMarketGoods = new BmobOperatorMarketGoods();
        marketGoods = new MarketGoods();
        user = BmobUser.getCurrentUser(User.class);
        //setListener
        tv_addGoodsTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getX() <= tv_addGoodsTitle.getCompoundDrawables()[0].getBounds().width() * 4) {
                    Intent intent = new Intent(MarketGoodsAdd.this, MarketMineGoods.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
        btn_modifyAdd.setOnClickListener(new ButtonClickListener());
        iv_addGoods1.setOnClickListener(new ButtonClickListener());
        iv_addGoods2.setOnClickListener(new ButtonClickListener());
        iv_addGoods3.setOnClickListener(new ButtonClickListener());
        //drawable
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.arrowleft);
        drawable.setBounds(0, 0, 60, 60);
        tv_addGoodsTitle.setCompoundDrawables(drawable, null, null, null);
    }

    class ButtonClickListener implements View.OnClickListener {

        Intent intent = null;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_addGoods1:
                    ImageId = 1;
                    intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 1);
                    break;
                case R.id.iv_addGoods2:
                    ImageId = 2;
                    intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 1);
                    break;
                case R.id.iv_addGoods3:
                    ImageId = 3;
                    intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 1);
                    break;
                case R.id.btn_modifyAdd:
                    marketGoods.setUserId(BmobUser.getCurrentUser(User.class).getMobilePhoneNumber());
                    marketGoods.setgName(et_goodsName.getText().toString().trim());
                    marketGoods.setgPrice(Integer.valueOf(et_goodsPrice.getText().toString().trim()));
                    marketGoods.setgDetail(et_goodsDetail.getText().toString().trim());
                    marketGoods.setgStatue(1);
                    if (marketGoods.getgName() == null) {
                        Toast.makeText(MarketGoodsAdd.this, "商品名不能为空", Toast.LENGTH_SHORT).show();
                        break;
                    } else if (marketGoods.getgPrice() < 0) {
                        Toast.makeText(MarketGoodsAdd.this, "商品价格不能为空", Toast.LENGTH_SHORT).show();
                        break;
                    } else if (marketGoods.getgDetail() == null || marketGoods.getgDetail().length() <= 5) {
                        Toast.makeText(MarketGoodsAdd.this, "商品描述不能少于5个字", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    String imagepath[] = new String[4];
                    index = 0;
                    if (URLPATH1 != null) imagepath[index++] = URLPATH1;
                    if (URLPATH2 != null) imagepath[index++] = URLPATH2;
                    if (URLPATH3 != null) imagepath[index++] = URLPATH3;
                    BmobFile.uploadBatch(imagepath, new UploadBatchListener() {
                        @Override
                        public void onSuccess(List<BmobFile> list, List<String> list1) {
                            if (list1.size() == index) {
                                for (String s : list1) {
                                    if (s.equals(list1.get(0))) {
                                        marketGoods.setgImage1(s);
                                    } else if (s.equals(list1.get(1))) {
                                        marketGoods.setgImage2(s);
                                    } else if (s.equals(list1.get(2))) {
                                        marketGoods.setgImage3(s);
                                    }
                                }
                                bmobOperatorMarketGoods.add(MarketGoodsAdd.this, marketGoods);
                            }
                        }

                        @Override
                        public void onProgress(int i, int i1, int i2, int i3) {

                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                    Toast.makeText(MarketGoodsAdd.this, "添加成功！", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MarketGoodsAdd.this, MarketMineGoods.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());
                    if (ImageId == 1) {
                        URLPATH1 = PathGet.getPath(MarketGoodsAdd.this, data.getData());
                        iv_addGoods1.setImageURI(data.getData());
                    } else if (ImageId == 2) {
                        URLPATH2 = PathGet.getPath(MarketGoodsAdd.this, data.getData());
                        iv_addGoods2.setImageURI(data.getData());
                    } else {
                        URLPATH3 = PathGet.getPath(MarketGoodsAdd.this, data.getData());
                        iv_addGoods3.setImageURI(data.getData());
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "false");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("result-data", true);
        startActivityForResult(intent, 3);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(MarketGoodsAdd.this, MarketMineGoods.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
