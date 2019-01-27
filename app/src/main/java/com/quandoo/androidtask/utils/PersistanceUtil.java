package com.quandoo.androidtask.utils;

import android.content.Context;

import com.quandoo.androidtask.App;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@Deprecated
public class PersistanceUtil {


    /**
     * Saves a serializable object.
     *
     * @param objectToSave The object to save.
     * @param fileName The name of the file.
     * @param <T> The type of the object.
     */

    public static <T extends Serializable> void saveSerializable( T objectToSave, String fileName) {
        try {
            FileOutputStream fileOutputStream = App.Companion.getInstance().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(objectToSave);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a serializable object.
     *
     * @param fileName The filename.
     * @param <T> The object type.
     *
     * @return the serializable object.
     */

    public static<T extends Serializable> T readSerializable( String fileName) {
        T objectToReturn = null;

        try {
            FileInputStream fileInputStream = App.Companion.getInstance().openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            objectToReturn = (T) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return objectToReturn;
    }

    /**
     * Removes a specified file.
     *
     * @param filename The name of the file.
     */

    public static void removeSerializable(String filename) {
        App.Companion.getInstance().deleteFile(filename);
    }
}
