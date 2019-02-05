/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trisserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author lorenzo.orsingher
 */
public class gameServer extends Thread{
    
    private ServerSocket s;
    
    
    
    public gameServer() throws Exception
    {
        
        s = new ServerSocket(10000);
        this.start();
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            try{
            //System.out.println("attesa");
            Socket client = s.accept();
            //System.out.println("accettata 1");
            Socket client2 = s.accept();
            //System.out.println("accettata 2");
            newMatch match = new newMatch(new Connect(client), new Connect(client2));
            
            
            
            
            }catch(Exception e){}
            
        }
    }
    
}
