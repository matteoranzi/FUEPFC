/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trisserver;

/**
 *
 * @author lorenzo.orsingher
 */
public class TrisServer {

    /**
     * @param args the command line arguments
     */
    private static gameServer gs;
    
    public static void main(String[] args) {
        
        System.out.println("start");
        try{
            gs = new gameServer();
        }catch(Exception e){}
    }
}
