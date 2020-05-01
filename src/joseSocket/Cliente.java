
package joseSocket;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                
                System.out.println("yaaaaaa");
            }
	
        
        
    }	
		
		
	private JTextField campo1;
	
	private JButton miboton;
	
}
