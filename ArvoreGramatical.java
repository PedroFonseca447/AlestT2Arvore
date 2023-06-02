import java.util.LinkedList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class ArvoreGramatical{
    public int count;

    private class Nodo{

        public Nodo father;

        public String palavra;

        public LinkedList<Nodo> subtree;
        //para adicionar depois de ja ter feito o root
        public Nodo(String p,Nodo pai){
            palavra=p;
            father=pai;
            subtree = new LinkedList<>();
        }
        //para criar o primeiro nodo
        public Nodo(String p){
            palavra=p;
            father=null;
            subtree = new LinkedList<>();
        }
        public void addSubtree(Nodo entrada){
            subtree.add(entrada);

            entrada.father=this;
        }
        public int getSubtreeSize(){
            return subtree.size();
        }
        public Nodo getSubtree(int i) throws Exception{
            if(i<0 || i>subtree.size()){
                throw new IndexOutOfBoundsException();
            }
            return subtree.get(i);
        }
    }
         public Nodo root;

        public ArvoreGramatical(){
            this.count=0;
            this.root=null;
        }

        public boolean add(String letra,String pai){
            Nodo aux = new Nodo(letra);
            
            if(pai==null){
                if(root==null){
                    root=aux;
                    count++;
                    return true ;
                }
                else
                return false;
            }
            Nodo ref = searchNode(pai, root);

            if(ref==null){
                return false;
            }
            //faz o nodo apontar para o pai
            aux.father=ref;
            //adciona o nodo e faz o pai apontar para o filho em sua lista
            ref.addSubtree(aux);
            count++;
            return true;
            
        }
        public LinkedList<String> possitionsPre() throws Exception {
            LinkedList<String> lista = new LinkedList<>();
            possitionsPre(root, lista);
            return lista;
        }
        private void possitionsPre(Nodo aux,LinkedList<String> lista) throws Exception {
           if(aux!=null){
            //em tese primeiro visitaria o nodo pai
            lista.add(aux.palavra);
            //depois visitaria o filho usando os aux para saber se viu
            for(int i=0;i<aux.getSubtreeSize();i++){
                possitionsPre(aux.getSubtree(i), lista);
            }
           }
        }
        public Nodo searchNode(String letra, Nodo alvo) {
            if (alvo == null) {
                return null;
            }
        
            if (letra.equals(alvo.palavra)) {
                return alvo;
            }
        
            for (Nodo filho : alvo.subtree) {
                Nodo resultado = searchNode(letra, filho);
                if (resultado != null) {
                    return resultado;
                }
            }
        
            return null;
        }

        public void add(Palavra palavra, String pai) {
            String palavraStr = palavra.getPalavra();
            Nodo ref = null;
    
            // Verifica se o pai é nulo
            if (pai == null) {
                ref = root;
                count++;
            } else {
                ref = searchNode(pai, root);
            }
    
            if (ref == null) {
                return;
            }
    
            // Percorre cada caractere da palavra
            Nodo nodoPai = ref;
            for (int i = 0; i < palavraStr.length(); i++) {
                String letra = Character.toString(palavraStr.charAt(i));
    
                // Verifica se o caractere já existe no nodo atual
                Nodo nodoAtual = null;
                //nodo filho é criado e se percorre a lista do nodo pai
                for (Nodo filho : nodoPai.subtree) {
                    if (filho.palavra.equals(letra)) {
                        //se ele estiver pressente se adiciona o pai ao nodo
                        nodoAtual = filho;
                        break;
                    }
                }
    
                // Se o caractere não existe, cria um novo nodo e adiciona ao nodo atual
                if (nodoAtual == null) {
                    nodoAtual = new Nodo(letra, nodoPai);
                    nodoPai.addSubtree(nodoAtual);
                    count++;
                }
    
                nodoPai = nodoAtual; // Atualiza o nodo pai para o próximo caractere
            }
}

        public boolean isExternal(String palavra){
            Nodo per = searchNode(palavra, root);

            if(per.getSubtreeSize()==0){
                return true;
            }
            return false;
        }
      
       
    public void geraDot(String nomeArquivo) {
        try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo))) {
            out.println("digraph ArvoreGramatical {");
            geraDotRecursivo(root, out);
            out.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void geraDotRecursivo(Nodo nodo, PrintWriter out) {
        out.println("\t\"" + nodo.palavra + "\";");

        for (Nodo filho : nodo.subtree) {
            out.println("\t\"" + nodo.palavra + "\" -> \"" + filho.palavra + "\";");
            geraDotRecursivo(filho, out);
        }
    }
}