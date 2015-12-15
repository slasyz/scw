package ru.slasyz.scw.filetree

import ru.slasyz.scw.filetree.FileSystem;

class FileSystemTest extends GroovyTestCase {
    FileSystem fs;

    void setUp() {
        super.setUp()

        fs = new FileSystem();
    }

    void testDirectory() {
        fs.mkdir("home");
        fs.chdir("home");
        fs.mkdir("user1");
        fs.mkdir("user2");
        fs.chdir("user1");

        assertEquals(2, fs.ls("/home").size())
    }

    void testFile() {
        fs.mkdir("home");
        fs.chdir("home")
        fs.mkdir("sl");
        fs.chdir("/home/sl");

        def text = "blah-blah-blah";
        fs.mkfile("test.txt");
        fs.write("test.txt", text);

        assertEquals(text, fs.cat("test.txt"));
    }
}
