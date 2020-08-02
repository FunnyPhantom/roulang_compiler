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
//                  file.deleteOnExit();
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
            allcode = allcode.replaceAll("\\bfunction\\b", "");
            allcode = allcode.replaceAll("\\bauto\\b", "var");
            allcode = allcode.replaceAll("\\bbool\\b", "boolean");
            allcode = allcode.replaceFirst("\\bstart[ \\t]*\\([ \\t]*\\)",
                    "start(String[] args)");
            allcode = allcode.replaceAll("\\band\\b", "&&");
            allcode = allcode.replaceAll("\\bor\\b", "||");
            allcode = allcode.replaceAll("\\bnot\\b", "!");
            allcode = allcode.replaceAll("\\bstring\\b", "String");
            allcode = allcode.replaceAll("\\bstring\\[\\]", "String[]");
            

            var pattern = Pattern.compile("\\bvoid\\b");
            var matcher = pattern.matcher(allcode);
            if (matcher.find()) {
                  System.out.println("mathc");
                  matcher.results().forEach(matchResult -> {
                        System.out.println(matchResult.groupCount());
                        System.out.println(matchResult.group());
                  });
            } else {
                  System.out.println("no match");
            }

            return List.of(allcode.split("\n"));
      }
}
