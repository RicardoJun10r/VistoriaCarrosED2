package db.util.Tree;

import java.util.LinkedList;
import java.util.List;

import db.handlers.ArvoreVazia;
import db.handlers.VeiculoNaoEncontrado;

public class AvlTree<T> {
    
    private No<T> raiz;

    private List<T> lista = new LinkedList<>();

    private Integer size;

    public AvlTree() {
        this.raiz = null;
        this.size = 0;
    }

    public void insert(T valor, Integer chave){
        if(isEmpty()) this.raiz = new No<>(valor, chave);
        this.raiz = insert(raiz, valor, chave);
        this.raiz = this.FB(raiz);
        size++;
        System.out.println("Altura = " + altura(raiz) + " Quantidade de elementos = " + size);
    }

    private No<T> insert(No<T> folha, T valor, Integer chave){
        if(folha == null) folha = new No<T>(valor, chave);
        else if(chave > folha.getChave()) folha.setDir(insert(folha.getDir(), valor, chave));
        else if(chave < folha.getChave()) folha.setEsq(insert(folha.getEsq(), valor, chave));
        return folha;
    }

    public void remove(Integer chave){
        if(isEmpty()) throw new ArvoreVazia("Árvore vazia!");
        this.raiz = remove(raiz, chave);
        this.raiz = this.FB(raiz);
        size--;
        System.out.println("Altura = " + altura(raiz) + " Quantidade de elementos = " + size);
    }

    private No<T> remove(No<T> folha, Integer chave){
        if(folha == null) throw new VeiculoNaoEncontrado("Error: não é possível remover, veículo não encontrado!");
        else if(chave > folha.getChave()) folha.setDir(remove(folha.getDir(), chave));
        else if(chave < folha.getChave()) folha.setEsq(remove(folha.getEsq(), chave));
        else {
            
            if(ehFolha(folha)) folha = null;

            else if(soFolhaEsq(folha)){
                folha.setValor(folha.getEsq().getValor());
                folha.setEsq(null);
            } else if(soFolhaDir(folha)){
                folha.setValor(folha.getDir().getValor());
                folha.setDir(null);
            } else {
                No<T> aux = sucessor(folha);
                folha.setValor(aux.getValor());
                folha.setDir( remove(folha.getDir(), aux.getChave()) );
            }

        }

        return folha;

    }

    private No<T> sucessor(No<T> folha){
        No<T> index = folha.getDir();
        while(index != null){
            if(index.getEsq() != null) index = index.getEsq();
            else break;
        }
        return index;
    }

    public T buscar(Integer chave){

        if(isEmpty()) throw new ArvoreVazia("Árvore vazia!");
        else return buscar(raiz, chave).getValor();

    }

    private No<T> buscar(No<T> folha, Integer chave){
        if(folha == null) throw new VeiculoNaoEncontrado("Error: não é possível remover, veículo não encontrado!");
        else if(chave > folha.getChave()) return buscar(folha.getDir(), chave);
        else if(chave < folha.getChave()) return buscar(folha.getEsq(), chave);
        else return folha;
    }

    public void atualizarValor(T novoValor, Integer chave){
        if(isEmpty()) throw new ArvoreVazia("Árvore vazia!");
        this.raiz = atualizarValor(raiz, novoValor, chave);
    }

    private No<T> atualizarValor(No<T> folha, T novoValor, Integer chave){
        if(folha == null) return null;
        else if(chave > folha.getChave()) return atualizarValor(folha.getDir(), novoValor, chave);
        else if(chave < folha.getChave()) return atualizarValor(folha.getEsq(), novoValor, chave);
        else {
            folha.setValor(novoValor);
            return folha;
        }
    }

    public List<T> listar(){
        this.lista.clear();
        emOrdem();
        return this.lista;
    }

    public void emOrdem(){
        if(isEmpty()) throw new ArvoreVazia("Árvore vazia!");
        else emOrdem(raiz);
    }

    private void emOrdem(No<T> folha){
        if(folha != null){
            emOrdem(folha.getEsq());
            this.lista.add(folha.getValor());
            System.out.println("ID = " + folha.getChave() + " FB = " + folha.getFB());
            emOrdem(folha.getDir());
        }
    }

    public void preOrdem(){
        if(isEmpty()) System.out.println("Vazio");
        else preOrdem(raiz);
    }

    private void preOrdem(No<T> folha){
        if(folha != null){
            System.out.println("Valor = " + folha.getValor() + " FB = " + folha.getFB());
            preOrdem(folha.getEsq());
            preOrdem(folha.getDir());
        }
    }

    public void posOrdem(){
        if(isEmpty()) System.out.println("Vazio");
        else posOrdem(raiz);
    }

    private void posOrdem(No<T> folha){
        if(folha != null){
            posOrdem(folha.getEsq());
            posOrdem(folha.getDir());
            System.out.println("Valor = " + folha.getValor() + " FB = " + folha.getFB());
        }
    }

    public Integer altura(){
        if(isEmpty()) return -1;
        else return altura(raiz);
    }

    private Integer altura(No<T> folha){
        if(folha == null) return 0;
        else if(altura(folha.getEsq()) > altura(folha.getDir())) return 1 + altura(folha.getEsq());
        else return 1 + altura(folha.getDir());
    }

    private No<T> FB(No<T> folha){
        if(folha != null){
            calcularFB(folha);
            calcularFB(folha.getEsq());
            calcularFB(folha.getDir());
            folha.setEsq( FB(folha.getEsq()) );
            folha.setDir( FB(folha.getDir()) );

            // Rotação Simples a esquerda
            if(folha.getFB() < -1 && folha.getDir().getFB() <= 0){
                System.out.println("Rotação Simples a esquerda");
                return rotacaoEsquerdaSimples(folha);
            }

            // Rotação Simples a direita
            if(folha.getFB() > 1 && folha.getEsq().getFB() >= 0){
                System.out.println("Rotação Simples a direita");
                return rotacaoDireitaSimples(folha);
            }

            // Rotação Dupla a esquerda
            if(folha.getFB() < -1 && folha.getDir().getFB() > 0){
                System.out.println("Rotação Dupla a esquerda");
                folha.setDir(rotacaoDireitaSimples(folha.getDir()));
                return rotacaoEsquerdaSimples(folha);
            }

            // Rotação Dupla a direita
            if(folha.getFB() > 1 && folha.getEsq().getFB() < 0){
                System.out.println("Rotação Dupla a direita");
                folha.setEsq(rotacaoEsquerdaSimples(folha.getEsq()));
                return rotacaoDireitaSimples(folha);
            }

        }
        return folha;
    }

    private No<T> rotacaoEsquerdaSimples(No<T> folha){
        No<T> aux = folha.getDir();
        folha.setDir(aux.getEsq());
        aux.setEsq(folha);
        calcularFB(folha);
        calcularFB(aux);
        return aux;
    }

    private No<T> rotacaoDireitaSimples(No<T> folha){
        No<T> aux = folha.getEsq();
        folha.setEsq(aux.getDir());
        aux.setDir(folha);
        calcularFB(folha);
        calcularFB(aux);
        return aux;
    }

    private void calcularFB(No<T> folha){
        if(folha != null){
            if(ehFolha(folha)) folha.setFB(0);
            else folha.setFB( altura(folha.getEsq()) - altura(folha.getDir()) );
        }

    }

    private Boolean ehFolha(No<T> folha){
        if(folha.getEsq() == null && folha.getDir() == null) return true;
        else return false;
    }

    private Boolean soFolhaEsq(No<T> folha){
        if(folha.getEsq() != null && folha.getDir() == null) return true;
        else return false;
    }

    private Boolean soFolhaDir(No<T> folha){
        if(folha.getEsq() == null && folha.getDir() != null) return true;
        else return false;
    }

    private Boolean isEmpty(){
        if(this.raiz == null) return true;
        else return false;
    }

    public Integer quantidade(){
        return this.size;
    }

}
