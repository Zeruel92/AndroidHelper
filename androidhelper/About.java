/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package androidhelper;
import javax.swing.*;

/**
 *
 * @author gabry
 */
public class About extends JFrame{
    
    private JLabel LABEL1;
    private JLabel LABEL2;
    private JPanel P;
    private String Version;
    
    public About(String v){
        super("About");
        this.setSize(300,150);
        this.Version=v;
        this.P=new JPanel();
        this.add(this.P);
        this.LABEL1=new JLabel();
        this.LABEL1.setText("Versione: "+this.Version);
        this.P.add(this.LABEL1);
        this.LABEL2=new JLabel();
        this.LABEL2.setText("Applicazione realizzata da pspGT");
        this.P.add(this.LABEL2);
        
    }
    
}
