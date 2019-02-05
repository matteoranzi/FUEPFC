/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tris;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.io.Serializable;

/**
 *
 * @author lorenzo.orsingher
 */
public class Dati implements Serializable{
    
    private String s;
    private String Type;
    private String coo;
    private char[][] field;
    private String ID;
    private String winnerID;
    public Dati(String type, String value)
    {
        Type = type;
        s = "DATI";
        switch(Type)
        {
            case "MOVE":
                coo = value;
                break;
            case "ID":
                ID = value;
                break;
            case "WINNER":
                winnerID = value;
                break;
        }
    }
    
    public Dati(String type, char[][] f)
    {
        Type = type;
        
        field= new char[3][3];
        
        for(int i = 0; i < f.length; i++)
        field[i] = f[i].clone();
        
    }
    
    public Dati(String type)
    {
        Type = type;
    }
    
    public String getData()
    {
        return s;
    }
    
    public String getType()
    {
        return Type;
    }

    public String getCoo() {
        return coo;
    }

    public String getID() {
        return ID;
    }

    public char[][] getField() {
        return field;
    }

    public String getWinnerID() {
        return winnerID;
    }
    
    
    
}