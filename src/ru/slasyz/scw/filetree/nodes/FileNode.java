package ru.slasyz.scw.filetree.nodes;

public class FileNode extends Node {
    private String content;

    public FileNode(String name) {
        super(name);
    }

    public String read() {
        return content;
    }

    public void write(String content) {
        this.content = content;
    }
}