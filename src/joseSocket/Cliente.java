
package joseSocket;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
                
                InformaIp wl=new InformaIp();//esta claase tiene un evento de ventana para cuando sea abierta avise al servidor
                
                mimarco.addWindowListener(wl);//ponemos la ventana a la escucha
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		}	
	
}

class LaminaMarcoCliente extends JPanel implements Runnable{
	
	public LaminaMarcoCliente(){
            
                nombreNick =new JLabel("Nick: ");
                
                add(nombreNick);
                
                txtnick = new JLabel();
                
                nombre = JOptionPane.showInputDialog("Ingrese Nick: ");
                
                txtnick.setText(nombre);
                
                add(txtnick);
                
                
	
		JLabel texto=new JLabel("CLIENTE");
		
		add(texto);
                
                menuip = new JComboBox();
                
               
                
                add(menuip);
                
                campoChat=new JTextArea(10,19);
                
                add(campoChat);
	
		campo1=new JTextField(20);
	
		add(campo1);		
	
		miboton=new JButton("Enviar");
                
                ClaseEvento objetoEvento=new ClaseEvento();
                
                miboton.addActionListener(objetoEvento);
		
		add(miboton);	
                
                Thread nuevoHilo=new Thread(this);
                
                nuevoHilo.start();
                
                
		
	}

    @Override
    public void run() {
       
        while (true) {            
            
            try {
                ServerSocket servidor_cliente=new ServerSocket(9090);//establecemos el puente de comunicacion a abrir del servidor hacia este cliente
                Socket cliente;//nesecitamos un flujo de entrada, para usaremos esta variable
                PaqueteEnvio paqueteRecibido;//y tambien un variable donde se almacenara los datos recibidos
                
                
                while (true) {                    
                    
                    cliente=servidor_cliente.accept();//aceptamos la conexion
                    ObjectInputStream flujoEntrada= new ObjectInputStream(cliente.getInputStream());//creamos el flujo de entrada de datos
                    paqueteRecibido=(PaqueteEnvio)flujoEntrada.readObject();//leemos el objeto recibido y lo almacenamos en la variable asiganada paa ello
                   
                    
                    
                    if(!paqueteRecibido.getMensaje().equals("Online ")){
                        
                         campoChat.append("\n "+paqueteRecibido.getNick()+" dice: "+paqueteRecibido.getMensaje());//mostramos el mensaje en el chat del cliente
                        
                    }else{
                        ArrayList<String> actualizarIpConect=new ArrayList<String>();//se guardaran las ip que se van conectando en esta variable array
                        actualizarIpConect=paqueteRecibido.getIpConectadas();//se realiza el almacenamiento
                        
                        for (String poneIp: actualizarIpConect){
                            menuip.removeAll();//reseteamos para no tener las ip repetidas
                            menuip.addItem(poneIp);//agregamos las ip el comboBox
                            
                        }
                    }
                }
                
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al leer los datos enviados del servidor en direccion al cliente");
            }
            
        }
        
    }
	
	
	
	private class ClaseEvento implements ActionListener{
    
            @Override
            public void actionPerformed(ActionEvent e){
                
                try {
                    Socket miSocket=new Socket("192.168.56.1"/*InetAddress.getLocalHost()*/,9999);//de esta manera se crea un tipo de puente entre el cliente y servidor
                  
                    PaqueteEnvio datos=new PaqueteEnvio();//en la variable datos se va a empaquetar la informacion a enviar
                    
                    datos.setNick(txtnick.getText());//guardamos el nick o nombre del cliente en la variable datos, on¡btenido del campo de txt
                    datos.setIp(menuip.getSelectedItem().toString());//guardamos la  ip  del cliente en la variable datos, on¡btenido del campo de txt
                    datos.setMensaje(campo1.getText());//guardamos el msm o   cliente en la variable datos, on¡btenido del campo de txt
                    
                    ObjectOutputStream flujoSalida=new ObjectOutputStream(miSocket.getOutputStream());//creamos el flujo de salida del objeto con la informacion
                    flujoSalida.writeObject(datos);//escribimos el obj a enviar el cual debe poder ser serializado
                    flujoSalida.close();
                    /*DataOutputStream flujoSalida=new DataOutputStream(miSocket.getOutputStream());//de esta forma es como obtener la ruta por donde se enviaran los datos
                    flujoSalida.writeUTF(campo1.getText());//aui enviamos el texto escrito en el campo de texto 
                    flujoSalida.close();
*/
                    
                } catch (IOException ex) {
                    
                    System.out.println(ex.getMessage()+" Error en el cliente");
                }
            }
	
        
        
    }	
		
	private JTextArea campoChat;
        
	private JTextField campo1;
        
        private JComboBox menuip;
        
        private JLabel txtnick,nombreNick;
	
	private JButton miboton;
        
        private String nombre;
	
}


class PaqueteEnvio implements Serializable{
    
    private String nick,ip,mensaje;
    ArrayList <String> ipConectadas=new ArrayList<String>();

    public ArrayList<String> getIpConectadas() {
        return ipConectadas;
    }

    public void setIpConectadas(ArrayList<String> ipConectadas) {
        this.ipConectadas = ipConectadas;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    
}

class InformaIp extends WindowAdapter{
    
    @Override
    public void windowOpened(WindowEvent e){
       
        try {
            Socket puenteInformar= new Socket("192.168.56.1",9999);//creamos el puente de comunicacion
            ObjectOutputStream ipInformar=new ObjectOutputStream(puenteInformar.getOutputStream());//creamos el flujo de datos de salida
            PaqueteEnvio datosInformacion=new PaqueteEnvio();//creamos un paquete de envio de informacion
            datosInformacion.setMensaje("Online ");//agregamos este mensaje al objeto de tipo PaqueteEbvio
            ipInformar.writeObject(datosInformacion);//enviamos los datos mediante la variable creada tipo ObjectInputStream
            ipInformar.close();//cerramos la conexion
            
        } catch (IOException ex) {
            System.out.println("Error al informar la ip, origen en clase cliente");
        }
        
    }
    
}
