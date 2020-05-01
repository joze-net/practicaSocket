
package joseSocket;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
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
	
		JLabel texto=new JLabel("CLIENTE");
		
		add(texto);
	
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
                    Socket miSocket=new Socket(InetAddress.getLocalHost(),9999);//de esta manera se crea un tipo de puente entre el cliente y servidor
                    DataOutputStream flujoSalida=new DataOutputStream(miSocket.getOutputStream());//de esta forma es como obtener la ruta por donde se enviaran los datos
                    flujoSalida.writeUTF(campo1.getText());//aui enviamos el texto escrito en el campo de texto 
                    flujoSalida.close();
                    
                } catch (IOException ex) {
                    
                    System.out.println(ex.getMessage());
                }
            }
	
        
        
    }	
		
		
	private JTextField campo1;
	
	private JButton miboton;
	
}
