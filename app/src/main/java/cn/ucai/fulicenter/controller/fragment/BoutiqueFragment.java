package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.net.IModelBoutique;
import cn.ucai.fulicenter.model.net.ModelBoutique;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {


    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.rv2)
    RecyclerView mRv2;
    @BindView(R.id.srl2)
    SwipeRefreshLayout mSrl2;

    public BoutiqueFragment() {
        // Required empty public constructor
    }
    static final int ACTION_DOWNLOAD=0;
    static final int ACTION_PULL_DOWN=1;
    LinearLayoutManager lm;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList=new ArrayList<>();
    IModelBoutique model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boutique, container, false);
        ButterKnife.bind(this, view);
        initView();
        model=new ModelBoutique();
        initData();
        setListener();
        return view;
    }
    private void setListener() {
        setPullDownListener();
    }
    private void setPullDownListener() {
        mSrl2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl2.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                downloadList(ACTION_PULL_DOWN);
            }
        });
    }
    private void initData() {
        downloadList(ACTION_DOWNLOAD);
    }

    private void downloadList(final int action) {
        model.downData(getContext(), new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                mAdapter.setMore(result!=null&&result.length>0);
                ArrayList<BoutiqueBean> list= ConvertUtils.array2List(result);
                switch (action){
                    case ACTION_DOWNLOAD:
                        mAdapter.initData(list);
                        break;
                    case ACTION_PULL_DOWN:
                        mSrl2.setRefreshing(false);
                        mTvRefresh.setVisibility(View.GONE);
                        mAdapter.initData(list);
                        break;
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(),error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initView(){
        mSrl2.setColorSchemeColors(
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_blue)
        );
        lm=new LinearLayoutManager(getContext());
        mRv2.addItemDecoration(new SpaceItemDecoration(15));
        mRv2.setLayoutManager(lm);
        mRv2.setHasFixedSize(true);
        mAdapter=new BoutiqueAdapter(getContext(),mList);
        mRv2.setAdapter(mAdapter);
    }

}
