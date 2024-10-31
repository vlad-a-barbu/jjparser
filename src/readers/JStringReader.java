package readers;

import readers.exceptions.JStreamExhaustedException;

public final class JStringReader implements JReader {

    private final String stream;
    private int currentPosition;

    public JStringReader(String stream) {
        if (stream == null || stream.isEmpty())
            throw new IllegalArgumentException("Stream null or empty");
        this.stream = stream;
        this.currentPosition = 0;
    }

    @Override
    public char read() throws JStreamExhaustedException {
        int length = this.stream.length();
        if (this.currentPosition == length)
            throw new JStreamExhaustedException();
        return this.readChar();
    }

    @Override
    public int position() {
        return this.currentPosition;
    }

    @Override
    public void seek(int position) {
        if (position < 0 || position >= this.stream.length())
            throw new IllegalArgumentException("Position out of bounds: " + position);
        this.currentPosition = position;
    }

    private char readChar() {
        return this.stream.charAt(this.currentPosition++);
    }

}
