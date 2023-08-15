package client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Scanner;

import db.model.Condutor;
import db.model.Veiculo;
import server.IServer;

public class Cliente {

    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws UnknownHostException, IOException, NotBoundException {
        String objName = "rmi://localhost:1099/lojaCarros";
        IServer veiculos = (IServer) Naming.lookup(objName);
        int opcao = 1;

        carregarCarros(veiculos);

        try {
            do {
                menu();
                opcao = scan.nextInt();
                processOption(opcao, veiculos);
            } while (opcao != 0);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void carregarCarros(IServer veiculoService) throws RemoteException{

        for(int i = 0; i < 50; i++){
            String renavam = String.valueOf(i) + "0015468";
            veiculoService.adicionar(new Veiculo(renavam, "Kwid" + i, "Renault" + i, "QRT12" + i, LocalDate.now(), new Condutor("Roberto", "041646" + i)));
        }

    }

    static void menu(){
        System.out.print("\n[0] - sair\n[1] - adicionar\n[2] - remover\n[3] - listar\n[4] - buscar\n[5] - atualizar\n[6] - quantidade de carros\nOpção: [_]\b\b");
    }

    static void processOption(int opcao , IServer veiculos) throws RemoteException{
        try {
            switch(opcao){
            case 0:
            {
                System.out.println("Tchau!");
                break;
            }
            case 1:
            {
                String renavam, nome, modelo, placa, condutor, cpf;
                System.out.println("Renavam:");
                renavam = scan.next();
                scan.nextLine();
                System.out.println("Nome do carro:");
                nome = scan.next();
                System.out.println("Modelo do carro:");
                modelo = scan.next();
                System.out.println("Placa do carro:");
                placa = scan.next();
                scan.nextLine();
                System.out.println("Data Fabricação do carro( ex: ANO-MES-DIA ): ");
                LocalDate data = LocalDate.parse(scan.next());
                scan.nextLine();
                System.out.println("Nome do condutor:");
                condutor = scan.nextLine();
                System.out.println("CPF do condutor:");
                cpf = scan.next();
                veiculos.adicionar(new Veiculo(renavam, nome, modelo, placa, data, new Condutor(condutor, cpf)));
                break;
            }
            case 2:
            {
                String renavam;
                System.out.println("Renavam:");
                renavam = scan.next();
                System.out.println(veiculos.remover(renavam));
                break;
            }
            case 3:
            {
                System.out.println("\nListando");
                System.out.println(veiculos.listar());
                break;
            }
            case 4:
            {
                String renavam;
                System.out.println("Renavam:");
                renavam = scan.next();
                Veiculo veiculo = veiculos.buscar(renavam);
                System.out.println(veiculo.toString());
                break;
            }
            case 5:
            {
                String renavam, nome, modelo, placa, condutor, cpf;
                System.out.println("Renavam do veículo a atualizar:");
                renavam = scan.next();
                System.out.println("Nome do carro ou '*' (vazio):");
                nome = scan.next();
                System.out.println("Modelo do carro ou '*' (vazio):");
                modelo = scan.next();
                System.out.println("Placa do carro ou '*' (vazio):");
                placa = scan.next();
                scan.nextLine();
                System.out.println("Data Fabricação do carro: ");
                LocalDate data = LocalDate.parse(scan.nextLine());
                scan.nextLine();
                System.out.println("Nome do condutor ou '*' (vazio):");
                condutor = scan.nextLine();
                System.out.println("CPF do condutor ou '*' (vazio):");
                cpf = scan.next();
                veiculos.atualizar(new Veiculo(renavam, nome, modelo, placa, data, new Condutor(condutor, cpf)), renavam);
                break;
            }
            case 6:
            {
                System.out.println("Existem: " + veiculos.quantidadeDeCarros() + " carros cadastrados");
                break;
            }
            default:
                System.out.println("Opção inexistente!!!");
                break;
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
