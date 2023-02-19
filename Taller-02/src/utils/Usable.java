package utils;

import java.io.*;
import java.util.Iterator;
import java.util.Objects;

public final class Usable {
    public static int opens = 0;

    public static final class Reader implements Iterable<String>{
        private final BufferedReader file;
        public Reader(File file) throws IOException{
            Usable.opens ++;
            this.file = new BufferedReader(new FileReader(file));
        }
        public Reader(String file) throws IOException{
            Usable.opens ++;
            this.file = new BufferedReader(new FileReader(file));
        }
        @Override
        public Iterator<String> iterator() {
            return file.lines().iterator();
        }
        public void close() throws IOException{
            Usable.opens --;
            file.close();
        }
    }

    public static final class Writer {
        private final BufferedWriter file;
        public Writer(File file) throws IOException{
            Usable.opens ++;
            this.file = new BufferedWriter(new FileWriter(file));
        }
        public void writeByStringArray(String[] str, boolean with_jump) throws IOException{
            for (String line: str){
                file.write(line);
                if (with_jump) if (!Objects.equals(line, "")) file.newLine();
            }
        }
        public void writeByStringArray(String[] str) throws IOException{
            for (String line: str){
                file.write(line);
            }
        }
        public void close() throws IOException{
            Usable.opens --;
            file.close();
        }
    }


}
