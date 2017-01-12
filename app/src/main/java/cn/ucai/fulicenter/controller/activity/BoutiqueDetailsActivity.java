package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.view.MFGT;

public class BoutiqueDetailsActivity extends AppCompatActivity {
    @BindView(R.id.ivBack)
    ImageView mIvBack;
    @BindView(R.id.tvBoutiqueTitle)
    TextView mTvBoutiqueTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_details);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container1, new NewGoodsFragment())
                .commit();
        String title = this.getIntent().getStringExtra(I.Boutique.NAME);
        mTvBoutiqueTitle.setText(title);
    }
    @OnClick(R.id.ivBack)
    public void onClick(){
        MFGT.finish(this);
    }
}
