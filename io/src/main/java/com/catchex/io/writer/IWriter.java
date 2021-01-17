package com.catchex.io.writer;

import java.io.IOException;
import java.nio.file.Path;


public interface IWriter<T>
{
    boolean writeToFile( T content, Path path ) throws IOException;
}
