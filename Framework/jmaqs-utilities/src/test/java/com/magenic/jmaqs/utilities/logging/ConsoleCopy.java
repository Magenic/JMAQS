/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class ConsoleCopy {
    /**
     * Used to Write to the log file.
     */
    private PrintStream fileWriter;

    /**
     * Used to Write to both the console and the log.
     */
    private PrintStream doubleWriter;

    /**
     * Stores the original console output.
     */
    private PrintStream oldOut;

    /**
     * Initializes a new instance of the ConsoleCopy class.
     * @param path Path of the Log File
     */
    public ConsoleCopy(String path) {
        this.oldOut = System.out;

        try {
            this.fileWriter = new PrintStream(new FileOutputStream(new File(path), true));
            this.doubleWriter = new DoubleWriter(this.fileWriter, this.oldOut);
        } catch (Exception e) {
            System.out.println("Cannot open file for writing");
            System.out.println(e.getMessage());
            return;
        }

        System.setOut(this.doubleWriter);
    }

    /**
     * Cleans up the Writers and reverts.
     */
    //@Override
    public void close() {
        System.setOut(this.oldOut);
        if (this.fileWriter != null) {
            this.fileWriter.flush();
            this.fileWriter.close();
            this.fileWriter = null;
        }

        // Suggest to JVM to run garbage collector. No guarantee that this will run.
        System.gc();
    }

    /**
     * Custom PrintStream that writes to both a log file and the console.
     */
    private class DoubleWriter extends PrintStream {
        /**
         * Used to write to the Log File.
         */
        private PrintStream fileOutput;

        /**
         * Used to write to the console.
         */
        private PrintStream consoleOutput;

        /**
         * Initializes a new instance of the DoubleWriter class.
         * @param fileOutput Used to write to the Log
         * @param consoleOutput Used to write to the console
         */
        public DoubleWriter(PrintStream fileOutput, PrintStream consoleOutput) {
            super(consoleOutput);
            this.fileOutput = fileOutput;
            this.consoleOutput = consoleOutput;
        }

        /**
         * Write to both outputs.
         * @param value Byte value to write
         */
        @Override
        public void write(int value) {
            this.fileOutput.write(value);
            this.consoleOutput.write(value);
        }

        /**
         * Writes bytes from byte array with offset and length.
         * @param buf  Array of bytes to write
         * @param off Offset point to start writing
         * @param len Length of the bytes
         */
        @Override
        public void write(byte[] buf, int off, int len) {
            this.fileOutput.write(buf, off, len);
            this.consoleOutput.write(buf, off, len);
        }
    }
}