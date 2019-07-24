package com.glhd.tb.app.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.glhd.tb.app.R;
import com.glhd.tb.app.base.bean.BeanSpinner;

import java.util.ArrayList;

public class PwAdsType extends MyPopupWindow {

    private ListView listview;
    private ItemMenufilterSortAdapter adapter;
    private ArrayList<BeanSpinner> data = new ArrayList<>();
    private MenuFilter filter;

    public PwAdsType(Context con, ArrayList<BeanSpinner> data, int w, int h) {
        super(con, w, h);
        this.data = data;
        initView();
    }

    public void setFilter(MenuFilter filter) {
        this.filter = filter;
    }

    @Override
    public int layoutId() {
        return R.layout.layout_menufilter_sort;
    }


    String type;

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void initView() {
        listview = (ListView) findViewById(R.id.listview);
        adapter = new ItemMenufilterSortAdapter(con, data);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setChecked(false);
                }

                if (listener != null) {
                    data.get(position).setChecked(true);
                    listener.ok(data.get(position));
                }
                adapter.setSelect(position);
                PwAdsType.this.dismiss();
            }
        });
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getY() < 0 && event.getY() > -filter.getHeight()) {

                    if("巡检状态".equals(type)){
                        if (event.getX() > 0 && event.getX() < w / 3) {
                            dismiss();
                        } else if (event.getX() > w / 3 && event.getX() < w / 3 * 2) {
                            filter.performItemClick(1);
                        } else if (event.getX() > w / 3 * 2 && event.getX() < w / 3 * 3) {
                            filter.performItemClick(2);
                        }
                    }
                    if("媒体类型".equals(type)){
                        if (event.getX() > 0 && event.getX() < w / 3) {
                            filter.performItemClick(0);
                        } else if (event.getX() > w / 3 && event.getX() < w / 3 * 2) {
                            filter.performItemClick(1);
                        } else if (event.getX() > w / 3 * 2 && event.getX() < w / 3 * 3) {
                            dismiss();
                        }
                    }

                }
                return false;
            }
        });


        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (listener != null) {
                    listener.cancel();
                }
            }
        });
    }

    private OkListener listener;

    public void setOnOkListener(OkListener listener) {
        this.listener = listener;
    }

    public interface OkListener {
        public void ok(BeanSpinner bean);

        public void cancel();
    }


    public class ItemMenufilterSortAdapter extends BaseAdapter {

        private ArrayList<BeanSpinner> objects = new ArrayList<BeanSpinner>();

        private Context context;
        private LayoutInflater layoutInflater;
        private int select = -1;

        public ItemMenufilterSortAdapter(Context context, ArrayList<BeanSpinner> objects) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.objects = objects;
        }

        public int getSelect() {
            return select;
        }

        public void setSelect(int select) {
            this.select = select;
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public BeanSpinner getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_menufilter_sort, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews((BeanSpinner) getItem(position), (ViewHolder) convertView.getTag(), position);
            return convertView;
        }

        private void initializeViews(BeanSpinner b, ViewHolder holder, int posi) {

            holder.title.setText(b.getTitle());
            if (b.isChecked()) {
                holder.title.setTextColor(con.getResources().getColor(R.color.titleColor));
            } else {
                holder.title.setTextColor(Color.BLACK);
            }
        }

        protected class ViewHolder {
            private TextView title;

            public ViewHolder(View view) {
                title = (TextView) view.findViewById(R.id.title);
            }
        }
    }

}
