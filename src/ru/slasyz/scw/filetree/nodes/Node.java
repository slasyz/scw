package ru.slasyz.scw.filetree.nodes;

public abstract class Node {
    private String name;
    private DirectoryNode parent;

    public Node(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DirectoryNode getParent() {
        return parent;
    }

    public void setParent(DirectoryNode parent) {
        this.parent = parent;
    }
}