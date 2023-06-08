
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;


public class App {
    public static void main(String[] args) throws Exception {
        LinkedList<Palavra> lista = new LinkedList<Palavra>();
        ArvoreGramatical a = new ArvoreGramatical();
        String aux[];
        a.add("/",null);
       
        Path path1 = Paths.get("dicionario.csv");

        System.out.println("\n" + path1.toString());
      
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("UTF-8"))) {// Charset.defaultCharset())
            String line = reader.readLine();
            Integer apoio=0;
            while (line != null) {
                    if(line.length()>0) { 
                    aux = line.split(";");
                    Palavra p = new Palavra(aux[0].toLowerCase(),aux[1]);
                         a.add(p, "/");
                         apoio++;  
                         lista.add(p);
                         
                  
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }  
        //assim posso isolar so as palavras e os significados nao ficando tudo
        //junto e misturado
        LinkedList<String> wordsWithPrefix = a.findWordsWithPrefix("car");
        for(int i=0;i<lista.size();i++){
            Palavra novoAux = lista.get(i);
            String ant = novoAux.getPalavra();
            String jux = novoAux.getSignificado();
            for(int j=0; j< wordsWithPrefix.size();j++){
                if(ant==wordsWithPrefix.get(j)){
                    System.out.println(ant+" : "+jux);
                }
            }
         //   System.out.println(ant+"\n Significado: "+jux);
        }
       // System.out.println(a.printSignificados());
     
     //   System.out.println(a.count);
      a.geraDot("arvore.dot");
      System.out.println(a.count);
      //varrer as 2 listas comparar as palavras e pegar o significado
        System.out.println("Palavras com prefixo 'ab': " + wordsWithPrefix);

    }
 
}
