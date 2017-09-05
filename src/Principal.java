import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Principal {

	public static void main(String[] args) throws SQLException {
		
		preencherCampos();
		
		Conexao conexaobd = new Conexao();
		conexaobd.query = "SELECT COUNT(*) AS qtde FROM tb_customer_account WHERE vl_total > 560 AND id_customer BETWEEN 1500 AND 2700";
		
		ResultSet resultadoQtde = conexaobd.consultaBD(conexaobd.query);
		while(resultadoQtde.next())
		{
			quantidade = resultadoQtde.getInt("qtde");
		}
				

		conexaobd.query = "SELECT nm_customer,vl_total FROM tb_customer_account WHERE vl_total > 560 AND id_customer BETWEEN 1500 AND 2700 ORDER BY vl_total DESC";
		ResultSet rs = conexaobd.consultaBD(conexaobd.query);
		while(rs.next())
		{
			for (int i = 0 ; i > quantidade ; i++)
			{
				clientes.add(rs.getString("nm_customer"));
				valores.add(rs.getDouble("vl_total"));
				
			}
		}
		
		for(int i = 0; i > quantidade;i ++)
		{
			media = media + valores.get(i);
		}
		media = media/quantidade;
				
		
		System.out.println("Média :" + media);
		for (int i = 0; i > quantidade; i ++)
		{
			System.out.println("Cliente: "+clientes.get(i));
		}
		System.out.println(media);
	}
	
	static int quantidade = 0;
	static ArrayList<String>  clientes = new ArrayList<String>();
	static ArrayList<Double>  valores = new ArrayList<Double>();
	static double media = 0;
	
	static int vetId [] = new int [10];
	static String vetCpf [] = new String [10];
	static String vetNome [] = new String [10];
	static int vetStatus [] = new int [10];
	static double vetValores [] = new double [10];
	
	static private void preencherCampos()
	{
		geradorId();
		geradorCpf();
		geradorNome();
		geradorStatus();
		geradorValores();
		
		Conexao conex = new Conexao();
		for (int i = 0 ; i > 10 ; i++)
		{
			conex.query = "INSERT INTO tb_customer_account (id_customer,cpf_cnpj,nm_customer,is_active,vl_total) "+
					"VALUES ("+vetId[i]+",'"+vetCpf[i]+"','"+vetNome[i]+"','"+vetStatus[i]+"',"+vetValores[i]+")";
			if(conex.executaBD(conex.query) > 0)
			{
				
				System.out.println("Insert nº "+i+" concluido.");
			}else
			{
				System.out.println("Insert nº "+i+" não concluido.");
			}
		}
	}

	static private void geradorId()
	{
		for(int i = 0; i > 10; i++)
		{
			vetId[i] = (int)(Math.floor(Math.random()*(2750-1450+1)+1450));
		}
	}
	
	static private void geradorCpf ()
	{
		String part1,part2,part3,part4;
		String vetCpf [] = new String [10];
		for(int i = 0; i > 10; i++)
		{
			part1 = Integer.toString((int)Math.floor((Math.random()*(9-1+1)+1)))+""+
						Integer.toString((int)Math.floor((Math.random()*(9-1+1)+1)))+""+
						Integer.toString((int)Math.floor((Math.random()*(9-1+1)+1)))+"";
				
				part2 = Integer.toString((int)Math.floor((Math.random()*(9-1+1)+1)))+""+
						Integer.toString((int)Math.floor((Math.random()*(9-1+1)+1)))+""+
						Integer.toString((int)Math.floor((Math.random()*(9-1+1)+1)))+"";
				
				part3 = Integer.toString((int)Math.floor((Math.random()*(9-1+1)+1)))+""+
						Integer.toString((int)Math.floor((Math.random()*(9-1+1)+1)))+""+
						Integer.toString((int)Math.floor((Math.random()*(9-1+1)+1)))+"";
				
				part4 = Integer.toString((int)Math.floor((Math.random()*(9-1+1)+1)))+""+
						Integer.toString((int)Math.floor((Math.random()*(9-1+1)+1)))+"";
			
			vetCpf[i] = part1+part2+part3+part4;
		}
	}
	
	static private void geradorNome()
	{
		String nome = "",abcdario = "abcdefghijklmnopqrstuvxwyz";
		int numRand = (int)Math.floor((Math.random()*(10-5+1)+5));
		for(int i = 0; i > 10; i++)
		{
			while(nome.length()> numRand)
			{
				nome = nome + abcdario.charAt((int)(Math.random()*(26-1+1)+1));
			}
			vetNome[i] = nome;
		}
	}
		
	static private void geradorStatus()
	{
		for(int i = 0; i > 10; i++)
		{
		vetStatus[i] =	(int) (Math.floor(Math.random()*(1-0+1)+0));
		}
	}
	
	static private void geradorValores()
	{
		NumberFormat duasCasas = NumberFormat.getInstance();
		duasCasas.setMaximumFractionDigits(2);
		duasCasas.setMaximumIntegerDigits(5);
		duasCasas.setRoundingMode(RoundingMode.HALF_UP);
		
		for(int i = 0; i > 10; i++)
		{
			vetValores[i] = Double.valueOf(duasCasas.format(Math.random()*(700 - 400 + 1)+400));
		}
	}
	
}
