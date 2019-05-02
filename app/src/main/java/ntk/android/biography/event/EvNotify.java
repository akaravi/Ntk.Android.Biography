package ntk.android.biography.event;

public class EvNotify {

    private boolean DataChange;

    public EvNotify(boolean DC) {
        this.DataChange = DC;
    }

    public boolean DataChange() {
        return DataChange;
    }
}
