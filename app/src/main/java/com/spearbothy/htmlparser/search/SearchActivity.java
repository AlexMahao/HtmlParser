package com.spearbothy.htmlparser.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.spearbothy.htmlparser.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex_MaHao on 2017/7/17.
 */
public class SearchActivity extends AppCompatActivity implements TextWatcher {

    private EditText mSearchEt;
    private RecyclerView mRecycler;
    private List<String> mData = new ArrayList<>();
    private List<String> mFilterData = new ArrayList<>();
    private MyAdapter mAdapter;
    private List<HashMap<Integer, Integer>> mFilterColorIndexList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        initData();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(manager);
        mAdapter = new MyAdapter();
        mRecycler.setAdapter(mAdapter);

        mSearchEt = (EditText) findViewById(R.id.search_et);
        mSearchEt.addTextChangedListener(this);
    }

    /**
     * 过滤方法
     * @param str 输入的字符串
     */
    private void filter(String str) {
        mFilterData.clear();
        mFilterColorIndexList.clear();
        if (TextUtils.isEmpty(str)) {
            mFilterData.addAll(mData);
            mAdapter.notifyDataSetChanged();
            return;
        }
        for (String search : mData) {
            if (isConformSplitFilter(str, search, mFilterColorIndexList)) {
                mFilterData.add(search);
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    /**
     * 是否符合匹配,对于
     *
     * @param filter 过滤条件
     * @param source 原始数据
     * @param filterColorIndexList 保存匹配索引的集合
     * @return true表示符合过滤条件
     */
    public static boolean isConformSplitFilter(String filter, String source, List<HashMap<Integer, Integer>> filterColorIndexList) {
        // 分割字符串
        char[] ss = filter.toLowerCase().replaceAll("\\s+", "").toCharArray();
        source = source.toLowerCase();
        int[] colorIndex = new int[ss.length];
        // 标志位
        boolean find = true;
        int count = 0;
        // 循环过滤条件
        for (int i = 0; i < ss.length; i++) {
            char c = ss[i];
            // 查找是否有匹配字符
            int index = source.indexOf(c);
            if (index >= 0) {
                // 如果匹配上了，保存索引，并截取，然后继续循环
                count += index;
                colorIndex[i] = count;
                source = source.substring(index + 1, source.length());
                count++;
            } else {
                find = false;
                break;
            }
        }
        // 如果查找到，切索引集合不为空，则保存索引到集合中
        if (find && filterColorIndexList != null) {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int index : colorIndex) {
                map.put(index, index + 1);
            }
            filterColorIndexList.add(map);
        }
        return find;
    }

    public void initData() {
        mData.add("dasdsa3123dsalkjpiincz");
        mData.add("czxndoqiewnzxczouie2");
        mData.add("dasne2mdsakljdnxczhgdsa");
        mData.add("daskjhewqbmcxzugudwehjc");
        mData.add("ggyiqbckxzjhueqwwbnmczxhiuhda");
        mData.add("das8nc8unzoijeqwbchuz");
        mFilterData.addAll(mData);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // 搜索匹配的关键方法
        filter(editable.toString());
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_search_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            String title = mFilterData.get(position);
            // 构造span对象
            SpannableString spannableString = new SpannableString(title);
            // 判断条件是为了防止过滤条件为空时的数组越界问题
            if (mFilterColorIndexList.size() > 0) {
                // 获取对应List数据的索引Map集合然后遍历
                HashMap<Integer, Integer> colorMap = mFilterColorIndexList.get(position);
                for (Map.Entry<Integer, Integer> entry : colorMap.entrySet()) {
                    int start = entry.getKey();
                    int end = entry.getValue();
                    // 设置变色
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            holder.title.setText(spannableString);
        }

        @Override
        public int getItemCount() {
            return mFilterData.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;

            public MyViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
}
