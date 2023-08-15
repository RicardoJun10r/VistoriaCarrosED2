package db.util.Tree;

public class No<T> {
 
    private T valor;

    private Integer chave;

    private Integer FB;
    
    private No<T> esq;
    
    private No<T> dir;
    
    public No(T valor, Integer id) {
        this.chave = id;
        this.valor = valor;
        this.FB = 0;
        this.dir = null;
        this.esq = null;
    }

    public Integer getChave() {
        return chave;
    }

    public void setChave(Integer chave) {
        this.chave = chave;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public Integer getFB() {
        return FB;
    }

    public void setFB(Integer fB) {
        FB = fB;
    }

    public No<T> getEsq() {
        return esq;
    }

    public void setEsq(No<T> esq) {
        this.esq = esq;
    }

    public No<T> getDir() {
        return dir;
    }

    public void setDir(No<T> dir) {
        this.dir = dir;
    }
    
}
