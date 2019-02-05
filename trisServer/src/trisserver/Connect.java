/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trisserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import tris.Dati;


/**
 *
 * @author lorenzo.orsingher
 */
public class Connect extends Thread{
    
    private Socket client = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    
    private boolean newMsg = false;
    
    private String msg = "";
    
    private newMatch nm;
    
    public String ID = "";
    
    
    
    public Connect(Socket clientSocket)
    {
        

        client = clientSocket;
        System.out.println("clientSocket " + client.getInetAddress());
        
        try{
            out = new ObjectOutputStream(client.getOutputStream()); //IMPORTANTE: bisogna inizializzare prima l'output se no rimane in deadlock
            in = new ObjectInputStream(client.getInputStream());
        }catch(Exception e)
        {
            
            try{
                client.close();
            }catch(Exception er){}
            
            System.out.println("oh no");

            return;
        }
        System.out.println("start");
        this.start();
    }
    
    @Override
    public void run()
    {
        
        try {
            ID = ((Dati)in.readObject()).getID();
            nm.communicate(this, new Dati("ID", ID));
        } catch (IOException ex){} catch (ClassNotFoundException ex) {}

        while(true)
        {
            try{
                System.out.println("waiting msg");
                nm.communicate(this, (Dati)in.readObject());
                System.out.println("received");
                out.flush();
                
            }catch(Exception e){System.err.println(e);}
        }
    }
    
    public boolean newMsg()
    {
        return newMsg;
    }
    
    public void readMsg()
    {
        newMsg = false;
    }
    
    public void sendMsg(Dati d)
    {
        try{
        out.writeObject(d);
        }catch(Exception e){}
    }
    
    public ObjectInputStream getInputStream()
    {
        return in;
    }
    
    public ObjectOutputStream getOutputStream()
    {
        return out;
    }
    
    public void setParent(newMatch nm)
    {
        this.nm = nm;
    }
}
