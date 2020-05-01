
package joseSocket;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
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

class LaminaMarcoCliente extends JPanel{
	
	public LaminaMarcoCliente(){
            
            
                txtnick = new JTextField(5);
                
                add(txtnick);
	
		JLabel texto=new JLabel("CLIENTE");
		
		add(texto);
                
                txtip = new JTextField(5);
                
                add(txtip);
                
                txtMensaje=new JTextArea(10,19);
                
                add(txtMensaje);
	
		campo1=new JTextField(20);
	
		add(campo1);		
	
		miboton=new JButton("Enviar");
                
                ClaseEvento objetoEvento=new ClaseEvento();
                
                miboton.addActionListener(objetoEvento);
		
		add(miboton);	
		
	}
	
	
	
	private class ClaseEvento implements ActionListener{
    
            @Override
            public void actionPerformed(ActionEvent e){
                
                try {
                    Socket miSocket=new Socket("192.168.56.1"/*InetAddress.getLocalHost()*/,9999);//de esta manera se crea un tipo de puente entre el cliente y servidor
                  
                    PaqueteEnvio datos=new PaqueteEnvio();//en la variable datos se va a empaquetar la informacion a enviar
                    
                    datos.setNick(txtnick.getText());//guardamos el nick o nombre del cliente en la variable datos, on¡btenido del campo de txt
                    datos.setIp(txtip.getText());//guardamos la  ip  del cliente en la variable datos, on¡btenido del campo de txt
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
		
	private JTextArea txtMensaje;
        
	private JTextField campo1,txtip,txtnick;
	
	private JButton miboton;
	
}


class PaqueteEnvio implements Serializable{
    
    private String nick,ip,mensaje;

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
