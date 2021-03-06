package ntk.android.biography.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import ntk.android.biography.BuildConfig;
import ntk.android.biography.model.Notify;


@Database(entities = {Notify.class}, version = 1, exportSchema = false)
public abstract class RoomDb extends RoomDatabase {

    public abstract NotificationDoa NotificationDoa();

    private static RoomDb Instance;

    public static RoomDb getRoomDb(Context context) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(context.getApplicationContext(), RoomDb.class, BuildConfig.APPLICATION_ID)
                    .allowMainThreadQueries()
                    .build();
        }
        return Instance;
    }

    public static void DestroyInstance() {
        Instance = null;
    }
}
