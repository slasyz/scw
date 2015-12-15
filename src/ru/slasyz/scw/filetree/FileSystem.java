package ru.slasyz.scw.filetree;

import ru.slasyz.scw.filetree.exceptions.*;
import ru.slasyz.scw.filetree.nodes.DirectoryNode;
import ru.slasyz.scw.filetree.nodes.FileNode;
import ru.slasyz.scw.filetree.nodes.Node;

import java.util.*;

public class FileSystem {
    DirectoryNode root;

    public FileSystem() {
        root = new DirectoryNode(null);
    }

    private Node getNode(Integer[] path) throws AboveRootException, NotFoundException {
        Node current = root;

        for (Integer name : path) {
            try {
                DirectoryNode currentDirectory = (DirectoryNode) current;
                current = currentDirectory.getChild(name);
            } catch (ClassCastException ex) {
                // There is a file in the middle of path.
                throw new NotFoundException();
            }
        }

        return current;
    }

    private FileNode getFileNode(Integer[] path) throws NotFileException, AboveRootException, NotFoundException {
        Node node = getNode(path);

        if (!(node instanceof FileNode))
            throw new NotFileException();

        return (FileNode) node;
    }

    private DirectoryNode getDirectoryNode(Integer[] path) throws NotDirectoryException, AboveRootException, NotFoundException {
        Node node = getNode(path);

        if (!(node instanceof DirectoryNode))
            throw new NotDirectoryException();

        return (DirectoryNode) node;
    }

    private DirectoryNode getParentDirectory(Integer[] path) throws NotFoundException, AboveRootException, NotDirectoryException {
        Integer[] pathToParent = Arrays.copyOf(path, path.length - 1);

        Node parent = getNode(pathToParent);
        if (!(parent instanceof DirectoryNode))
            throw new NotDirectoryException();

        return (DirectoryNode) parent;
    }

    // Analog of "mkdir" command in UNIX shell.
    // strPath is a path to new directory.
    public void mkdir(Integer[] path) throws NotFoundException, AboveRootException, NotDirectoryException, NodeAlreadyExists {
        getParentDirectory(path).createDirectory(path[path.length - 1]);
    }

    // Analog of "touch" command in UNIX shell
    // (but throws an exception if file already exists).
    // strPath is a path to new directory.
    public void mkfile(Integer[] path) throws NotFoundException, AboveRootException, NotDirectoryException, NodeAlreadyExists {
        getParentDirectory(path).createFile(path[path.length - 1]);
    }

    // Analog of "cat" command in UNIX shell.
    public String cat(Integer[] path) throws AboveRootException, NotFoundException, NotFileException {
        FileNode file = getFileNode(path);
        return file.read();
    }

    // Analog of "ls" command in UNIX shell.
    public List<Integer> ls(Integer[] path) throws NotDirectoryException, AboveRootException, NotFoundException {
        DirectoryNode directory = getDirectoryNode(path);
        return directory.getContent();
    }

    // Just write content to a file.
    public void write(Integer[] path, String content) throws AboveRootException, NotFoundException, NotFileException {
        FileNode file = getFileNode(path);
        file.write(content);
    }

    // Search for files by exact filename.
    public List<Node> find(Integer name) {
        return findFrom(name, root);
    }

    private List<Node> findFrom(Integer name, Node node) {
        List<Node> result = new ArrayList<>();
        if (name.equals(node.getName()))
            result.add(node);

        if (node instanceof DirectoryNode)
            for (Node child : ((DirectoryNode) node).getChildren())
                result.addAll(findFrom(name, child));

        return result;
    }

    // Remove file.
    public void rm(Integer[] path) throws AboveRootException, NotFoundException, NotDirectoryException {
        DirectoryNode parent = getParentDirectory(path);
        parent.remove(path[path.length - 1]);
    }
}