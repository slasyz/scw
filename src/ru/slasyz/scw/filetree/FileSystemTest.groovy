package ru.slasyz.scw.filetree

import ru.slasyz.scw.filetree.FileSystem

class FileSystemTest extends GroovyTestCase {
    FileSystem fs;

    void setUp() {
        super.setUp()

        fs = new FileSystem();
    }

    void testDirectory() {
        fs.mkdir([1] as Integer[]);
        fs.mkdir([1, 2] as Integer[]);
        fs.mkdir([1, 3] as Integer[]);

        assertEquals(2, fs.ls([1] as Integer[]).size())
    }

    void testFile() {
        fs.mkdir([1] as Integer[]);
        fs.mkdir([1, 2] as Integer[]);

        def text = "blah-blah-blah";
        fs.mkfile([1, 2, 3] as Integer[]);
        fs.write([1, 2, 3] as Integer[], text);

        assertEquals(text, fs.cat([1, 2, 3] as Integer[]));
    }

    void testFind() {
        fs.mkdir([1] as Integer[]);
        fs.mkdir([1, 2] as Integer[]);
        fs.mkdir([1, 2, 3] as Integer[]);
        fs.mkdir([10] as Integer[]);
        fs.mkdir([10, 3] as Integer[]);

        assertEquals(2, fs.find(3).size())
    }

    void testRm() {
        fs.mkdir([1] as Integer[]);
        fs.mkdir([1, 2] as Integer[]);
        fs.mkdir([1, 3] as Integer[]);
        fs.mkdir([1, 4] as Integer[]);

        fs.rm([1, 3] as Integer[]);

        assertEquals(2, fs.ls([1] as Integer[]).size());
    }
}
