package com.zook.shipit.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager
{
    public String readFile(String fileName) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

}
