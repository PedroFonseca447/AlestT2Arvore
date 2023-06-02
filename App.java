
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;


public class App {
    public static void main(String[] args) throws Exception {
        LinkedList<Palavra> lista = new LinkedList<>();
        ArvoreGramatical a = new ArvoreGramatical();
        String aux[];
        a.add("/",null);       
        Path path1 = Paths.get("novo.csv");

        System.out.println("\n" + path1.toString());
      
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("UTF-8"))) {// Charset.defaultCharset())
            String line = reader.readLine();
            while (line != null) {
                    if(line.length()>0) { 
                    aux = line.split(";");
                    Palavra p = new Palavra(aux[0],aux[1]);
                  a.add(p, "/");  
               
                    lista.add(p);
                    
                  
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }  
       System.out.println("Lista de palavras e seus significados" + lista);
      System.out.println(a.possitionsPre());
    
      a.geraDot("arvore.dot");
    }
 
}
