import java.util.LinkedList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class ArvoreGramatical{
 
    public int count;
    //count para o método Dot
    public int ajuda;
 
    /**
     * Nodo que armazena os caracteres da palavra.
     * Em caso de ser o último caracter, ele armazena o significado da palavra
     */
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
     /**
     * Parte para alterar o significado nos nodos, facilitando a operaçãos junto
     * do uso do set.
     */
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


      /**
     * Retorna o elemento de uma determinada posicao da lista.
     * @param letra recebe o "/" que iniciará a arvpre
     * @param pai recebe um null, que indica o inicio da arvore 
     * @return true se não tinha raiz e false caso ja exista
     */
        public boolean addRoot(String letra,String pai){
            Nodo aux = new Nodo(letra,null);
            
            if(root!=null){
                return false;
            }

            root = aux;
            count++;
            return true;          
        }


       

    /**
     * Retorna uma lista com todos as letras da árvore em uma ordem de 
     * caminhamento pre-fixado.
     * @return lista com os elementos da arvore na ordem do caminhamento pre-fixado
     */  
        public LinkedList<String> possitionsPre() throws Exception {
            LinkedList<String> lista = new LinkedList<>();
            possitionsPre(root, lista);
            return lista;
        }
        private void possitionsPre(Nodo aux,LinkedList<String> lista) throws Exception {
           if(aux!=null){
          
            lista.add(aux.palavra);
        
            for(int i=0;i<aux.getSubtreeSize();i++){
                possitionsPre(aux.getSubtree(i), lista);
            }
           }
        }
        // Procura pela letra anterior, seguindo uma referencia do alvo
        // caminhamento pre-fixado. Retorna a referencia
        // para o nodo no qual "elem" esta armazenado.
        // Se não encontrar "elem", ele retorna NULL.

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

    

             /**
                //o métoo addPalavra, primeiro recebe as palavras da classe Palavra.java junto de seu pai
                //que inicialmente é o root quando a árvore está vazia, e depois conforme vai preenchendo 
                //essa lógica, o pai vira a letra anterior em relação a que irá ser adicionada.
             * 
             * Adiciona palavra(Na classe eu explico novamente, nela tem um Char que quebra essa palavra)
             *  como filho de pai
             * @param palavra elemento a ser adicionado na arvore.
             * @param father pai do elemento a ser adicionado.
             */


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
                //aqui verifica se o pai do nodo adicionado já existe na árvore
                //e se a árvore ja esta preenchida com o root
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
             //nodo atual é criado para percorrer a subArvore do nodo pai e para cada filho
             //verificar se a sua letra é igual a atual;
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
                 //aqui funciona para caso no for seja indicado a ultima letra
                 //se adiciona o signnificado da palavra, assim mostrando
                 //qual é o ultimo nodo
                    if(palavraStr.length()==i+1){
                        nodoAtual.setSignificado(nrt);
                        }
                    nodoPai.addSubtree(nodoAtual);
                    count++;                   
                }
                       
    
                nodoPai = nodoAtual; // Atualiza o nodo pai para o próximo caractere
            }
}
     
       //O gera dot foi feito baseado no que vi no material somado a consultas para entendr o formato do arquivo, realmente é meio confuso a fora como ele funciona,
       //nesse caso ele só passou a printar da forma certa quando se implementou nível + numeração para diferenciar cada nodo na hora da impressão de uma palavra
       //no arvore.dot da para ver como esta feito o dicionario
       // Gera uma saida no formato DOT
       // http://www.webgraphviz.com/
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

        //Esse método é outro que no final não está sendo utilizado para compor os métodos finais principais porém,
        //ele serve como uma forma de entender como estava sendo preenchido o slot de significado, imprimindo no geral uma lista
        //de null com exceção de a letra final que possui todo o significado da palavra.
           
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
       


    /** 
     * // O método buscarPlavras recebe os caracteres da consulta do usuário, e usando o caminhamento prévio
       //percorre a lista juntando as possiveis palavras que serão formadas através do que foi oferecido pelo
       //usuário, onde dependendo pode ser uma lista de várias palavras, ou a própria palavra especificada
       //para para de percorrer
       @param caracteres recebe os caracteres que o usuario da aplicação irá repassar na App, para consultar na arvore
     * Retorna uma lista com todos as palavras da árvore que possuam as inicias referenciadas pelo String caracteres em uma ordem de 
     * caminhamento pre-fixado.
     * @return lista com as palavras da arvore na ordem do caminhamento pre-fixado
     */  



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

/** 
     * // O método bucasSignificado recebe os caracteres da consulta do usuário, e usando o caminhamento prévio
       //percorre a lista juntando as possiveis palavras que serão formadas. Nesse caso, ele não necessita de retornar uma lista já que é necessário,
       //apenas um único significado referenciado por quem está solicitando.
       //o significado fica armazenado no fim das palavras no último nodo delas.
       @param caracteres recebe os caracteres que o usuario da aplicação irá repassar na App, para consultar na arvore
     * Retorna uma lista com o significado da palavra procurada.
     * @return lista com os siginificados das palavras na arvore usando a ordem do caminhamento pre-fixado
     */  

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
            //pega o significado da palavra conforme é formada na váriavel anterior
            //até chegar a signficado !=null ai ja para a recursão
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
