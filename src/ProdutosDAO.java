/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement st;
    ResultSet rs;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public boolean conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uc11", "root", "assiral12");
            System.out.println("Conectado");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return false;
        }
    }

    public void cadastrarProduto(ProdutosDTO produto) {

        try {
            int status;
            st = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES(?,?,?)");
            st.setString(1, produto.getNome());
            st.setInt(2, produto.getValor());
            st.setString(3, produto.getStatus());
            status = st.executeUpdate();
            System.out.println(status);
            if (status == 1) {
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar o produto.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na SQL");
            System.out.println(ex);
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        try {
            st = conn.prepareStatement("SELECT * FROM produtos");
            rs = st.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));

                listagem.add(produto);
                System.out.println(produto);
            }
            return listagem;

        } catch (Exception e) {
            return null;
        }

    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        try {
            st = conn.prepareStatement("SELECT * FROM produtos WHERE status = 'Vendido'");
            rs = st.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));

                listagem.add(produto);
                System.out.println(produto);
            }
            return listagem;

        } catch (Exception e) {
            return null;
        }

    }
    
    public void atualizar(int id) {

        try {
            st = conn.prepareStatement("UPDATE produtos SET status = 'Vendido' where id = ?");
            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode());
        }
    }

}
