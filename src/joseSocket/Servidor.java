/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joseSocket;

/**
 *
 * @author JOZE RODRIGUEZ
 */

import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable{
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
                
                Thread hilo=new Thread(this);//creamos un hilo para ue este a la escucha constate en segundo plano de lo ue envie el cliente
                
                hilo.start();
                
		
		}
	
	private	JTextArea areatexto;

    @Override
    public void run() {
        
            try {
                
                
                ServerSocket servidor=new ServerSocket(9999);//se debe establecer el puerto de comuniacion ue se debe abrir
                while(true){
                Socket miSocket=servidor.accept();//de esta forma aceptamos la conexion
                DataInputStream flujoEntrada=new DataInputStream(miSocket.getInputStream());//hacemos el flujo de datos de entrada
                String texto=flujoEntrada.readUTF();//guardamos los datos recibidos en un varable
                areatexto.append(texto);//agregamos el texto recibido en el texArea
                miSocket.close();//cerramos la conexion
                }
            } catch (IOException ex) {
                System.out.println("error en el servidor");
            }
        
    }
}

