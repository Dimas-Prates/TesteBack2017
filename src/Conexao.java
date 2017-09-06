import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

	private String usuario = "testeback2017";
	private String senha = "testeback2017!";
	private String banco = "testeback2017";
	private String server = "mssql6.gear.host";

	public String query;
	private Statement stmt;
	private Connection conexao;

	public boolean abrirConexao() {
		boolean situacao = false;

		// Verifica drive
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			situacao = true;
		} catch (Exception ex) {
			situacao = false;
			System.out.println("Erro no drive: " + ex);
		}

		// Verifica conexão com bd
		try {
			conexao = DriverManager.getConnection("jdbc:jtds:sqlserver://" + server + "/" + banco + "", usuario, senha);
			stmt = conexao.createStatement();
			situacao = true;
		} catch (SQLException ex) {
			situacao = false;
			System.out.println("Erro ao conectar com banco: " + ex);
		}

		return situacao;
	}

	public ResultSet consultaBD(String query) {
		ResultSet rs = null;
		if (abrirConexao()) {
			try {
				rs = stmt.executeQuery(query);
			} catch (Exception ex) {
				System.out.println(ex);
			}
		} else {
			System.out.print("Erro ao conectar no banco de dados.");

		}
		return rs;
	}

	public int executaBD(String query) {
		int resultado = -1;
		if (abrirConexao()) {
			try {
				resultado = stmt.executeUpdate(query);
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
		return resultado;
	}

	public void configurarConexao(String user, String password, String dataBase, String serverAddress) {
		usuario = user;
		senha = password;
		banco = dataBase;
		server = serverAddress;
	}

}
