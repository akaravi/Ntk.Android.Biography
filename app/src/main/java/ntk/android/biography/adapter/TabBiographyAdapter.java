package ntk.android.biography.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.base.adapter.BaseRecyclerAdapter;
import ntk.android.base.entitymodel.biography.BiographyContentOtherInfoModel;
import ntk.android.base.utill.FontManager;
import ntk.android.biography.R;

public class TabBiographyAdapter extends BaseRecyclerAdapter<BiographyContentOtherInfoModel, TabBiographyAdapter.ViewHolder> {


    private Context context;

    public TabBiographyAdapter(Context context, List<BiographyContentOtherInfoModel> arrayList) {
       super(arrayList);
        this.context = context;
        drawable=R.drawable.news_placeholder;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler_tab, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.Btn.setText(list.get(position).Title);
        if (list.get(position).TypeId == 0) {
            if (list.get(position).Title.equals("منبع")) {
                if (list.get(position).HtmlBody.startsWith("http")) {
                    holder.webView.loadData("<html dir=\"ltr\" lang=\"\"><body>" + list.get(position).HtmlBody + "</body></html>", "text/html; charset=utf-8", "UTF-8");
                    holder.Ripple.setOnClickListener(v -> {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).HtmlBody)));
                        holder.webView.loadData("<html dir=\"ltr\" lang=\"\"><body>" + list.get(position).HtmlBody + "</body></html>", "text/html; charset=utf-8", "UTF-8");
                    });
                } else {
                    holder.webView.loadData("<html dir=\"rtl\" lang=\"\"><body>" + list.get(position).HtmlBody + "</body></html>", "text/html; charset=utf-8", "UTF-8");
                    holder.Ripple.setOnClickListener(v ->
                            holder.webView.loadData("<html dir=\"rtl\" lang=\"\"><body>" + list.get(position).HtmlBody + "</body></html>", "text/html; charset=utf-8", "UTF-8")
                    );
                }
            } else {
                holder.webView.loadData("<html dir=\"rtl\" lang=\"\"><body>" + list.get(position).HtmlBody + "</body></html>", "text/html; charset=utf-8", "UTF-8");
                holder.Ripple.setOnClickListener(v ->
                        holder.webView.loadData("<html dir=\"rtl\" lang=\"\"><body>" + list.get(position).HtmlBody + "</body></html>", "text/html; charset=utf-8", "UTF-8")
                );
            }
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.BtnRecyclerTab)
        Button Btn;

        @BindView(R.id.RippleBtnRecyclerTab)
        MaterialRippleLayout Ripple;

        @BindView(R.id.WebViewActDetailNews)
        WebView webView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Btn.setTypeface(FontManager.T1_Typeface(context ));
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setBuiltInZoomControls(true);
        }
    }
}
