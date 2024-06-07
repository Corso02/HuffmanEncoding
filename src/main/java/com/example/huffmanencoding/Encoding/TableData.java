package com.example.huffmanencoding.Encoding;

public class TableData {
    private String character;
    private int count;
    private String encoding;

    public TableData(String character, int count, String encoding) {
        this.character = character;
        this.count = count;
        this.encoding = encoding;
    }

    public String getCharacter() {
        return character;
    }

    public int getCount() {
        return count;
    }

    public String getEncoding() {
        return encoding;
    }

    @Override
    public String toString() {
        return "TableData { character = "
                + this.character
                + ", count = " + this.count
                + ", encoding = " + this.encoding
                + "}";
    }
}
