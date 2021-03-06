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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
		
		setBounds(1000,300,280,350);				
			
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
                String ipRecibido,nickRecibido,mensajeRecibido;  //en estas variables guardaremos los datos recibidos  
                ArrayList<String> listaip=new  ArrayList<>();
                while(true){
                
                 
                 PaqueteEnvio datosRecibidos;
                 Socket miSocket=servidor.accept();//de esta forma aceptamos la conexion
                 
                
                
                 ObjectInputStream flujoEntrada=new ObjectInputStream(miSocket.getInputStream());//recibimos la informacion o datos de entrada
                 datosRecibidos=(PaqueteEnvio)flujoEntrada.readObject();//guardamos la info recibida en una var de tipo PaqueteEnvio
                 
                 if(!datosRecibidos.getMensaje().equals("Online ")){
                 
                 ipRecibido=datosRecibidos.getIp();//y guardamos los datos correpondientes en la variables
                 nickRecibido=datosRecibidos.getNick();
                 mensajeRecibido=datosRecibidos.getMensaje();
                 
                 areatexto.append("\n"+nickRecibido+": "+mensajeRecibido+" PARA: "+ipRecibido);//mostramos los datos en el area de texto del servidor
                 
                 Socket socketReenvio = new Socket(ipRecibido,9090);//este sera la parte de abrir un nuevo enlcae con el cliente al que se le enviaran los datos
                 ObjectOutputStream flujoServidorCliente= new ObjectOutputStream(socketReenvio.getOutputStream());//creamos el nuevo flujo de datos hacie el cliente receptor
                 flujoServidorCliente.writeObject(datosRecibidos);//enviamos el objeto al cliente
                 
                 flujoServidorCliente.close();
                 socketReenvio.close();
                 miSocket.close();//se debe cerrar la conexion
                 }else{
                     
                      //------------------DETECTA IP ONLINE-----------------------------
                 
                 InetAddress localizacion=miSocket.getInetAddress();//esta parte nos devuelve un objeto de tipo InetAddress
                 String ipOnline=localizacion.getHostAddress();//en esta variable guardamos la ip que se ha conectado
                 System.out.println("Online: "+ipOnline);
                 listaip.add(ipOnline);//agregamos en un arrayList las ip que envian señal al servidor
                 datosRecibidos.setIpConectadas(listaip);//agregamos el array de ips conectadas al paquete de datos recibido
                     
                 for(String l: listaip){//en este punto se le enviara a todos los clientes las ip a medidad de que se conectan
                     
                    Socket socketReenvio = new Socket(l,9090);//en este punto se enviara a cada uno de los clientes conectados las ip que estan disponibles
                    ObjectOutputStream flujoServidorCliente= new ObjectOutputStream(socketReenvio.getOutputStream());//creamos el nuevo flujo de datos hacie el cliente receptor
                    flujoServidorCliente.writeObject(datosRecibidos);//enviamos el objeto al cliente
                 
                    flujoServidorCliente.close();
                    socketReenvio.close();
                    miSocket.close();//se debe cerrar la conexion
                     
                     
                 }
                 
                 //--------------------FIN ONLINE---------------------------------------
                     
                 }
                
                /*
                DataInputStream flujoEntrada=new DataInputStream(miSocket.getInputStream());//hacemos el flujo de datos de entrada
                String texto=flujoEntrada.readUTF();//guardamos los datos recibidos en un varable
                areatexto.append("\n"+texto);//agregamos el texto recibido en el texArea
                miSocket.close();//cerramos la conexion */
                }
            } catch (IOException ex) {
                System.out.println("error en el servidor"+" "+ex.getMessage());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MarcoServidor.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("error al leer el objeto");
            }
        
    }
}

