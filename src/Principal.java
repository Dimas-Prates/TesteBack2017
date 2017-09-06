import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class Principal {

	public static void main(String[] args) throws SQLException {
		Geradores ger = new Principal().new Geradores();
		ger.preencherCampos();

		int quantidade = 0;
		double media = 0;
		String clientes = "";

		Conexao conexaobd = new Conexao();
		conexaobd.query = "SELECT COUNT(*) AS qtde FROM tb_customer_account WHERE vl_total > 560 AND id_customer BETWEEN 1500 AND 2700";

		ResultSet resultadoQtde = conexaobd.consultaBD(conexaobd.query);
		while (resultadoQtde.next()) {
			quantidade = resultadoQtde.getInt("qtde");
		}

		conexaobd.query = "SELECT nm_customer,vl_total FROM tb_customer_account WHERE vl_total > 560 AND id_customer BETWEEN 1500 AND 2700 ORDER BY vl_total DESC";
		ResultSet rs = conexaobd.consultaBD(conexaobd.query);
		while (rs.next()) {
			media = media + rs.getDouble("vl_total");

			clientes = clientes + ", " + rs.getString("nm_customer");
		}

		media = media / quantidade;
		DecimalFormat df = new DecimalFormat("0.00");

		System.out.println("Clientes (" + quantidade + "): " + clientes + ".");
		System.out.println("Média do saldo: " + df.format(media) + ".");
	}

	public class Geradores {

		private int vetId[] = new int[10];
		private long vetCpf[] = new long[10];
		private String vetNome[] = new String[10];
		private int vetStatus[] = new int[10];
		private double vetValores[] = new double[10];

		public void preencherCampos() throws SQLException {
			geradorId();
			geradorCpf();
			geradorNome();
			geradorStatus();
			geradorValores();

			Conexao conex = new Conexao();
			boolean vetInserts[] = new boolean[10];
			int concluido = 0, falhado = 0;
			for (int i = 0; i < 10; i++) {
				conex.query = "INSERT INTO tb_customer_account (id_customer,cpf_cnpj,nm_customer,is_active,vl_total) "
						+ "VALUES (" + vetId[i] + "," + vetCpf[i] + ",'" + vetNome[i] + "','" + vetInserts[i] + "',"
						+ vetValores[i] + ")";
				if (conex.executaBD(conex.query) > 0) {

					vetInserts[i] = true;
				} else {
					vetInserts[i] = false;
				}
			}

			for (int i = 0; i < 10; i++) {
				if (vetInserts[i] == true) {
					concluido++;
				} else {
					falhado++;
				}
			}

			conex.query = "SELECT COUNT(*) AS qtde FROM tb_customer_account";
			int totBD = 0;
			ResultSet resultadoQtde = conex.consultaBD(conex.query);
			while (resultadoQtde.next()) {
				totBD = resultadoQtde.getInt("qtde");
			}

			System.out.println(concluido + " insert(s) foram conluídos e " + falhado + " insert(s) falharam.");
			System.out.println("Total de registros no banco: " + totBD + ".");
			System.out.println("----------------------------------------------------");
		}

		private void geradorId() {
			for (int i = 0; i < 10; i++) {
				vetId[i] = (int) (Math.floor(Math.random() * (2800 - 1300 + 1) + 1300));
			}
		}

		private void geradorCpf() {
			String part1, part2, part3, part4;
			for (int i = 0; i < 10; i++) {
				part1 = (int) Math.floor((Math.random() * (9 - 1 + 1) + 1)) + ""
						+ (int) Math.floor((Math.random() * (9 - 1 + 1) + 1)) + ""
						+ (int) Math.floor((Math.random() * (9 - 1 + 1) + 1)) + "";

				part2 = (int) Math.floor((Math.random() * (9 - 1 + 1) + 1)) + ""
						+ (int) Math.floor((Math.random() * (9 - 1 + 1) + 1)) + ""
						+ (int) Math.floor((Math.random() * (9 - 1 + 1) + 1)) + "";

				part3 = (int) Math.floor((Math.random() * (9 - 1 + 1) + 1)) + ""
						+ (int) Math.floor((Math.random() * (9 - 1 + 1) + 1)) + ""
						+ (int) Math.floor((Math.random() * (9 - 1 + 1) + 1)) + "";

				part4 = (int) Math.floor((Math.random() * (9 - 1 + 1) + 1)) + ""
						+ (int) Math.floor((Math.random() * (9 - 1 + 1) + 1)) + "";

				vetCpf[i] = Long.parseLong(part1 + part2 + part3 + part4);
			}
		}

		private void geradorNome() {

			String nome = "", abcdario = "abcdefghijklmnopqrstuvxwyz";
			int numRand;

			for (int i = 0; i < 10; i++) {
				numRand = (int) Math.floor((Math.random() * (10 - 5 + 1) + 5));

				while (nome.length() < numRand) {
					nome = nome + abcdario.charAt((int) (Math.random() * (25 - 0 + 1) + 0));
				}
				vetNome[i] = nome;
				nome = "";
			}
		}

		private void geradorStatus() {
			for (int i = 0; i < 10; i++) {
				vetStatus[i] = (int) (Math.floor(Math.random() * (1 - 0 + 1) + 0));
			}
		}

		private void geradorValores() {
			DecimalFormat df = new DecimalFormat("0.00");
			for (int i = 0; i < 10; i++) {
				vetValores[i] = Double
						.parseDouble(df.format((Math.random() * (700 - 400 + 1) + 400)).replace(",", "."));
			}
		}

	}
}
