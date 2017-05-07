/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomatakip;

/**
 *
 * @author anormal
 */
import javafx.beans.property.*;
import java.sql.Date;
 
public class Person {

    private IntegerProperty id;
    private StringProperty tc;
    private StringProperty ad;
    private StringProperty soyad;
    private StringProperty uni;
    private StringProperty ikametgah;
    private StringProperty Dec_No;    
    private StringProperty kayittarihi;
    private IntegerProperty durum;
     private StringProperty durum2;

   
 

    public Person() {
        this.id = new SimpleIntegerProperty();
        this.tc = new SimpleStringProperty();
        this.ad = new SimpleStringProperty();
        this.soyad = new SimpleStringProperty();
        this.uni = new SimpleStringProperty();
        this.ikametgah = new SimpleStringProperty();
        this.Dec_No = new SimpleStringProperty();
        this.kayittarihi = new SimpleStringProperty();
        this.durum = new SimpleIntegerProperty();
        this.durum2 = new SimpleStringProperty();
        
    }
 
  
    public int getid() {
        return id.get();
    }
 
    public void setid(int personId){
        this.id.set(personId);
    }
 
    public IntegerProperty idProperty(){
        return id;
    }
    
    public String gettc () {
        return tc.get();
    }
 
    public void settc (String tc){
        this.tc.set(tc);
    }
 
    public StringProperty tcProperty() {
        return tc;
    }
 
 
    public String getad () {
        return ad.get();
    }
 
    public void setad(String firstad){
        this.ad.set(firstad);
    }
 
    public StringProperty nameProperty() {
        return ad;
    }
 
    
    public String getsoyad () {
        return soyad.get();
    }
 
    public void setsoyad(String lastad){
        this.soyad.set(lastad);
    }
 
    public StringProperty surnameProperty() {
        return soyad;
    }
 
    
    public String getuni () {
        return uni.get();
    }
 
    public void setuni (String uni){
        this.uni.set(uni);
    }
 
    public StringProperty uniProperty() {
        return uni;
    }
 
    
    public String getPop () {
        return ikametgah.get();
    }
 
    public void setPop (String jobId){
        this.ikametgah.set(jobId);
    }
 
    public StringProperty popProperty() {
        return ikametgah;
    }
 
    
    public String getkayittarihi(){
        return kayittarihi.get();
    }
 
    public void setkayittarihi(String hireDate){
        this.kayittarihi.set(hireDate);
    }
 
    public StringProperty dec_dateProperty(){
        return kayittarihi;
    }
    
    
    public String getDec_No() {
        return Dec_No.get();
    }
 
    public void setDec_No(String salary){
        this.Dec_No.set(salary);
    }
 
    public StringProperty dec_noProperty(){
        return Dec_No;
    }
    
    
    public int getProcess() {
        return durum.get();
    }
 
    public void setProcess(int process){
        this.durum.set(process);
        this.durum2.set((process == 0 ? "Tasdikli Diploma": (process == 1 ? "İptal Edilmiştir" : "Askıya Alınmıştır")));
    }
    
    public StringProperty processProperty() {
       return durum2; 
    }
 
}
