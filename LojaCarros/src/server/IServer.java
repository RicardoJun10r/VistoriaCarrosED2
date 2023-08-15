package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import db.model.Veiculo;

public interface IServer extends Remote {
    void adicionar(Veiculo veiculo) throws RemoteException;
    String remover(String renavam) throws RemoteException;
    String listar() throws RemoteException;
    Veiculo buscar(String renavam) throws RemoteException;
    void atualizar(Veiculo novo, String renavam) throws RemoteException;
    Integer quantidadeDeCarros() throws RemoteException;
}
