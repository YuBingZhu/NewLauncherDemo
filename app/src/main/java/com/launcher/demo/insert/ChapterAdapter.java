package com.launcher.demo.insert;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.launcher.demo.R;

import java.util.ArrayList;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    public static final int VIEW_TYPE_CHAPTER = 1;
    public static final int VIEW_TYPE_SECTION = 2;

    private CourseInfo courseInfo;

    private List<BaseInfo> dataInfos = new ArrayList<>();
    private int curExpandChapterIndex = -1;

    public ChapterAdapter(CourseInfo _courseInfo) {
        this.courseInfo = _courseInfo;
        for (BaseInfo info : courseInfo.chapterInfos) {
            dataInfos.add(info);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == VIEW_TYPE_CHAPTER) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chapter, parent, false);
            return new ItemHolder(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_section, parent, false);
            return new ItemSectionHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == VIEW_TYPE_CHAPTER) {
            ItemHolder itemHolder = (ItemHolder) holder;
            itemHolder.itemView.setTag(position);
            itemHolder.tvPractise.setTag(position);

            ChapterInfo chapterInfo = (ChapterInfo) dataInfos.get(position);
            itemHolder.tvName.setText(chapterInfo.name);

            if (chapterInfo.sectionInfos.size() > 0) {
                itemHolder.ivArrow.setVisibility(View.VISIBLE);
                if (curExpandChapterIndex == position) {
                    itemHolder.ivArrow.setBackgroundResource(R.mipmap.ic_launcher_round);
                } else {
                    itemHolder.ivArrow.setBackgroundResource(R.mipmap.icon);
                }
            } else {
                itemHolder.ivArrow.setVisibility(View.INVISIBLE);
            }

        } else {
            ItemSectionHolder itemSectionHolder = (ItemSectionHolder) holder;
            itemSectionHolder.tvName.setTag(position);

            SectionInfo sectionInfo = (SectionInfo) dataInfos.get(position);
            itemSectionHolder.tvName.setText(sectionInfo.name);
        }
    }

    //该方法只更改itemView的部分信息，不全部刷新
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            String str = (String) payloads.get(0);
            //更改view的tag
            if (str.equals("change_position")) {
                if (getItemViewType(position) == VIEW_TYPE_CHAPTER) {
                    ItemHolder itemHolder = (ItemHolder) holder;
                    itemHolder.itemView.setTag(position);
                    itemHolder.tvPractise.setTag(position);
                    //改变箭头方向
                    if (curExpandChapterIndex == position) {
                        itemHolder.ivArrow.setBackgroundResource(R.mipmap.ic_launcher);
                    } else {
                        itemHolder.ivArrow.setBackgroundResource(R.mipmap.icon);
                    }
                } else {
                    ItemSectionHolder itemSectionHolder = (ItemSectionHolder) holder;
                    itemSectionHolder.tvName.setTag(position);
                }
            }
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        if (dataInfos == null) {
            return 0;
        } else {
            return dataInfos.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataInfos.get(position) instanceof ChapterInfo) {
            return VIEW_TYPE_CHAPTER;
        } else if (dataInfos.get(position) instanceof SectionInfo) {
            return VIEW_TYPE_SECTION;
        }
        return super.getItemViewType(position);
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public LinearLayout llBg;
        public ImageView ivArrow;
        public TextView tvName;
        public TextView tvPractise;
        public LinearLayout llSection;
        public GridView gvSection;

        public ItemHolder(View itemView) {
            super(itemView);
            ivArrow = (ImageView) itemView.findViewById(R.id.iv_item_chapter_arrow);
            tvName = (TextView) itemView.findViewById(R.id.tv_item_chapter_name);
            tvPractise = (TextView) itemView.findViewById(R.id.tv_item_chapter_practise);

            //将创建的View注册点击事件
            itemView.setOnClickListener(ChapterAdapter.this);
            tvPractise.setOnClickListener(ChapterAdapter.this);
        }
    }

    public class ItemSectionHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public ItemSectionHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_item_section_name);

            //将创建的View注册点击事件
            tvName.setOnClickListener(ChapterAdapter.this);
        }
    }


    ////////////////////////////以下为item点击处理///////////////////////////////

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public enum ViewName {
        CHAPTER_ITEM,
        CHAPTER_ITEM_PRACTISE,
        SECTION_ITEM
    }

    public interface OnRecyclerViewItemClickListener {
        void onClick(View view, ViewName viewName, int chapterIndex, int sectionIndex);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            int position = (int) v.getTag();
            ViewName viewName = ViewName.CHAPTER_ITEM;
            int chapterIndex = -1;
            int sectionIndex = -1;
            if (getItemViewType(position) == VIEW_TYPE_CHAPTER) {
                ChapterInfo chapterInfo = (ChapterInfo) dataInfos.get(position);
                chapterIndex = chapterInfo.chapterIndex;
                sectionIndex = -1;
                if (v.getId() == R.id.tv_item_chapter_practise) {
                    viewName = ViewName.CHAPTER_ITEM_PRACTISE;
                } else {
                    viewName = ViewName.CHAPTER_ITEM;
                    if (chapterInfo.sectionInfos.size() > 0) {
                        if (chapterIndex == curExpandChapterIndex) {
                            narrow(curExpandChapterIndex);
                        } else {
                            narrow(curExpandChapterIndex);
                            expand(chapterIndex);
                        }
                    }
                }
            } else if (getItemViewType(position) == VIEW_TYPE_SECTION) {
                SectionInfo sectionInfo = (SectionInfo) dataInfos.get(position);
                viewName = ViewName.SECTION_ITEM;
                chapterIndex = sectionInfo.chapterIndex;
                sectionIndex = sectionInfo.sectionIndex;
            }
            mOnItemClickListener.onClick(v, viewName, chapterIndex, sectionIndex);
        }
    }

    /**
     * 展开某个item
     *
     * @param chapterIndex
     */
    private void expand(int chapterIndex) {
        dataInfos.addAll(chapterIndex + 1, courseInfo.chapterInfos.get(chapterIndex).sectionInfos);
        curExpandChapterIndex = chapterIndex;
        notifyItemRangeInserted(chapterIndex + 1, courseInfo.chapterInfos.get(chapterIndex).sectionInfos.size());

        /*notifyItemRangeChanged(chapterIndex + 1 + courseInfo.chapterInfos.get(chapterIndex).sectionInfos.size(),
                getItemCount() - chapterIndex - 1, "change_position");*/
        notifyItemRangeChanged(0, getItemCount(), "change_position");
    }

    /**
     * 收起某个item
     *
     * @param chapterIndex
     */
    private void narrow(int chapterIndex) {
        if (chapterIndex != -1) {
            int removeStart = chapterIndex + 1;
            int removeCount = 0;
            for (int i = removeStart; i < dataInfos.size() && getItemViewType(i) == VIEW_TYPE_SECTION; i++) {
                removeCount++;
            }
            dataInfos.removeAll(courseInfo.chapterInfos.get(chapterIndex).sectionInfos);
            curExpandChapterIndex = -1;
            notifyItemRangeRemoved(removeStart, removeCount);
            //notifyItemRangeChanged(removeStart, getItemCount() - removeStart, "change_position");
            notifyItemRangeChanged(0, getItemCount(), "change_position");
        }
    }
}
