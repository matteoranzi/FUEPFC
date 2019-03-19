/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proviamo1;

import javafx.beans.property.SimpleStringProperty;



/**
 *
 * @author Aurelio
 */
public class Player {
    private SimpleStringProperty id;
    private SimpleStringProperty partite;
    private SimpleStringProperty vittorie;
    
    public Player(String id, String partite, String vittorie)
    {
        this.id = new SimpleStringProperty(id);
        this.partite = new SimpleStringProperty(partite);
        this.vittorie = new SimpleStringProperty(vittorie);
               
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id= new SimpleStringProperty(id);
    }

    public String getPartite() {
        return partite.get();
    }

    public void setPartite(String partite) {
        this.partite = new SimpleStringProperty(partite);
    }

    public String getVittorie() {
        return vittorie.get();
    }

    public void setVittorie(String vittorie) {
        this.vittorie = new SimpleStringProperty(vittorie);
    }    
}
