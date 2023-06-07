import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class ArvoreGramatical{
    public int count;
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
   
        public boolean addRoot(String elemento){
            if(root!=null){
                return false;
            }
            root = new Nodo(elemento,null);
            count++;
            return true;
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
                
                // Percorre a lista de subnodos do nodo pai
                for (Nodo filho : nodoPai.subtree) {
                    if (filho.palavra.equals(letra)) {
                        // O caractere já existe, portanto, define o nodo atual e continua para o próximo caractere
                        nodoAtual = filho;
                        break;
                    }
                }
        
                if (nodoAtual == null) {
                    // Se o caractere não existe, cria um novo nodo e adiciona ao nodo atual
                    nodoAtual = new Nodo(letra, null);
                    nodoPai.addSubtree(nodoAtual);
                    count++;
                }
        
                // Verifica se é o último caractere da palavra
                if (i == palavraStr.length() - 1) {
                    // Define o significado apenas para o último nodo da palavra
                    nodoAtual.setSignificado(palavraStr);
                }
        
                nodoPai = nodoAtual; // Atualiza o nodo pai para o próximo caractere
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
    }
   
   
   
   

    