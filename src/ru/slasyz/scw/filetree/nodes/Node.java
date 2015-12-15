package ru.slasyz.scw.filetree.nodes;

public abstract class Node {
    private Integer name;
    private DirectoryNode parent;

    public Node(Integer name) {
        setName(name);
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public DirectoryNode getParent() {
        return parent;
    }

    public void setParent(DirectoryNode parent) {
        this.parent = parent;
    }
}