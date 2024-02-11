package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Factory.ConnectionFactory;

public class Usuarios {
	
	private String nome;
	private String senha;
	
	public Usuarios(String nome, String senha) {
		this.nome = nome;
		this.senha = senha;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public static boolean validarUsuario(String nome, String senha) {
		ConnectionFactory factory = new ConnectionFactory();
		try (Connection connection = factory.iniciaConexao();
		     PreparedStatement statement = connection.prepareStatement("SELECT * FROM usuarios WHERE nome=? AND senha=?")) {
			statement.setString(1, nome);
			statement.setString(2, senha);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				return resultSet.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
