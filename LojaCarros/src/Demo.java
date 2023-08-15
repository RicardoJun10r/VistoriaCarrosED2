import java.rmi.RemoteException;
import java.time.LocalDate;

import db.model.Condutor;
import db.model.Veiculo;
import db.service.VeiculoService;

public class Demo {
    public static void main(String[] args) throws RemoteException {
        
        VeiculoService veiculoService = new VeiculoService();

        veiculoService.adicionar(new Veiculo("25", "null", "null", "null", LocalDate.now(), new Condutor("null", "null")));

        System.out.println( veiculoService.listar() );

        veiculoService.remover("25");

        System.out.println( veiculoService.listar() );
        
    }
}
