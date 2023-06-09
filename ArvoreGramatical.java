import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class ArvoreGramatical{
    public int count;
    public int ajuda;
    public class Nodo {
        public Nodo father;
        public String palavra;
        public String significado;
        public LinkedList<Nodo> subtree;
    
    
        public Nodo(String p,String sgn) {
            palavra = p;
            father = null;
            subtree = new LinkedList<>();
            significado = sgn;
        }
    
        public void addSubtree(Nodo entrada) {
            subtree.add(entrada);
            entrada.father = this; // Atualiza a referência do pai no nodo filho
        }
        public int getSubtreeSize() {
            return subtree.size();
        }
        public String getSignificado(){
            return significado;
        }
    
        public void setSignificado(String p) {
            significado = p;
        }
    
    
        public Nodo getSubtree(int i) throws Exception {
            if (i < 0 || i >= subtree.size()) {
                throw new IndexOutOfBoundsException();
            }
            return subtree.get(i);
        }

        public String getPalavra() {
            return palavra;
        }
    }
         public Nodo root;

        public ArvoreGramatical(){
            this.count=0;
            this.root=null;
        }

        public boolean add(String letra,String pai){
            Nodo aux = new Nodo(letra,null);
            
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

        public void addPalavra(Palavra palavra, String pai) {
            String palavraStr = palavra.getPalavra();
            String nrt = palavra.getSignificado();
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
            //antes criar uma forma de ser o ultimo elemento da palavra
            //e setar um null para parar de percorrer
            Nodo nodoPai = ref;
            for (int i = 0; i < palavraStr.length(); i++) {
                String letra = Character.toString(palavraStr.charAt(i));
    
                // Verifica se o caractere já existe no nodo atual
                Nodo nodoAtual = null;
                
               // nodo filho é criado e se percorre a lista do nodo pai
                for (Nodo filho : nodoPai.subtree) {
                    if (filho.palavra.equals(letra)) {
                        //se ele estiver pressente se adiciona o pai ao nodo
                        nodoAtual = filho;
                        break;
                    }
                }
    
                // Se o caractere não existe, cria um novo nodo e adiciona ao nodo atual
                if (nodoAtual == null) {
                    nodoAtual = new Nodo(letra,null);
                    if(palavraStr.length()==i+1){
                        nodoAtual.setSignificado(nrt);
                        }
                    nodoPai.addSubtree(nodoAtual);
                    count++;                   
                }
                       
    
                nodoPai = nodoAtual; // Atualiza o nodo pai para o próximo caractere
            }
}
     
         //         Nesta implementação, o número do nível é concatenado ao nome do nodo, separado por um underscore (_). Isso garante que cada nodo tenha um nome único com base no seu valor e no seu nível.

        // Ao imprimir o nodo no formato DOT, o nome do nodo é atualizado para incluir o número do nível correspondente. Além disso, ao criar uma aresta para o filho, o nome do nodo do filho também é atualizado com o número do próximo nível.

        // Dessa forma, cada nodo terá um nome exclusivo com base no seu valor e no seu nível correspondente, criando a distinção visual entre os nodos em diferentes níveis da árvore no arquivo DOT gerado.

        // Por exemplo, para a palavra "correr", o primeiro "r" será do terceiro nível e o segundo "r" será do quarto nível, como você mencionou.
       
        public void geraDot(String nomeArquivo) {
            try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo))) {
                out.println("digraph ArvoreGramatical {");
                geraDotRecursivo(root, out, 0);
                out.println("}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        private void geraDotRecursivo(Nodo nodo, PrintWriter out, int nivel) {
            String espacos = "";
            for (int i = 0; i < nivel; i++) {
                espacos += "\t";
            }
            
            String nomeNodo = nodo.palavra + "_" + ajuda;
            ajuda++;
            
            out.println(espacos + "\"" + nomeNodo + "\";");
            
            for (Nodo filho : nodo.subtree) {
                out.println(espacos + "\"" + nomeNodo + "\" -> \""+ filho.palavra + "_" + ajuda + "\";");
                geraDotRecursivo(filho, out, nivel + 1);
            }
        }
           
    public LinkedList<String> printSignificados() throws Exception {
       LinkedList<String> lista = new LinkedList<>();
       printSignificadosRecursivo(root,lista);
       return lista;

    }
    
    private void printSignificadosRecursivo(Nodo aux,LinkedList<String> lista) throws Exception {
        if(aux!=null){
            //em tese primeiro visitaria o nodo pai
            lista.add(aux.significado);
            //depois visitaria o filho usando os aux para saber se viu
            for(int i=0;i<aux.getSubtreeSize();i++){
                printSignificadosRecursivo(aux.getSubtree(i), lista);
            }
           }
    }

    public LinkedList<String> findWordsWithPrefix(String prefix) {
        LinkedList<String> words = new LinkedList<>();
        Nodo lastPrefixNode = getLastPrefixNode(prefix, root);
        collectWordsFromNode(lastPrefixNode, words);
        return words;
    }
    
    private Nodo getLastPrefixNode(String prefix, Nodo node) {
        if (node == null || prefix.isEmpty()) {
            return node;
        }
    
        String firstChar = prefix.substring(0, 1);
        String remainingPrefix = prefix.substring(1);
    
        for (Nodo child : node.subtree) {
            if (child.getPalavra().equals(firstChar)) {
                return getLastPrefixNode(remainingPrefix, child);
            }
        }
    
        return null;
    }
    
    private void collectWordsFromNode(Nodo node, LinkedList<String> words) {
        if (node == null) {
            return;
        }
    
        if (node.getSignificado() != null) {
            words.add(node.getSignificado());
        }
    
        for (Nodo child : node.subtree) {
            collectWordsFromNode(child, words);
        }
    }
            public LinkedList<String> percorrerArvorePalavras(ArvoreGramatical arvore) {
            LinkedList<String> palavras = new LinkedList<>();
            percorrerArvorePalavrasRecursivo(arvore.root, "", palavras, false);
            return palavras;
        }

        private void percorrerArvorePalavrasRecursivo(ArvoreGramatical.Nodo nodo, String palavraAtual, LinkedList<String> palavras, boolean ignoreRaiz) {
            // Verifica se o nodo atual é a raiz e se deve ser ignorado
            if (ignoreRaiz && nodo == null) {
                return;
            }
            
            // Concatena a palavra atual com a letra do nodo, exceto para a raiz "/"
            String novaPalavra = (ignoreRaiz && nodo == null) ? palavraAtual : palavraAtual + nodo.palavra;

            // Verifica se o nodo atual forma uma palavra completa e não contém "/"
            if (nodo != null && nodo.getSignificado() != null && !nodo.getPalavra().equals("/")) {
                palavras.add(novaPalavra);
            }
            
            // Percorre os nodos filhos
            if (nodo != null) {
                for (ArvoreGramatical.Nodo filho : nodo.subtree) {
                    percorrerArvorePalavrasRecursivo(filho, novaPalavra, palavras, true);
                }
            }
        }

}