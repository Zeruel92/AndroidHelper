/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package androidhelper;
import javax.swing.*;
import java.awt.event.*;
import java.lang.Runtime;
/**
 *
 * @author gabry
 */
public class Rebooter extends JFrame implements ActionListener{
    
    private JButton OK;
    private JButton CANCEL;
    private JRadioButton RECOVERY;
    private JRadioButton NORMAL;
    private ButtonGroup GRP;
    private String TARGET; 
    private String WORKDIR;
    private Runtime RNT;
    private JPanel P;
    
    public Rebooter(String target, String workdir, Runtime rnt){
        super("Riavviare "+target);
        this.setSize(200,200);
        this.TARGET=target;
        this.WORKDIR=workdir;
        this.RNT=rnt;
        this.P=new JPanel();
        this.add(P);
     //   this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.OK= new JButton("Riavvia");
        this.CANCEL= new JButton("Annulla");
        this.RECOVERY=new JRadioButton("Recovery Mode");
        this.NORMAL=new JRadioButton("Normal Mode");
        this.GRP=new ButtonGroup();
        this.GRP.add(this.RECOVERY);
        this.GRP.add(this.NORMAL);
        this.NORMAL.setSelected(true);
        this.OK.addActionListener(this);
        this.CANCEL.addActionListener(this);
        this.P.add(this.NORMAL);
        this.P.add(this.RECOVERY);
        this.P.add(this.OK);
        this.P.add(this.CANCEL);
    }
    
    public void actionPerformed(ActionEvent ev){
        String command=ev.getActionCommand();
        boolean mode=this.RECOVERY.isSelected();
        try{
        if(command.equals("Riavvia")){
            if(mode)
                this.RNT.exec(this.WORKDIR+"adb -s "+this.TARGET+" reboot recovery");
            else
                this.RNT.exec(this.WORKDIR+"adb -s "+this.TARGET+" reboot");
            this.setVisible(false);
        }
        else{
            this.setVisible(false);
        }
     }catch(Exception e){
         
     }
    }
    
}
