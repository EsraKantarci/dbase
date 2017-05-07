/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomatakip;

import com.sun.rowset.CachedRowSetImpl;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author n
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    private static Connection conn=null;
    private static Statement stmt;
    private static PreparedStatement prprdstmt;
    
    private int duzenlenecekid = 0;
    private int suankidurum = 0;
   
    @FXML
    private Button search;
    @FXML
    private Button seeList;
    @FXML
    private Button addNew;
    @FXML
    private Button update;
          
    @FXML
    private Label aciklama;
    @FXML
    private TextField tc; 
    @FXML
    private TextField ad;
    @FXML
    private TextField soyad;
    @FXML
    private TextField uni;
    @FXML
    private TextField ikametgah;
    @FXML
    private TextField kararNo;
    @FXML
    private TextField kararTarih;
    @FXML
    private MenuButton durum;
    @FXML
    private TextField ara;
    @FXML
    private TableView tableview;   
    @FXML
    private TableColumn<Person, Integer> id;  
    @FXML
    private TableColumn<Person, String> tctb;
    @FXML
    private TableColumn<Person, String> adtb;
    @FXML
    private TableColumn<Person, String> soyadtb;
    @FXML
    private TableColumn<Person, String> unitb;
    @FXML
    private TableColumn<Person, String> ikametgahtb;
    @FXML
    private TableColumn<Person, String> kararnotb;
    @FXML
    private TableColumn<Person, String> karartarihtb;
    @FXML
    private TableColumn<Person, String> durumtb;
    
    
    
    
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    // jdbc:mysql://hostname:port/database?useUnicode=true&characterEncoding=utf8
    private static final String URL = "jdbc:mysql://localhost/diplomatakip";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    
    // Bağlantı kısmı
    public static void dbConnect() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            
        } 
        
        catch (SQLException e) {
            System.out.println("Bağlantı kurulamadı" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    
 
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("");
        label.setText("");
    }
    
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(queryStmt);
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return crs;
    }
    
    private static ObservableList<Person> getPersonList(ResultSet rs) throws SQLException, ClassNotFoundException {
       
        ObservableList<Person> perList = FXCollections.observableArrayList();
 
        while (rs.next()) {
            Person per = new Person();
            per.setid(rs.getInt("id"));
            per.settc(rs.getString("tc"));
            per.setad(rs.getString("ad"));
            per.setsoyad(rs.getString("soyad"));
            per.setuni(rs.getString("uni"));
            per.setPop(rs.getString("ikametgah"));
            per.setDec_No(rs.getString("kayitno"));
            per.setkayittarihi(rs.getString("kayittarihi"));
            per.setProcess(rs.getInt("durum"));
           
            perList.add(per);
        }
      
        return perList;
    }
    public void addNew(){
             String updateStmt ="INSERT INTO kayitlar(tc, ad, soyad, uni, ikametgah, kayitno,kayittarihi,durum)\n" +
                        "VALUES ('"+ tc.getText() + "','" + ad.getText() + "', '"+soyad.getText()+"','"+uni.getText()+"','" + ikametgah.getText() + "','" + kararNo.getText() +
                     "','" + kararTarih.getText() + "','" + suankidurum + "');";
                        
 
       
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
        }
    }
    
    
    public void seeList(){
            
            try{
                ResultSet rsPers = dbExecuteQuery("SELECT * FROM kayitlar");
                ObservableList<Person> perList = getPersonList(rsPers); 
                populatePersons(perList);
                
            }
            
            catch(Exception e){
                System.out.println(e.getMessage());
            }
            
        
    }
    
    public void search(){
        update.setVisible(false);
        String[] keys = ara.getText().split(" ");
//        HashMap map = new HashMap();
        String ney = "", o = "";
        String yeni = "SELECT * FROM kayitlar WHERE" ;
        for(String key : keys){
            
            // ad:    Eray     İsmet 
            int nere = key.indexOf(":");
            
            if(nere != -1){
                if(!o.isEmpty()){
                    yeni += " AND";
//                    map.put(ney, o);
                }
                
                ney = key.substring(0,nere);
                yeni+= " " + ney + " LIKE " ;
                
                if(nere != key.length()-1){
                    o = key.substring(nere+1,key.length());
                    yeni+= "'%" + o + "%'";
                }
            }
            else {
                
                o += (o.isEmpty() ? "" : " ") + key;
                yeni+= "'%" + o + "%'";
                       
            }
//            System.out.println(yeni);
            try{
                ResultSet rsPers = dbExecuteQuery(yeni);
                ObservableList<Person> perList = getPersonList(rsPers); 
                populatePersons(perList);
                
            }
            
            catch(Exception e){
                System.out.println(e.getMessage());
            }
            
        }
        
        
        
    }
    
    public void guncelle() throws SQLException, ClassNotFoundException {
        String updateStmt ="";
        try {
            
        updateStmt ="   UPDATE kayitlar\n" +
                        "SET \n"+
                        " tc = ?,\n" +
                        " ad = ?,\n" +
                        " soyad = ?,\n" +
                        " uni = ?,\n" +
                        " ikametgah = ?,\n" +
                        " kayitno = ?,\n" +
                        " kayittarihi = ?,\n" +
                        " durum = ?\n" +
                        " WHERE id = ?;";
        PreparedStatement prep = conn.prepareStatement(updateStmt);
                     prep.setString(1, tc.getText());
                     prep.setString(2, ad.getText());
                     prep.setString(3, soyad.getText());
                     prep.setString(4, uni.getText());
                     prep.setString(5, ikametgah.getText());
                     prep.setString(6, kararNo.getText());
                     prep.setString(7, kararTarih.getText());
                     prep.setInt(8, suankidurum);
                     prep.setInt(9, duzenlenecekid);
 
      
            prep.executeUpdate();
            Person sdsd = (Person) tableview.getSelectionModel().getSelectedItem();
            sdsd.settc(tc.getText());
            sdsd.setsoyad(soyad.getText());
            sdsd.setad(ad.getText());
            sdsd.setuni(uni.getText());
            sdsd.setPop(ikametgah.getText());
            sdsd.setDec_No(kararNo.getText());
            sdsd.setkayittarihi(kararTarih.getText());
            sdsd.setProcess(suankidurum);
            aciklama.setText("Person has been updated for, Person id: " + duzenlenecekid + "\n");
//            ShowAll();
        } 
        catch (SQLException e) {
            aciklama.setText(e + " ");
        }
        finally{
//            clearFields();
        }
    }
 
    
    public void tasdik(){
        suankidurum = 0;
        durum.setText("Tasdikli Diploma");
    }

    public void iptal(){
        suankidurum = 1;
        durum.setText("İptal Edilmiş Diploma");
    }
    
    public void aski(){
        suankidurum = 2;
        durum.setText("Askıya Alınmış Diploma");
    }
    
    public void fillTF(Person person) {
        duzenlenecekid = person.getid();
        tc.setText(person.gettc());
        ad.setText(person.getad());
        soyad.setText(person.getsoyad());
        uni.setText(person.getuni());
        ikametgah.setText(person.getPop());
        kararNo.setText(person.getDec_No());
        kararTarih.setText(person.getkayittarihi());
        switch (person.getProcess()) {
            case 0:
                tasdik();
                break;
            case 1:
                iptal();
                break;
            case 2:
                aski();
                break;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            dbConnect();
        }catch(Exception e){
            System.out.println("baglanmadi");
        }
        update.setVisible(false);
        tableview.setRowFactory( tv -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Person rowData = row.getItem();
                    fillTF(rowData);
                    update.setVisible(true);
                }
            });
            return row ;
        });
        id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tctb.setCellValueFactory(cellData -> cellData.getValue().tcProperty());
        adtb.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        soyadtb.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        unitb.setCellValueFactory(cellData -> cellData.getValue().uniProperty());
        ikametgahtb.setCellValueFactory(cellData -> cellData.getValue().popProperty());
        kararnotb.setCellValueFactory(cellData -> cellData.getValue().dec_noProperty());
        karartarihtb.setCellValueFactory(cellData -> cellData.getValue().dec_dateProperty());
        durumtb.setCellValueFactory(cellData -> cellData.getValue().processProperty());
    }    
    
    private void populatePersons (ObservableList<Person> perData) throws ClassNotFoundException {
        tableview.setVisible(true);
        tableview.setItems(perData);
    }
    
}
