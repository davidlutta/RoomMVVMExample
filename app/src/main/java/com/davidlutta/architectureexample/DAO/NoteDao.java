package com.davidlutta.architectureexample.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.davidlutta.architectureexample.Entities.Note;

import java.util.List;

//Tells Room that this interface is a Dao and room will generate all the necessary code for us
@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();
    //With LiveData if there are any changes with our table room will automatically change it for us
}
