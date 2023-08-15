package db.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;

import db.handlers.ArvoreVazia;
import db.model.Veiculo;
import db.util.Tree.AvlTree;
import server.IServer;

public class VeiculoService extends UnicastRemoteObject implements IServer {

    private AvlTree<Veiculo> avlTree;

    public VeiculoService() throws RemoteException{
        this.avlTree = new AvlTree<>();
    }

    private int hashRenavam(String renavam){
        return Integer.parseInt(renavam);
    }

    public void adicionar(Veiculo veiculo){
        int chave = hashRenavam(veiculo.getRenavam());
        this.avlTree.insert(veiculo, chave);
    }

    public String remover(String renavam){

        try {
            this.avlTree.remove(hashRenavam(renavam));
            return "Removido [ " + renavam + " ]";
        } catch (ArvoreVazia e) {
            e.printStackTrace();
        }

        return "Erro!";
        
    }

    public String listar(){
        List<Veiculo> lista = this.avlTree.listar();
        Iterator<Veiculo> iterator = lista.iterator();
        String res = "";
        int cont = 1;
        while(iterator.hasNext()){
            Veiculo index = iterator.next();
            if(index != null){
                res += "\nCarro: " + cont + "°\n" 
                + "\nRenavam: " + index.getRenavam() 
                + "\nNome: " + index.getNome() 
                + "\nModelo: " + index.getModelo() 
                + "\nPlaca: " + index.getPlaca() 
                + "\nAno: " + index.getDataFabricacao() 
                + "\nCondutor: " + index.getCondutor().getNome()
                + "\nCPF do condutor: " + index.getCondutor().getCpf() + "\n";
                cont++;
            } else break;
        }
        return res;
    }

    public Veiculo buscar(String renavam){
        return this.avlTree.buscar(hashRenavam(renavam));
    }

    public void atualizar(Veiculo novo, String renavam){
        int chave = hashRenavam(renavam);
        Veiculo veiculo_velho = this.avlTree.buscar(chave);
        if(attCampos(novo, veiculo_velho)){
            this.avlTree.atualizarValor(veiculo_velho, chave);
        }
    }

    private Boolean attCampos(Veiculo novo, Veiculo velho){

        boolean hasAtt = false;

        if(novo.getNome() != null && !novo.getNome().isEmpty() && !novo.getNome().equalsIgnoreCase("*")){
            velho.setNome(novo.getNome());
            hasAtt = true;
        }
        if(novo.getModelo() != null && !novo.getModelo().isEmpty() && !novo.getModelo().equalsIgnoreCase("*")){
            velho.setModelo(novo.getModelo());
            hasAtt = true;
        }
        if(novo.getPlaca() != null && !novo.getPlaca().isEmpty() && !novo.getPlaca().equalsIgnoreCase("*")){
            velho.setPlaca(novo.getPlaca());
            hasAtt = true;
        }
        if(novo.getDataFabricacao() != null && !novo.getDataFabricacao().equals("*")){
            velho.setDataFabricacao(novo.getDataFabricacao());
            hasAtt = true;
        }
        if(novo.getCondutor().getNome() != null && !novo.getCondutor().getNome().isEmpty() && !novo.getCondutor().getNome().equalsIgnoreCase("*")){
            velho.getCondutor().setNome(novo.getCondutor().getNome());
            hasAtt = true;
        }
        if(novo.getCondutor().getCpf() != null && !novo.getCondutor().getCpf().isEmpty() && !novo.getCondutor().getCpf().equalsIgnoreCase("*")){
            velho.getCondutor().setCpf(novo.getCondutor().getCpf());
            hasAtt = true;
        }

        return hasAtt;
        
    }

    public Integer quantidadeDeCarros(){
        return this.avlTree.quantidade();
    }

}
