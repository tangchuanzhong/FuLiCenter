package cn.ucai.fulicenter.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.AlbumsBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelGoods;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelGoods;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.view.FlowIndicator;
import cn.ucai.fulicenter.view.MFGT;
import cn.ucai.fulicenter.view.SlideAutoLoopView;

public class GoodsDetailsActivity extends AppCompatActivity {
    int goodsId = 0;
    IModelGoods model;
    IModelUser userModel;
    @BindView(R.id.ivBack2)
    ImageView ivBack2;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.iv_good_share)
    ImageView ivGoodShare;
    @BindView(R.id.iv_good_cart)
    ImageView ivGoodCart;
    @BindView(R.id.tv_cart_count)
    TextView tvCartCount;
    @BindView(R.id.tv_good_name_english)
    TextView tvGoodNameEnglish;
    @BindView(R.id.tv_good_name)
    TextView tvGoodName;
    @BindView(R.id.tv_good_price_shop)
    TextView tvGoodPriceShop;
    @BindView(R.id.tv_good_price_current)
    TextView tvGoodPriceCurrent;
    @BindView(R.id.salv)
    SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    FlowIndicator indicator;
    @BindView(R.id.layout_image)
    RelativeLayout layoutImage;
    @BindView(R.id.wv_good_brief)
    WebView wvGoodBrief;
    @BindView(R.id.layout_banner)
    RelativeLayout layoutBanner;
    @BindView(R.id.activity_goods_details)
    RelativeLayout activityGoodsDetails;

    boolean isCollect;
    @BindView(R.id.iv_good_collect)
    ImageView ivGoodCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        if (goodsId == 0) {
            MFGT.finish(this);
        } else {
            initData();
        }
    }

    private void initData() {
        model = new ModelGoods();
        model.downData(this, goodsId, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    showGoodsDetails(result);
                } else {
                    MFGT.finish(GoodsDetailsActivity.this);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void showGoodsDetails(GoodsDetailsBean goods) {
        tvGoodName.setText(goods.getGoodsName());
        tvGoodNameEnglish.setText(goods.getGoodsEnglishName());
        tvGoodPriceCurrent.setText(goods.getCurrencyPrice());
        tvGoodPriceShop.setText(goods.getShopPrice());
        salv.startPlayLoop(indicator, getAlbumUrl(goods), getAlbumCount(goods));
        wvGoodBrief.loadDataWithBaseURL(null, goods.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
    }

    private int getAlbumCount(GoodsDetailsBean goods) {
        if (goods != null && goods.getProperties() != null && goods.getProperties().length > 0) {
            return goods.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumUrl(GoodsDetailsBean goods) {
        if (goods != null && goods.getProperties() != null && goods.getProperties().length > 0) {
            AlbumsBean[] albums = goods.getProperties()[0].getAlbums();
            if (albums != null && albums.length > 0) {
                String[] urls = new String[albums.length];
                for (int i = 0; i < albums.length; i++) {
                    urls[i] = albums[i].getImgUrl();
                }
                return urls;
            }
        }
        return new String[0];

    }


    @OnClick(R.id.backClickArea)
    public void onClick() {
        MFGT.finish(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCollectStatus();
    }

    private void initCollectStatus() {
        //ivGoodCollect.setEnabled(false);
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            model.isCollect(this, goodsId, user.getMuserName(), new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        isCollect = true;
                    } else {
                        isCollect = false;
                    }
                    setCollectStatus();
                }

                @Override
                public void onError(String error) {
                    isCollect = false;
                    setCollectStatus();
                }
            });
        }
    }

    private void setCollectStatus() {
        if (isCollect) {
            ivGoodCollect.setImageResource(R.mipmap.bg_collect_out);
        } else {
            ivGoodCollect.setImageResource(R.mipmap.bg_collect_in);
        }
    }

    @OnClick(R.id.iv_good_collect)
    public void setCollectListener() {
        //ivGoodCollect.setEnabled(false);
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            setCollect(user);
        } else {
            MFGT.gotoLogin(this);
        }
    }


    public void setCollect(User user) {
        model.setCollect(this, goodsId, user.getMuserName(),
                isCollect ? I.ACTION_DELETE_COLLECT : I.ACTION_ADD_COLLECT,
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollect = !isCollect;
                            setCollectStatus();
                            CommonUtils.showShortToast(result.getMsg());
                            sendBroadcast(new Intent("update_collect").
                                    putExtra(I.Collect.GOODS_ID, goodsId));
                        }
                    }

                    @Override
                    public void onError(String error) {
                        //ivGoodCollect.setEnabled(true);
                    }
                });
    }

    @OnClick(R.id.iv_good_cart)
    public void addCart() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            userModel = new ModelUser();
            userModel.updateCart(this, I.ACTION_CART_ADD, user.getMuserName()
                    , goodsId, 1, 0, new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result != null && result.isSuccess()) {
                                CommonUtils.showLongToast(R.string.add_goods_success);
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
        }else {
            MFGT.gotoLogin(this);
        }
    }
}
