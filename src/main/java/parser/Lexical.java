package parser;

import scanner.CodeScanner;
import shared.models.Token;

import java.io.File;
import java.io.FileReader;


public class Lexical{

      final CodeScanner cs;
      private Token currentToken = null;

      public Lexical(File in){
            try{
                  cs  = new CodeScanner( new FileReader(in));
            }catch(Exception e){
                  throw new RuntimeException(e);
            }
      }

      public Token currentToken(){
            return currentToken;
      }

      public String nextToken(){
            try{
                  currentToken = cs.getNext();
            } catch(Exception e){
                  throw new RuntimeException(e);
            }

            if (currentToken==null || currentToken.getValue() == null){
                  System.out.println("file tamum");
                  currentToken = Token.EOF;
            }

            System.out.println(">> "+currentToken);

            switch (currentToken.getType()) {
                  case INTEGER_LITERAL:
                        return "icv";
                  case IDENTIFIER:
                        switch (currentToken.getValue().toString()){
                              case "start":
                                    return "start";
                              default:
                                    return "id";
                        }

                  default:
                        return currentToken.getValue().toString();

            }
      }
}
