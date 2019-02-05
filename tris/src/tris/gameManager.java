/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tris;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author lorenzo.orsingher
 */
public class gameManager extends Thread{
    
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private Socket socket = null;
    
    private Scanner inp = new Scanner(System.in);
    
    private Game g;
    
    private String ID = "B100";
    
    GUI gui;
    
    boolean getMove = false;
    String move;
    
    public gameManager(Game game)
    {
        g = game;
        gui = new GUI(this);
        gui.show();
        System.out.println("CIAO");
        try{
            
            socket = new Socket("localhost", 10000);
            System.out.println("Connesso");

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            
            System.out.println("reading");

        }catch(Exception e){ System.out.println("di po");}
        
        this.start();
    }
    
    @Override
    public void run()
    {
        try {
            out.writeObject(new Dati("ID", ID));
            System.out.println("SENT ID " + ID);
        } catch (IOException ex) {}
        
        while(true){
            System.out.println("waiting for msg");
            try{
                elabMessage((Dati)in.readObject());
            }catch(Exception e){}
        }
    }
    
    private void elabMessage(Dati d) throws IOException
    {
        System.out.println("TYPE: " + d.getType());
        
        if(d.getType().compareTo("WAIT_FOR_TURN") == 0)
        {
            gui.setLable("IT'S YOUR TURN");
            
            while(!getMove){getMove = false; System.out.println("getMove");}
            getMove = false;
            gui.setLable("WAIT");
            
            Dati mov = new Dati("MOVE", move);
            out.writeObject(mov);
        }
        if(d.getType().compareTo("FIELD") == 0)
        {
            System.out.println("field here");
            g.field = d.getField();
            gui.updateField(g.field);
        }
        
        if(d.getType().compareTo("WINNER") == 0)
        {
            if(d.getWinnerID().compareTo(ID) == 0)
            {
                gui.setLable("YOU WON!");
            }else
            {
                gui.setLable("GAME OVER");
            }
        }
    }
    
    public void newMove(String s)
    {
        move = s;
        System.out.println("new move " + s);
        getMove = true;
    }
}
