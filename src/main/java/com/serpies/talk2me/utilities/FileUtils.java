package com.serpies.talk2me.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class FileUtils {

    @Autowired
    private Properties properties;

    public Optional<URI> createFile(String name, byte[] data){

        Path destination = Path.of(this.properties.getImagesPath(), name);
        File file = destination.toFile();

        if (saveFile(file, data)){
            return Optional.of(file.toURI());
        }

        return Optional.empty();

    }

    private static boolean saveFile(File file, byte[] data) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String getName(URI uri){
        Path path = Paths.get(uri);
        return path.getFileName().toString();
    }

    public byte[] readFile(URI uri){

        Path path = Paths.get(uri);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return null;
        }

    }

}
