package com.xialacaidan1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity  extends Activity {
    private ImageView iv;
    private PopupWindow popupWindow;
    private ListView lv;
    private EditText et;
    private List<String> strs = new ArrayList<String>();
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListView();// 初始化ListView
        et = (EditText) findViewById(R.id.et);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(MainActivity.this);
                    popupWindow.setContentView(lv);
                    // PopupWindow必须设置高度和宽度
                    popupWindow.setWidth(et.getWidth()); // 设置宽度
                    popupWindow.setHeight(500); // 设置popWin 高度
                    popupWindow.showAsDropDown(et, 0, 0);
                    popupWindow.setOutsideTouchable(true);// 设置后点击其他位置PopupWindow将消失
                } else if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAsDropDown(et);
                }
            }
        });
    }
    /**
     * 初始化ListView
     */
    private void initListView() {
        for (int i = 0; i < 10; i++) {
            strs.add("第" + i + "条记录");
        }
        lv = new ListView(this);
        if (adapter == null) {
            adapter = new MyAdapter();
            lv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        lv.setBackgroundResource(R.drawable.listview_background);
        lv.setDividerHeight(2);
        lv.setVerticalScrollBarEnabled(false);
    }
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return strs.size();
        }
        @Override
        public Object getItem(int position) {
            return strs.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.list_item, null);
                holder = new ViewHolder();
                holder.iv = (ImageView) view.findViewById(R.id.iv_listitem_delete);
                holder.tv = (TextView) view.findViewById(R.id.tv_listitem_content);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            holder.tv.setText(strs.get(position));
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et.setText(strs.get(position));
                    popupWindow.dismiss();
                }
            });
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 从List中移除
                    strs.remove(position);
                    // 更新ListView
                    adapter.notifyDataSetChanged();
                }
            });
            return view;
        }

    }
    private class ViewHolder {
        private TextView tv;
        private ImageView iv;
    }
}
