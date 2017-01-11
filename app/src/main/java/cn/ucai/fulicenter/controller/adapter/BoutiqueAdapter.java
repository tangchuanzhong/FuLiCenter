package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

public class BoutiqueAdapter extends RecyclerView.Adapter {
    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_BOUTIQUE = 1;
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    String footer;

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
        notifyDataSetChanged();
    }

    boolean isMore;
    boolean isDragging;

    public boolean isDragging() {
        return isDragging;
    }

    public void setDragging(boolean dragging) {
        isDragging = dragging;
        notifyDataSetChanged();
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BOUTIQUE) {
            RecyclerView.ViewHolder holder =
                    new BoutiqueViewHolder(View.inflate(mContext, R.layout.item_boutique, null));
            return holder;
        } else {
            RecyclerView.ViewHolder holder =
                    new FootsViewHolder(View.inflate(mContext, R.layout.item_footer, null));
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder ParentHolder, int position) {
        if (getItemViewType(position)==TYPE_FOOTER){
            FootsViewHolder vh= (FootsViewHolder) ParentHolder;
            vh.mTvfooter.setText(getFooter());
            return;
        }

        BoutiqueViewHolder vh= (BoutiqueViewHolder) ParentHolder;
        ImageLoader.downloadImg(mContext,vh.mIvBoutiqueImg,mList.get(position).getImageurl());
        vh.mTvBoutiqueName.setText(mList.get(position).getName());
        vh.mTvBoutiqueTitle.setText(mList.get(position).getTitle());
        vh.mTvBoutiqueDescription.setText(mList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1){
            return TYPE_FOOTER;
        }
        return TYPE_BOUTIQUE;
    }
    public void initData(ArrayList<BoutiqueBean> list){
        if (mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }
    public void addData(ArrayList<BoutiqueBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    static class BoutiqueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivBoutiqueImg)
        ImageView mIvBoutiqueImg;
        @BindView(R.id.tvBoutiqueTitle)
        TextView mTvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView mTvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView mTvBoutiqueDescription;
        @BindView(R.id.layout_boutique_item)
        RelativeLayout layoutBoutiqueItem;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FootsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvfooter)
        TextView mTvfooter;
        @BindView(R.id.layout_footer)
        RelativeLayout layoutFooter;

        FootsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
