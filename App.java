
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println(" digite os caracteres");
        String ids = in.nextLine();
        LinkedList<Palavra> lista = new LinkedList<Palavra>();
        ArvoreGramatical a = new ArvoreGramatical();
        String aux[];
       
        a.add("/",null);
       
        Path path1 = Paths.get("dicionario.csv");

        System.out.println("\n" + path1.toString());
      
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("UTF-8"))) {// Charset.defaultCharset())
            String line = reader.readLine();
            while (line != null) {
                    if(line.length()>0) { 
                    aux = line.split(";");
                    Palavra p = new Palavra(aux[0].toLowerCase(),aux[1]);
                         a.addPalavra(p, "/");  
                         lista.add(p);
                         
                  
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }  
        //assim posso isolar so as palavras e os significados nao ficando tudo
        //junto e misturado
       
        LinkedList<String> la = a.buscarPalavras(a,"/"+ids);
        LinkedList<String> nla = new LinkedList<>();


        pularPrimeiroCaractere(la,nla);
  
        for(int i=0;i<la.size();i++){
            System.out.println(la.get(i));
        }
      
        System.out.println(" digite os caracteres");
        String ntd = in.nextLine();
        LinkedList<String> sgn = a.bucasSignificado(a, "/"+ntd);
       
        //pularPrimeiroCaractere(sgn, nsgn);

     

       for(int i=0;i<sgn.size();i++){
        System.out.println(sgn.get(i));
       }

      a.geraDot("arvore.dot");
      //varrer as 2 listas comparar as palavras e pegar o significado

     // System.out.println(a.printSignificados());
       

    }
   public static void pularPrimeiroCaractere(LinkedList<String> lista, LinkedList<String> listaPulada) {
        for (String str : lista) {
            if (str.length() > 1) {
                String strPulada = str.substring(1);
                listaPulada.add(strPulada);
            }
        }
    }
}
