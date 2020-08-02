package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CodeGenerator{
      private Lexical l;
      private File out;

      public CodeGenerator(Lexical l, File out){
            this.l = l;
            this.out = out;
      }

      public void doSemantic(String semantic){
            System.out.println("running "+semantic);
            switch (semantic) {
                  case "parseFinished":
                        doStuff();
                  case "make_type":
                        l.addRecord();
            }

      }

      private void doStuff() {
            var classLoader = ClassLoader.getSystemClassLoader();
            var scaff = classLoader.getResource("codegen/Code.java");
            try {
                  assert scaff != null;
                  var lines = Files.readAllLines(Path.of(scaff.toURI()));
                  var code = Files.readAllLines(l.getIn().toPath());
                  var tarTamizedCode = transform(code);

                  var lastLine = lines.size() -1;
                  lines.addAll(lastLine,tarTamizedCode);
                  File file = new File("./Code.java");
                  file.deleteOnExit();
                  Files.write(file.toPath(), lines);
                  Process proc = new ProcessBuilder("javac", "./Code.java").start();
                  proc.waitFor();
                  System.out.println(proc.exitValue());
                  if (proc.exitValue() != 0) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
                        String line = "";
                        while ((line = bufferedReader.readLine()) != null) {
                              System.out.println(line);
                        }
                  }
                  File classFile = new File("./Code.class");
//                  classFile.deleteOnExit();
                  Process proc2 = Runtime.getRuntime().exec("javap -c ./Code.class");
                  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(proc2.getInputStream()));
                  List<String> bytecodes = new ArrayList<>();
                  String line = "";
                  while ((line = bufferedReader.readLine()) != null) {
                        bytecodes.add(line);
                  }
                  proc2.waitFor();
                  Files.write(out.toPath(), bytecodes);
            } catch (IOException | URISyntaxException | InterruptedException e) {
                  e.printStackTrace();
            }
      }

      private List<String> transform(List<String> code) {
            var allcode = String.join("\n", code);
            // handle func
            allcode = allcode.replaceAll("\\bfunction\\b", "");
            // handle auto
            allcode = allcode.replaceAll("\\bauto\\b", "var");
            // handle boolean
            allcode = allcode.replaceAll("\\bbool\\b", "boolean");
            // handle arg pass
            allcode = allcode.replaceFirst("\\bstart[ \\t]*\\([ \\t]*\\)",
                    "start(String[] args)");
            // handle boolean ops
            allcode = allcode.replaceAll("\\band\\b", "&&");
            allcode = allcode.replaceAll("\\bor\\b", "||");
            allcode = allcode.replaceAll("\\bnot\\b", "!");

            // handle string class
            allcode = allcode.replaceAll("\\bstring\\b", "String");
            allcode = allcode.replaceAll("\\bstring\\[\\]", "String[]");

            // handle repeat until
            allcode = allcode.replaceAll("\\brepeat\\b", "do");
            allcode = allcode.replaceAll("\\buntil\\b", "while");

            // handle switch case
            allcode = allcode.replaceAll("\\bof\\b", "");
            allcode = allcode.replaceAll("\\bbegin\\b", "{");
            allcode = allcode.replaceAll("\\bend\\b", "}");

            //handle foreach
            allcode = allcode.replaceAll("\\bforeach\\b", "for");
            allcode = allcode.replaceAll("\\bin\\b", ":");

            // handle input overloading
            allcode = allcode.replaceAll("Input \\([ \\t\\n]*int[ \\t\\n]*\\)", "Input(idum)");
            allcode = allcode.replaceAll("Input \\([ \\t\\n]*long[ \\t\\n]*\\)", "Input(ldum)");
            allcode = allcode.replaceAll("Input \\([ \\t\\n]*double[ \\t\\n]*\\)", "Input(ddum)");
            allcode = allcode.replaceAll("Input \\([ \\t\\n]*float[ \\t\\n]*\\)", "Input(fdum)");
            allcode = allcode.replaceAll("Input \\([ \\t\\n]*char[ \\t\\n]*\\)", "Input(cdum)");
            allcode = allcode.replaceAll("Input \\([ \\t\\n]*string[ \\t\\n]*\\)", "Input(sdum)");
            allcode = allcode.replaceAll("Input \\([ \\t\\n]*short[ \\t\\n]*\\)", "Input(idum)");

            // handle record;
            allcode = allcode.replaceAll("\\brecord\\b", "class");

            // handle const;
            allcode = allcode.replaceAll("\\bconst\\b", "final");


            return List.of(allcode.split("\n"));
      }
}
