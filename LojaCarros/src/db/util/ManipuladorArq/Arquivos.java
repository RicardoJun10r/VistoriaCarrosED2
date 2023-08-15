package db.util.ManipuladorArq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Arquivos {

    public Arquivos(){}

    public void escritor(String path, String texto) throws IOException 
	{
		BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(path) );
		bufferedWriter.append(texto);
		bufferedWriter.close();
	}

    public String leitor(String path) throws IOException 
	{
		BufferedReader bufferedReader = new BufferedReader( new FileReader(path) );
		StringBuffer sbResult = new StringBuffer();
		String linha = "";
		
		while (linha != null) 
		{
			sbResult.append(linha + "\n");
			linha = bufferedReader.readLine();
		}
		bufferedReader.close();
		
		return sbResult.toString();
	}

	public void escritorCsv(String path, String texto) throws IOException 
	{
		BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(path) );
		bufferedWriter.append(texto);
		bufferedWriter.close();
	}

	public String leitorCsv(String path) throws IOException 
	{
		BufferedReader bufferedReader = new BufferedReader( new FileReader(path) );
		
		StringBuffer sbResult = new StringBuffer();
		String linha = "";
		
		while (linha != null) 
		{
			sbResult.append(linha + "\n");
			linha = bufferedReader.readLine();
			
			if(linha != null) {
				String[] parts = linha.split(";");
				for (int i = 0; i < parts.length; i++) {
					System.out.print("["+parts[i]+"] ");
				}
			}

		}
		bufferedReader.close();
		
		return sbResult.toString();
	}

}
