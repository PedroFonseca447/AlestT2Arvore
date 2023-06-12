import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class ArvoreGramatical{
    public int count;
    public int ajuda;
    //Nodo que armazena os caracteres da palavra
    //Em caso de ser o último caracter, ele armazena o significado da palavra
    //caso não seja o valor é definido como nulo, temos tbm a referencia ao pai
    //e a sublist de cada nodo.
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
        //ajuda a alterar o significado das ultimas letras
        //de uma palavra
        public void setSignificado(String p) {
            significado = p;
        }
    
    
        public Nodo getSubtree(int i) throws Exception {
            if (i < 0 || i >= subtree.size()) {
                throw new IndexOutOfBoundsException();
            }
            return subtree.get(i);
        }
        //ajuda a pegar os caracteres
        public String getPalavra() {
            return palavra;
        }
    }
         public Nodo root;

        public ArvoreGramatical(){
            this.count=0;
            this.root=null;
        }

        //classe que ajuda a adicionar o elemento "/" que será o root da palavra,
        //quando se passa a refêrencia de null o programa entende que esse será o pai da
        //árvore

        public boolean addRoot(String letra,String pai){
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


        //Essa classe serviu para ajudar a entender como funcionava a estruturação da árvore
        //inicialmente ele apenas da um print das letras que compoem a árvore em uma lista
        //adicionado e feito claro no caminhamento pré.

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

        //funciona para procurar a letra das palavras, na hora de adicionar tanto o root como
        //o resto dos nodos na "árvore"

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

        //o métoo addPalavra, primeiro recebe as palavras da classe Palavra.java junto de seu pai
        //que inicialmente é o root quando a árvore está vazia, e depois conforme vai preenchendo 
        //essa lógica vira para a letra anterior a que irá ser adicionada.




        public void addPalavra(Palavra palavra, String pai) {
            //faz isso para converter em string oq será adicionado
            String palavraStr = palavra.getPalavra();
            //pega o significado para adicionar ao elemento final.
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
    
           //primeiro se cria um nodo pai para saber o nodo anterior ao que será inserido;
           //depois se quebra a palavra com um CharAt para assim pegar letra por letra,
           //se verifica se o caminho já foi preenchido ou não e assim adiciona o nodo na arvore,
           //caso esse caminho já tenha existido o programa da um return
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
     
        // Nesta implementação, o número do nível é concatenado ao nome do nodo, separado por um underscore (_). Isso garante que cada nodo tenha um nome único com base no seu valor e no seu nível.

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

        //esse método é outro que no final não está sendo utilizado para compor os métodos finais principais porém,
        //ele serve como uma forma de entender como estava sendo preenchido o slot de significado, imprimindo no geral uma lista
        //de null com exceção de a letra final possuir todo o significado da palavra.
           
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
       
    //O método buscarPlavras recebe os caracteres da constulda do usuário, e de forma recursiva + caminhamento prévio
    //percorre a lista juntando as possiveis palavras que serão formadas através do que foi oferecido pelo
    //usuário, onde dependendo pode ser uma lista de várias palavras, ou a própria palavra especificada
    //para para de percorrer


    public LinkedList<String> buscarPalavras(String caracteres) {
        LinkedList<String> palavrasEncontradas = new LinkedList<>();
        buscarPalavrasRecursivo(root, caracteres, "", palavrasEncontradas);
        return palavrasEncontradas;
    }

    private void buscarPalavrasRecursivo(Nodo nodo, String caracteres, String prefixo, LinkedList<String> palavrasEncontradas) {
        if (nodo == null ) {
             return;
        }
                
    // Verifica se o prefixo atual mais a letra do nodo forma a sequência de caracteres desejada
    //aqui ele vai acumulando a string das possiveis opções
        String novaPalavra = prefixo + nodo.palavra;
        //aqui ele sabe que para parar deve ser igual ao que foi consultado, e estar no final de uma palavra
          if (novaPalavra.startsWith(caracteres)&&nodo.significado!=null) {
                palavrasEncontradas.add(novaPalavra);
            }
                
                // Percorre os nodos filhos
                for (Nodo filho : nodo.subtree) {
                    buscarPalavrasRecursivo(filho, caracteres, novaPalavra, palavrasEncontradas);
                }
            }
        

            //Se baseando na primeira proposta de percorrer a palavra, esse método trabalha da mesma forma, com a diferença que na lista
            //final ele adiciona o significado da palavra consultada, assim devolvendo uma lista em separado das palavras porém com o significado 
            //solicitado



        public LinkedList<String> bucasSignificado( String caracteres) {
           LinkedList<String> palavrasEncontradas = new LinkedList<>();
           bucasSignificadoRecursivo(root, caracteres, "", palavrasEncontradas);
           return palavrasEncontradas;
        }

        private void bucasSignificadoRecursivo(Nodo nodo, String caracteres, String prefixo, LinkedList<String> palavrasEncontradas) {
            if (nodo == null ) {
                return;
            }
                    
            // Verifica se o prefixo atual mais a letra do nodo forma a sequência de caracteres desejada
            String novaPalavra = prefixo + nodo.palavra;
            String significado = nodo.getSignificado();
            
            if (novaPalavra.startsWith(caracteres) && significado != null) {
                palavrasEncontradas.add(significado);
            }
            
            // Percorre os nodos filhos
            for (Nodo filho : nodo.subtree) {
                bucasSignificadoRecursivo(filho, caracteres, novaPalavra, palavrasEncontradas);
            }
        }        
}