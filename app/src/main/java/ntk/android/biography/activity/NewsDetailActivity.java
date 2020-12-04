package ntk.android.biography.activity;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ntk.android.base.activity.news.BaseNewsDetail_1_Activity;
import ntk.android.base.entitymodel.news.NewsCommentModel;
import ntk.android.base.entitymodel.news.NewsContentOtherInfoModel;
import ntk.android.biography.adapter.NewsCommentAdapter;

public class NewsDetailActivity extends BaseNewsDetail_1_Activity {
    @Override
    public RecyclerView.Adapter createCommentAdapter(List<NewsCommentModel> listItems) {
        return new NewsCommentAdapter(this, listItems);
    }

    @Override
    protected RecyclerView.Adapter createOtherInfoAdapter(List<NewsContentOtherInfoModel> info) {
        return new TabNewsAdapter(this, info);
    }


//    //@newcode
//    private void SetDataOtherinfo(NewsContentOtherInfoResponse model) {
//        Info = model;
//        List<NewsContentOtherInfo> Info = new ArrayList<>();
//        if (this.model.Item.description != null) {
//            NewsContentOtherInfo i = new NewsContentOtherInfo();
//            i.Title = "توضیحات";
//            i.TypeId = 0;
//            i.HtmlBody = this.model.Item.description;
//            Info.add(i);
//        }
//        if (this.model.Item.Body != null) {
//            NewsContentOtherInfo i1 = new NewsContentOtherInfo();
//            i1.Title = "متن اخبار";
//            i1.TypeId = 0;
//            i1.HtmlBody = this.model.Item.Body;
//            Info.add(i1);
//        }
//
//        for (NewsContentOtherInfo ai : model.ListItems) {
//            switch (ai.TypeId) {
//                case 21:
//                    Lbls.get(7).setText(ai.Title);
//                    ai.HtmlBody = ai.HtmlBody.replace("<p>", "");
//                    ai.HtmlBody = ai.HtmlBody.replace("</p>", "");
//                    Lbls.get(6).setText(Html.fromHtml(ai.HtmlBody));
//                    break;
//                case 22:
//                    Lbls.get(9).setText(ai.Title);
//                    ai.HtmlBody = ai.HtmlBody.replace("<p>", "");
//                    ai.HtmlBody = ai.HtmlBody.replace("</p>", "");
//                    Lbls.get(8).setText(Html.fromHtml(ai.HtmlBody));
//                    break;
//                case 23:
//                    Lbls.get(11).setText(ai.Title);
//                    ai.HtmlBody = ai.HtmlBody.replace("<p>", "");
//                    ai.HtmlBody = ai.HtmlBody.replace("</p>", "");
//                    Lbls.get(10).setText(Html.fromHtml(ai.HtmlBody));
//                    break;
//                default:
//                    Info.add(ai);
//                    break;
//            }
//        }
//        if (this.model.Item.Source != null) {
//            NewsContentOtherInfo i2 = new NewsContentOtherInfo();
//            i2.Title = "منبع";
//            i2.TypeId = 0;
//            i2.HtmlBody = this.model.Item.Source;
//            Info.add(i2);
//        }
//        v adapter = new AdTabNews(NewsDetailActivity.this, Info);
//        RvTab.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }
//
//    //@newcode
//    private void SetData(NewsContentResponse model) {
//        ImageLoader.getInstance().displayImage(model.Item.imageSrc, ImgHeader);
//        Lbls.get(0).setText(model.Item.Title);
//        Lbls.get(1).setText(model.Item.Title);
//        Lbls.get(3).setText(String.valueOf(model.Item.viewCount));
//        if (model.Item.Favorited) {
//            ((ImageView) findViewById(R.id.imgHeartActDetailNews)).setImageResource(R.drawable.ic_fav_full);
//        }
//        double rating = 0.0;
//        int sumClick = 10;
//        if (model.Item.ScoreSumPercent / sumClick > 0 && model.Item.ScoreSumPercent / sumClick <= 10) {
//            rating = 0.5;
//        } else if (model.Item.ScoreSumPercent / sumClick > 10 && model.Item.ScoreSumPercent / sumClick <= 20) {
//            rating = 1.0;
//        } else if (model.Item.ScoreSumPercent / sumClick > 20 && model.Item.ScoreSumPercent / sumClick <= 30) {
//            rating = 1.5;
//        } else if (model.Item.ScoreSumPercent / sumClick > 30 && model.Item.ScoreSumPercent / sumClick <= 40) {
//            rating = 2.0;
//        } else if (model.Item.ScoreSumPercent / sumClick > 40 && model.Item.ScoreSumPercent / sumClick <= 50) {
//            rating = 2.5;
//        } else if (model.Item.ScoreSumPercent / sumClick > 50 && model.Item.ScoreSumPercent / sumClick <= 60) {
//            rating = 3.0;
//        } else if (model.Item.ScoreSumPercent / sumClick > 60 && model.Item.ScoreSumPercent / sumClick <= 70) {
//            rating = 3.5;
//        } else if (model.Item.ScoreSumPercent / sumClick > 70 && model.Item.ScoreSumPercent / sumClick <= 80) {
//            rating = 4.0;
//        } else if (model.Item.ScoreSumPercent / sumClick > 80 && model.Item.ScoreSumPercent / sumClick <= 90) {
//            rating = 4.5;
//        } else if (model.Item.ScoreSumPercent / sumClick > 90) {
//            rating = 5.0;
//        }
//        Rate.setRating((float) rating);
//
//        Rv.setHasFixedSize(true);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
//        Rv.setLayoutManager(manager);
//
//        AdNews adNews = new AdNews(this, model.ListItems);
//        Rv.setAdapter(adNews);
//        adNews.notifyDataSetChanged();
//        if (model.ListItems.isEmpty()) {
//            Lbls.get(5).setVisibility(View.GONE);
//            Lbls.get(4).setVisibility(View.GONE);
//        }
//    }
//
//    //@newcode
//    @OnClick(R.id.lblAllMenuActDetailNews)
//    public void onMoreNewsClick() {
//        this.startActivity(new Intent(this, NewsListActivity.class));
//    }
//
//    @OnClick(R.id.imgBackActDetailNews)
//    public void ClickBack() {
//        finish();
//    }


}
