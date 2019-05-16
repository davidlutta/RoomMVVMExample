package com.davidlutta.architectureexample.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.davidlutta.architectureexample.DAO.NoteDao;
import com.davidlutta.architectureexample.Entities.Note;

// you can add more classes in the entities array and
// version shows the version schema of the app and if you make a change to the database schema then increase the version number and uninstall the app then reinstall it
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    //We need to make it a singleton meaning that we don't want to have another instance of this database
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    //Synchronized means only one thread at a time can access this method
    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()// will delete the database and create if from scratch along with the version number
                    .addCallback(roomCallback)
                    .build(); //Returns an instance of this Database
        }
        return instance;
    }

    // To populate our database after it's creation
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        //is called after the database is created
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };



    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db){
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1","Description 1",1));
            noteDao.insert(new Note("Title 2","Description 2",3));
            noteDao.insert(new Note("Title 3","Description 3",2));
            return null;
        }
    }
}
