package readers;

import readers.exceptions.JStreamExhaustedException;

public interface JReader {

    char read() throws JStreamExhaustedException;

    int position();

    void seek(int position);

}
