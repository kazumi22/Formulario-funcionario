import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class FuncionarioDAO {
    private Connection con; 
    private  PreparedStatement st ;
    public boolean conectar(){
                try {
                Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/empresa","root","");
                return true;
    } catch (ClassNotFoundException | SQLException ex) {
            return false;
    }
}
    public int salvar(Funcionario e) {
      try {
          st = con.prepareStatement("Insert into funcionario values(?,?,?,?,?)");
          st.setString(1, e.getCodigo());
          st.setString(2,e.getNome());
          st.setString(3,e.getCargo());
          st.setString(4,e.getData());
          st.setDouble(5, e.getSalario());
          
          return st.executeUpdate(); // executa o comando  insert 
          
      } catch (SQLException ex) {
          if(ex.getErrorCode()==1062){
              return 1062; // código de erro: 1062 é duplicação de chave primária.
          } else{
              return 0;
          }
      }
    }

    public Funcionario consultar(String codigo ) {
        Statement st = null;
      try {
          st = con.createStatement();
          ResultSet rs = st.executeQuery("SELECT * FROM Funcionario WHERE código = '"+codigo+"'"); // nao colocar acento 
          if(rs.next()){
              Funcionario a;
              a = new Funcionario ();
              a.setCodigo(rs.getString(1));
              a.setNome(rs.getString(2));
              a.setCargo(rs.getString(3));
              a.setData(rs.getString(4));
              a.setSalario(rs.getDouble(5));
              return a;
          }else{
              return null;
          }
      } catch (SQLException ex) {
          return null;

    }
}
}
