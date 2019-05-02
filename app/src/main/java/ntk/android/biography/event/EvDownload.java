package ntk.android.biography.event;

public class EvDownload {

    private boolean DataChange;

    public EvDownload(boolean DC) {
        this.DataChange = DC;
    }

    public boolean DataChange() {
        return DataChange;
    }
}
