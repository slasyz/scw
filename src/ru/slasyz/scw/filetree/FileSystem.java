package ru.slasyz.scw.filetree;

import ru.slasyz.scw.filetree.exceptions.*;
import ru.slasyz.scw.filetree.nodes.DirectoryNode;
import ru.slasyz.scw.filetree.nodes.FileNode;
import ru.slasyz.scw.filetree.nodes.Node;

import java.util.Arrays;
import java.util.List;

public class FileSystem {
    DirectoryNode root;
    DirectoryNode workingDirectory;

    public FileSystem() {
        root = new DirectoryNode(null);
        workingDirectory = root;
    }

    private Node getNode(String[] path) throws AboveRootException, NotFoundException {
        Node current = workingDirectory;

        for (String name : path) {
            if (name.equals("")) {
                current = root;
                continue;
            }

            if (name.equals("."))
                continue;

            if (name.equals("..")) {
                if (current == root)
                    throw new AboveRootException();
                else
                    current = current.getParent();

                continue;
            }

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

    private FileNode getFileNode(String[] path) throws NotFileException, AboveRootException, NotFoundException {
        Node node = getNode(path);

        if (!(node instanceof FileNode))
            throw new NotFileException();

        return (FileNode) node;
    }

    private DirectoryNode getDirectoryNode(String[] path) throws NotDirectoryException, AboveRootException, NotFoundException {
        Node node = getNode(path);

        if (!(node instanceof DirectoryNode))
            throw new NotDirectoryException();

        return (DirectoryNode) node;
    }

    private DirectoryNode getParentDirectory(String[] path) throws NotFoundException, AboveRootException, NotDirectoryException {
        String[] pathToParent = Arrays.copyOf(path, path.length - 1);

        Node parent = getNode(pathToParent);
        if (!(parent instanceof DirectoryNode))
            throw new NotDirectoryException();

        return (DirectoryNode) parent;
    }

    // Analog of "cd" command in UNIX shell.
    // Something like that is allowable:
    //   - "relative/to/current/directory"
    //   - "/absolute/path"
    //   - "../relative/to/parent/directory"
    public void chdir(String[] path) throws NotDirectoryException, AboveRootException, NotFoundException {
        Node node = getNode(path);
        if (!(node instanceof DirectoryNode))
            throw new NotDirectoryException();

        workingDirectory = (DirectoryNode) node;
    }

    public void chdir(String strPath) throws NotDirectoryException, AboveRootException, NotFoundException {
        String[] path = strPath.split("/");
        chdir(path);
    }

    // Analog of "mkdir" command in UNIX shell.
    // strPath is a path to new directory.
    public void mkdir(String[] path) throws NotFoundException, AboveRootException, NotDirectoryException, NodeAlreadyExists {
        getParentDirectory(path).createDirectory(path[path.length - 1]);
    }

    public void mkdir(String strPath) throws NotFoundException, AboveRootException, NotDirectoryException, NodeAlreadyExists {
        String[] path = strPath.split("/");
        mkdir(path);
    }

    // Analog of "touch" command in UNIX shell
    // (but throws an exception if file already exists).
    // strPath is a path to new directory.
    public void mkfile(String[] path) throws NotFoundException, AboveRootException, NotDirectoryException, NodeAlreadyExists {
        getParentDirectory(path).createFile(path[path.length - 1]);
    }

    public void mkfile(String strPath) throws NotFoundException, AboveRootException, NotDirectoryException, NodeAlreadyExists {
        String[] path = strPath.split("/");
        mkfile(path);
    }

    // Analog of "cat" command in UNIX shell.
    public String cat(String[] path) throws AboveRootException, NotFoundException, NotFileException {
        FileNode file = getFileNode(path);
        return file.read();
    }

    public String cat(String strPath) throws AboveRootException, NotFoundException, NotFileException {
        String[] path = strPath.split("/");
        return cat(path);
    }

    // Analog of "ls" command in UNIX shell.
    public List<String> ls(String[] path) throws NotDirectoryException, AboveRootException, NotFoundException {
        DirectoryNode directory = getDirectoryNode(path);
        return directory.getContent();
    }

    public List<String> ls(String strPath) throws NotDirectoryException, AboveRootException, NotFoundException {
        String[] path = strPath.split("/");
        return ls(path);
    }

    // Just write content to a file.
    public void write(String[] path, String content) throws AboveRootException, NotFoundException, NotFileException {
        FileNode file = getFileNode(path);
        file.write(content);
    }

    public void write(String strPath, String content) throws AboveRootException, NotFoundException, NotFileException {
        String[] path = strPath.split("/");
        write(path, content);
    }
}