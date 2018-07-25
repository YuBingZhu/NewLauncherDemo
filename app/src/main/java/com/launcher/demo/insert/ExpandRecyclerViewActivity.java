package com.launcher.demo.insert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.launcher.demo.R;

public class ExpandRecyclerViewActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    CourseInfo mCourseInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_overview);

        initData();
        initViews();

    }

    private void initData() {
        //假数据
        mCourseInfo = new CourseInfo();
        mCourseInfo.name = "假装是课程的名称";
        for (int i = 0; i < 31; i++) {
            ChapterInfo chapterInfo = new ChapterInfo();
            chapterInfo.name = "假装是课时名称" + (i + 1);
            chapterInfo.chapterIndex = i;
            if (i == 0) {
                for (int j = 0; j < 2; j++) {
                    SectionInfo sectionInfo = new SectionInfo();
                    sectionInfo.name = "第" + (j + 1) + "节";
                    sectionInfo.chapterIndex = i;
                    sectionInfo.sectionIndex = j;
                    chapterInfo.sectionInfos.add(sectionInfo);
                }
            } else if (i == 1) {
                for (int j = 0; j < 3; j++) {
                    SectionInfo sectionInfo = new SectionInfo();
                    sectionInfo.name = "第" + (j + 1) + "节";
                    sectionInfo.chapterIndex = i;
                    sectionInfo.sectionIndex = j;
                    chapterInfo.sectionInfos.add(sectionInfo);
                }
            } else if (i == 2) {
            } else {
                for (int j = 0; j < 4; j++) {
                    SectionInfo sectionInfo = new SectionInfo();
                    sectionInfo.name = "第" + (j + 1) + "节";
                    sectionInfo.chapterIndex = i;
                    sectionInfo.sectionIndex = j;
                    chapterInfo.sectionInfos.add(sectionInfo);
                }
            }
            mCourseInfo.chapterInfos.add(chapterInfo);
        }
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ChapterAdapter chapterAdapter = new ChapterAdapter(mCourseInfo);
        mRecyclerView.setAdapter(chapterAdapter);
        chapterAdapter.setOnItemClickListener(new ChapterAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, ChapterAdapter.ViewName viewName,
                                int chapterIndex, int sectionIndex) {
                //Timber.v("---onClick---"+viewName+", "+chapterIndex+", "+sectionIndex);
                switch (viewName) {
                    case CHAPTER_ITEM:
                        if (mCourseInfo.chapterInfos.get(chapterIndex).sectionInfos.size() > 0) {
                            if (chapterIndex + 1 == mCourseInfo.chapterInfos.size()) {
                                //如果是最后一个，则滚动到展开的最后一个item
                                mRecyclerView.smoothScrollToPosition(chapterAdapter.getItemCount());
                            }
                        } else {
                            onClickChapter(chapterIndex);
                        }
                        break;
                    case CHAPTER_ITEM_PRACTISE:
                        onClickPractise(chapterIndex);
                        break;
                    case SECTION_ITEM:
                        onClickSection(chapterIndex, sectionIndex);
                        break;
                }
            }
        });

        //以下是对布局进行控制，让课时占据一行，小节每四个占据一行，结果就是相当于一个ListView嵌套GridView的效果。
        final GridLayoutManager manager = new GridLayoutManager(this, 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return chapterAdapter.getItemViewType(position) == ChapterAdapter.VIEW_TYPE_CHAPTER ? 4 : 1;
            }
        });
        mRecyclerView.setLayoutManager(manager);
    }

    private void onClickChapter(int chapterIndex) {
        Toast.makeText(ExpandRecyclerViewActivity.this, "播放" + chapterIndex
                , Toast.LENGTH_SHORT).show();
    }

    private void onClickSection(int chapterIndex, int sectionIndex) {
        Toast.makeText(ExpandRecyclerViewActivity.this, "播放" + chapterIndex
                + ", " + sectionIndex, Toast.LENGTH_SHORT).show();

    }

    private void onClickPractise(int chapterIndex) {
        Toast.makeText(ExpandRecyclerViewActivity.this, "onClick " + chapterIndex
                , Toast.LENGTH_SHORT).show();

    }

}