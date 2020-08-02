package appl;

import parser.CodeGenerator;
import parser.Lexical;
import parser.Parser;

import java.io.File;
import java.io.InputStream;

public class Main {

    public static InputStream getInputStreamFromResource(String fileName) {
        try{
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            return classLoader.getResourceAsStream(fileName);
        } catch(Exception e){
            throw new RuntimeException("parse table nist", e);
        }
    }

    public static void main(String[] args) {
        File in = new File(args[0]);
        File out = new File(args[1]);
        InputStream pt = getInputStreamFromResource("table.npt");

        Lexical l = new Lexical(in);
        CodeGenerator cg = new CodeGenerator(l,out);


        Parser parser = new Parser( l, cg ,pt);
        parser.parse();
    }
}
