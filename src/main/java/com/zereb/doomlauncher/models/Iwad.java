package com.zereb.doomlauncher.models;

import javafx.beans.property.SimpleStringProperty;

import java.nio.file.Path;

public class Iwad {

    private Path path;
    public final SimpleStringProperty iwadName;

    public Iwad() {
        iwadName = new SimpleStringProperty();
    }

    public void set(Path newIwad){
        path = newIwad;
        iwadName.set(newIwad.getFileName().toString());
    }

    public Path getParent() {
        return path.getParent();
    }

    public boolean isEmpty(){
        return path == null;
    }

    @Override
    public String toString() {
        if (path == null) return "";
        return path.toString();
    }
}
