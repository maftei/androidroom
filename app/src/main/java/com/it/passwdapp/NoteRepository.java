package com.it.passwdapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.io.NotActiveException;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application)
    {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao =database.notedao();// apeleaza metoda din interfata NoteDao
        allNotes = noteDao.getAllNotes();
    }

    public void insert (Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update (Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);

    }                                                                   //this methods here are the API that the repository expose to  the outside

    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);

    }

    public void deleteAllNotes() {
        new DeleteAlltNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    //video 4 mnutul 4:00 de revazut
    private static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;
        private InsertNoteAsyncTask(NoteDao noteDao)
        {
            this.noteDao = noteDao;

        }
        @Override
        protected Void doInBackground(Note... notes)
        {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    //1
    private static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;
        private UpdateNoteAsyncTask(NoteDao noteDao)
        {
            this.noteDao = noteDao;

        }
        @Override
        protected Void doInBackground(Note... notes)
        {
            noteDao.update(notes[0]);
            return null;
        }
    }

    //2
    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;
        private DeleteNoteAsyncTask(NoteDao noteDao)
        {
            this.noteDao = noteDao;

        }
        @Override
        protected Void doInBackground(Note... notes)
        {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    //3
    private static class DeleteAlltNotesAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private NoteDao noteDao;
        private DeleteAlltNotesAsyncTask(NoteDao noteDao)
        {
            this.noteDao = noteDao;

        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
