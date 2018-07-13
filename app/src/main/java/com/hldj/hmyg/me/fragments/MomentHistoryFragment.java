package com.hldj.hmyg.me.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.CallBack.IEditable;
import com.hldj.hmyg.CallBack.IFootMarkEmpty;
import com.hldj.hmyg.CallBack.impl.DeleteAbleImpl;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.bean.MomentsThumbUp;
import com.hldj.hmyg.Ui.friend.child.DetailActivity;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseRecycleViewFragment;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.decoration.SectionDecoration;
import com.hldj.hmyg.me.HistoryActivity;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.bean.enums.FootMarkSourceType;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.VideoHempler;
import com.hldj.hmyg.widget.MyCircleImageView;
import com.mabeijianxi.smallvideo2.VideoPlayerActivity2;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzy.common.widget.MeasureGridView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 *
 *
 */

public class MomentHistoryFragment extends BaseRecycleViewFragment<Moments> implements IFootMarkEmpty {


    @Override
    protected void onFragmentVisibleChange(boolean b) {
        super.onFragmentVisibleChange(b);
        if (b) {
            ((HistoryActivity) mActivity).toggleBottomParent(getiEditable().isEditable());
        }
    }

    @Override
    protected void doRefreshRecycle(String page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<Moments>>>>() {
        }.getType();

        new BasePresenter()
                .putParams(ConstantParams.pageIndex, page)
                .doRequest("admin/footmark/moment/list", new HandlerAjaxCallBackPage<Moments>(mActivity, type) {
                    @Override
                    public void onRealSuccess(List<Moments> e) {

//                        Collections.sort(e, new Comparator<Moments>() {
//
//                            @Override
//                            public int compare(Moments o1, Moments o2) {
//                                if (o1.attrData.dateStr.compareTo(o2.attrData.dateStr) == 0) {
//                                    return 0;
//                                } else if (o1.attrData.dateStr.compareTo(o2.attrData.dateStr) > 0) {
//                                    return -1;
//                                } else {
//                                    return 1;
//                                }
//                            }
//
//                        });

                        mCoreRecyclerView.getAdapter().addData(e);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mCoreRecyclerView.selfRefresh(false);
                    }
                });

    }

    @Override
    protected void onRecycleViewInited(CoreRecyclerView corecyclerView) {

        corecyclerView.getRecyclerView().addItemDecoration(
                SectionDecoration.Builder.init(new SectionDecoration.PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {


                        try {
                            // 19   suze = 20-1
                            if (position > mCoreRecyclerView.getAdapter().getData().size() - 1) {
                                return null;
                            }

                            if (mCoreRecyclerView.getAdapter().getData().size() == 0) {
                                return null;
                            } else {

                                Moments databean = (Moments) mCoreRecyclerView.getAdapter().getItem(position);
                                return databean.attrData.dateStr;
                            }


//                            if (mCoreRecyclerView.getAdapter().getData().size() == 0) {
//                                return null;
//                            } else {
//                                //                            dateStr
//                                BPageGsonBean.DatabeanX.Pagebean.Databean databean = (BPageGsonBean.DatabeanX.Pagebean.Databean) mCoreRecyclerView.getAdapter().getItem(position);
//                                return databean.attrData.dateStr;
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }


                    }

                    @Override
                    public View getGroupView(int position) {
//                        View view = LayoutInflater.from(HistoryActivity.this).inflate( R.layout.item_list_simple, null);
//                        view.setBackgroundColor(getColorByRes(R.color.gray_bg_ed));
//                        TextView tv = view.findViewById(android.R.id.text1);
//                        tv.setText(recycler.getAdapter().getItem(position) + "");
                        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_tag, null);
                        TextView textView = view.findViewById(R.id.text1);
                        textView.setHeight((int) getResources().getDimension(R.dimen.px74));
                        Moments databean = (Moments) mCoreRecyclerView.getAdapter().getItem(position);
                        textView.setText(databean.attrData.dateStr);

                        return view;
                    }
                }).setGroupHeight((int) getResources().getDimension(R.dimen.px74)).build());


    }

    @Override
    protected void doConvert(BaseViewHolder helper, Moments item, NeedSwipeBackActivity mActivity) {

//        D.i("=============doConvert==============" + item.getName());

        CheckBox checkBox = helper.getView(R.id.checkBox);
        if (checkBox != null) {
            helper.setChecked(R.id.checkBox, item.isChecked())
                    .setVisible(R.id.checkBox, getiEditable().isEditable())
                    .setVisible(R.id.checkBoxParent, getiEditable().isEditable())
                    .addOnClickListener(R.id.checkBoxParent, v -> {
                        item.toggle();
                        helper.setChecked(R.id.checkBox, item.isChecked());
                    })
                    .addOnClickListener(R.id.checkBox, v -> {
                        item.toggle();
                    });
        }


        if (item.attrData.isDelete) {
            helper.getView(R.id.root_view).setAlpha(0.5f);
            helper.setVisible(R.id.yxj, true);
        } else {
            helper.getView(R.id.root_view).setAlpha(1f);
            helper.setVisible(R.id.yxj, false);
        }

        View.OnClickListener clickListener = v ->
        {

            if (item.attrData.isDelete) {
//                        ToastUtil.showLongToast("删除了");
                return;
            }
//                  ToastUtil.showLongToast("点击文字--->跳转采购单详情界面");
            DetailActivity.start(mActivity, item.id);
        };
        helper.addOnClickListener(R.id.title, clickListener);// 发布名称或者标题
        helper.addOnClickListener(R.id.time_city, clickListener);//时间和  发布地址
        helper.addOnClickListener(R.id.descript, clickListener);//描述

        helper.setVisible(R.id.imageView7, false)
                .setVisible(R.id.tv_right_top, false)
                .addOnClickListener(R.id.tv_right_top, v ->
                        {


                            doUserDelDelete(helper, item, mActivity);

//                            new AlertDialog(mActivity).builder()
//                                    .setTitle("确定删除本项?")
//                                    .setPositiveButton("确定删除", v1 -> {
//                                        //                                    ToastUtil.showLongToast("删除");
//                                        doDelete(item, helper.getAdapterPosition());
//
//                                    }).setNegativeButton("取消", v2 -> {
//                            }).show();

                        }
                );
        helper.setText(R.id.descript, item.content);//描述
        String time_city = FUtil.$_zero(item.timeStampStr);
        if (item.ciCity != null && item.ciCity.fullName != null) {
            time_city += "   " + item.ciCity.fullName;
        }
        helper.setText(R.id.time_city, time_city);//时间戳_发布点


//                if (item.attrData != null && !TextUtils.isEmpty(item.ownerUserJson.headImage)) {
//                    //显示图片
//                    finalBitmap.display(helper.getView(R.id.head), item.ownerUserJson.headImage);
//                }

        helper.setVisible(R.id.imageView7, false);


        if (item.attrData != null) {
            helper.setText(R.id.title, item.attrData.displayName);
            //显示图片
//                    finalBitmap.display(helper.getView(R.id.head), item.attrData.headImage);
            MyCircleImageView circleImageView = helper.getView(R.id.head);
            if (!TextUtils.isEmpty(item.attrData.headImage)) {
                ImageLoader.getInstance().displayImage(item.attrData.headImage, circleImageView);
                ArrayList<Pic> arrayList = new ArrayList<>();
                arrayList.add(new Pic("0", false, item.attrData.headImage, 0));

                circleImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GalleryImageActivity.startGalleryImageActivity(mActivity, 0, arrayList);
                    }
                });

            }


//                    circleImageView.setImageURL(item.attrData.headImage);

        }



                  /* 视频  预览图片   */
        if (item.isVideo) {
            ImageView video = helper.getView(R.id.video);
            video.setVisibility(View.VISIBLE);
//                  video.setImageBitmap(VideoHempler.createVideoThumbnail(item.videoUrl, MyApplication.dp2px(mContext, 80), MyApplication.dp2px(mContext, 80)));

            D.e("============加载地址===========" + item.attrData.videoImageUrl);
            if (item.attrData != null && !TextUtils.isEmpty(item.attrData.videoImageUrl))
                ImageLoader.getInstance().displayImage(item.attrData.videoImageUrl, video);
            else {
                Observable.just(item.videoUrl)
                        .filter(test -> !TextUtils.isEmpty(item.videoUrl))
                        .subscribeOn(Schedulers.io())
                        .map(s -> VideoHempler.createVideoThumbnail(item.videoUrl, MyApplication.dp2px(mActivity, 80), MyApplication.dp2px(mActivity, 80)))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Bitmap>() {
                            @Override
                            public void accept(@NonNull Bitmap result) throws Exception {
                                video.setImageBitmap(result);
                            }
                        });
            }


//                ImageLoader.getInstance().displayImage(item.videoUrl, video);

            video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mActivity, VideoPlayerActivity2.class).putExtra(
                            "path", item.videoUrl));
                }
            });

        } else {
            ImageView video = helper.getView(R.id.video);
            video.setVisibility(View.GONE);
        }


        MeasureGridView gridView = helper.getView(R.id.imageView8);
        gridView.setImageNumColumns(3);
        gridView.setHorizontalSpacing(6);
        gridView.setVerticalSpacing(6);
        gridView.initFriend(mActivity, PurchaseDetailActivity.getPicList(item.imagesJson), (ViewGroup) gridView.getParent(), null);
        gridView.setOnViewImagesListener((mContext, pos, pics) -> {
            GalleryImageActivity.startGalleryImageActivity(
                    mContext, pos, PurchaseDetailActivity.getPicListOriginal(item.imagesJson));
        });
//                gridView.getAdapter().closeAll(true);
//                gridView.getAdapter().notifyDataSetChanged();


        helper.addOnClickListener(R.id.first, clickListener).setText(R.id.first, " " + item.thumbUpCount);//按钮一 点赞

        if (item.thumbUpListJson == null)
            item.thumbUpListJson = new ArrayList<MomentsThumbUp>();
        helper.addOnClickListener(R.id.second, clickListener).setText(R.id.second, " " + item.replyCount);//按钮2 评论


        helper.setVisible(R.id.botton_line, false);

    }

    @Override
    public int bindRecycleItemId() {
        return R.layout.item_friend_cicle_simple_center_d_activity;
    }

    @Override
    public void doEmpty() {
//        ToastUtil.showLongToast("清空  " + FootMarkSourceType.moments.getEnumText());

//        ToastUtil.showLongToast(this.getResourceId());
//        if (TextUtils.isEmpty(this.getResourceId())) {
//            ToastUtil.showLongToast("请选择删除项");
//            return;
//        }

        /* 判断是否为空 */
        if (null2Tip(this.getResourceId(), "请选择删除项")) return;

        doUserDelDelete(null, this, mActivity);
    }


//    private boolean isEditAble = false;


    /* core recycle view 代理 类 */
    private IEditable iEditable;

    public IEditable getiEditable() {
        if (iEditable == null) {
            iEditable = new DeleteAbleImpl(mCoreRecyclerView);
        }
        return iEditable;
    }


    @Override
    public void doEdit() {
        //编辑
//        this.getiEditable().isEditable() = !this.getiEditable().isEditable();
        getiEditable().toggleEditable();
        mCoreRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void toggleSelect() {
        getiEditable().toggleSelectAll();
    }

    @Override
    public String getEmptyTip() {
        return "确定删除所选？";
    }

    @Override
    public String getResourceId() {
        return getiEditable().getDeleteIds();
    }

    //BySource
    @Override
    public String getDomain() {
        return "admin/footmark/userDel";
    }

    @Override
    public FootMarkSourceType sourceType() {
        return FootMarkSourceType.moments;
    }


}
