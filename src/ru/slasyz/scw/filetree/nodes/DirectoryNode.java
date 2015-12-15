package ru.slasyz.scw.filetree.nodes;

import ru.slasyz.scw.filetree.exceptions.NodeAlreadyExists;
import ru.slasyz.scw.filetree.exceptions.NotFoundException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryNode extends Node {
    private List<Node> children;

    public DirectoryNode(Integer name) {
        super(name);

        children = new ArrayList<>();
    }

    public boolean contains(Integer name) {
        try {
            getChild(name);
            return true;
        } catch (NotFoundException ex) {
            return false;
        }
    }

    public List<Integer> getContent() {
        List<Integer> result = new ArrayList<>();

        for (Node child : children)
            result.add(child.getName());

        return result;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getChild(Integer name) throws NotFoundException {
        for (Node child : children) {
            if (child.getName().equals(name))
                return child;
        }

        throw new NotFoundException();
    }

    private void create(Node node) throws NodeAlreadyExists {
        if (contains(node.getName()))
            throw new NodeAlreadyExists();

        children.add(node);
    }

    public void createFile(Integer name) throws NodeAlreadyExists {
        create(new FileNode(name));
    }

    public void createDirectory(Integer name) throws NodeAlreadyExists {
        create(new DirectoryNode(name));
    }

    public void remove(Integer name) throws NotFoundException {
        for (Node child : children) {
            if (child.getName().equals(name))
                children.remove(child);
        }
    }
}