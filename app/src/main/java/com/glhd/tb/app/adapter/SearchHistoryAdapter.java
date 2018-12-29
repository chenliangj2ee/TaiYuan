package com.glhd.tb.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ast365.library.adapter.AstFlowLayoutAdapter;
import com.ast365.library.view.AstFlowViewGroup;
import com.glhd.tb.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenliangj2ee on 2016/8/17.
 */
public class SearchHistoryAdapter extends AstFlowLayoutAdapter<String> {
    private static SharedPreferences preferences;
    private static String cacheName = "searchHistory";
    private Context context;
    private LayoutInflater inflater;
    private List<String> data = new ArrayList<String>();

    public SearchHistoryAdapter(Context context) {
        this.context = context;
        inflater = ((Activity) this.context).getLayoutInflater();
        this.data.addAll(getHistory());
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public View getView(AstFlowViewGroup parent, int position, String s) {
        TextView tv = (TextView) inflater.inflate(R.layout.item_search_history, parent, false);
        tv.setText(s);
        return tv;
    }


    /**
     * 保存搜索历史
     *
     * @param value
     */
    public void putHistory(final String value) {
        if (value == null || "".equals(value.trim()))
            return;
        boolean add = false;
        for (int i = 0; i < data.size(); i++) {
            if (value.equals(data.get(i).trim())) {
                data.remove(i);
                data.add(0, value);
                add = true;
                break;
            }
        }
        if (add == false)
            data.add(0, value);
        notifyDataChanged();
        String historys = "";
        for (int i = 0; i < data.size(); i++) {
            historys = historys + data.get(i);
            if (i + 1 < data.size()) {
                historys = historys + "&";
            }
        }
        preferences = context.getSharedPreferences(cacheName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("searchHistory", historys);
        editor.commit();
    }

    /**
     * 获得搜索历史
     *
     * @return
     */
    public List<String> getHistory() {
        if (context == null)
            return new ArrayList<String>();
        preferences = context.getSharedPreferences(cacheName, Context.MODE_PRIVATE);
        String result = preferences.getString("searchHistory", "");
        ArrayList<String> data = new ArrayList<String>();
        String[] results = result.split("&");
        for (int i = 0; i < results.length; i++) {
            if (!"".equals(results[i].trim())) {
                data.add(results[i].trim());
            }
        }
        return data;
    }

    /**
     * 清空搜索历史
     */
    public void clearHistory() {
        if (context == null)
            return;
        preferences = context.getSharedPreferences(cacheName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor e = preferences.edit();
        e.clear();
        e.commit();
        data.clear();
        notifyDataChanged();
    }


}
