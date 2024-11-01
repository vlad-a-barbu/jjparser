package readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import readers.exceptions.JStreamExhaustedException;

public final class JFileReader implements JReader, AutoCloseable {

    private final BufferedReader reader;
    private final ArrayList<Character> buffer = new ArrayList<>();

    private int currentPosition = 0;

    public JFileReader(String path) throws FileNotFoundException {
        if (path == null || path.isEmpty())
            throw new IllegalArgumentException("File path null or empty");
        this.reader = new BufferedReader(new FileReader(new File(path)));
    }

    @Override
    public char read() throws JStreamExhaustedException {
        if (this.currentPosition == this.buffer.size())
            this.readNextLine();
        return this.buffer.get(this.currentPosition++);
    }

    @Override
    public int position() {
        return this.currentPosition;
    }

    @Override
    public void seek(int position) {
        if (position < 0 || position >= this.buffer.size())
            throw new IllegalArgumentException("Position out of bounds: " + position);
        this.currentPosition = position;
    }

    private void readNextLine() throws JStreamExhaustedException {
        String line;
        try {
            line = this.reader.readLine();
        } catch (IOException _) {
            line = null;
        }
        if (line == null)
            throw new JStreamExhaustedException();
        line.chars().forEach(c -> buffer.add((char) c));
    }

    @Override
    public void close() throws IOException {
        reader.close();
        buffer.clear();
    }

}
