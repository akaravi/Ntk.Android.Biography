package ntk.android.biography.event;

public class HtmlBodyBlogEvent {

    private String Html;

    public HtmlBodyBlogEvent(String m) {
        this.Html = m;
    }

    public String GetMessage() {
        return Html;
    }
}

