/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package androidhelper;

import javax.swing.*;
import java.lang.Thread;
import java.util.Vector;
import java.lang.Runtime;
import java.lang.Process;
import java.awt.event.*;
import java.io.*;

/**
 *
 * @author gabry
 */
public class UI extends JFrame implements Runnable,ActionListener{
    
    private JTextField WORKDIR;
    private JTextField TARGETDIR;
    private String workdir;
    private String targetdir;
    private int TIMEOUT;
    private JLabel work;
    private JLabel target;
    private JLabel devices;
    private JPanel P;
    private String VERSION;
    private JButton SEND;
    private JButton GET;
    private JButton REBOOT;
    private Thread T;
    private Runtime RNT;
    private Process PROCESS;
    private Vector<JRadioButton> RADIOS;
    private JPanel DEVICEP;
    private ButtonGroup GRP;
    private JButton ABOUT;
    // private JRadioButton temp;
    private InputStream is;
    private BufferedReader buff;
    public UI(){
        super("Android Helper ");
        this.VERSION="0.1alpha2";
        this.workdir="/opt/android-sdk-linux/platform-tools/";
        this.targetdir="/storage/sdcard1";
        this.TIMEOUT=5000;
        this.setSize(500,300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.WORKDIR=new JTextField(this.workdir);
        this.TARGETDIR=new JTextField(this.targetdir);
        this.work=new JLabel("Android Sdk Dir: ");
        this.target=new JLabel("Destinazione: ");
        this.P=new JPanel();
        this.add(P);
        this.P.setLayout(null);
        this.P.add(this.work);
        this.work.setLocation(0,10);
        this.work.setSize(150,20);
        this.P.add(this.WORKDIR);
        this.WORKDIR.setLocation(150,10);
        this.WORKDIR.setSize(300,20);
        this.P.add(this.target);
        this.target.setLocation(0,40);
        this.target.setSize(150,20);
        this.P.add(this.TARGETDIR);
        this.TARGETDIR.setLocation(150,40);
        this.TARGETDIR.setSize(300,20);
        this.devices=new JLabel();
        this.P.add(this.devices);
        this.devices.setSize(150,20);
        this.devices.setLocation(0, 70);
        this.DEVICEP=new JPanel();
        this.P.add(this.DEVICEP);
        this.DEVICEP.setLocation(0,90);
        this.DEVICEP.setSize(500,150);
        this.SEND=new JButton("Invia");
        this.P.add(this.SEND);
        this.SEND.setLocation(0,240);
        this.SEND.setSize(100,20);
        this.GET=new JButton("Preleva");
        this.P.add(this.GET);
        this.GET.setLocation(110,240);
        this.GET.setSize(100,20);
        this.REBOOT=new JButton("Riavvia");
        this.P.add(this.REBOOT);
        this.REBOOT.setLocation(220,240);
        this.REBOOT.setSize(100,20);
        this.REBOOT.setEnabled(false);
        this.SEND.setEnabled(false);
        this.GET.setEnabled(false);
        this.ABOUT=new JButton("About");
        this.P.add(this.ABOUT);
        this.ABOUT.setSize(100,20);
        this.ABOUT.setLocation(330,240);
        this.ABOUT.addActionListener(this);
        this.REBOOT.addActionListener(this);
        this.GET.addActionListener(this);
        this.SEND.addActionListener(this);
        this.RNT= Runtime.getRuntime();
        this.RADIOS=new Vector<JRadioButton>();
        this.GRP=new ButtonGroup();
        this.T=new Thread(this);
        T.start();
    }
    public void run(){    
        String output;
        String edited;
        String selected="";
        while(true){
            output="";
            edited=output;
            try{
            this.PROCESS=this.RNT.exec(this.WORKDIR.getText()+"adb devices");
            this.is=this.PROCESS.getInputStream();
            this.buff=new BufferedReader(new InputStreamReader(is));
            if(!(this.RADIOS.isEmpty())){
                for(int i=0; i<this.RADIOS.size();i++){
                    if(this.RADIOS.elementAt(i).isSelected())
                        selected=this.RADIOS.elementAt(i).getText();                       
                }
                 this.RADIOS.removeAllElements();
                 this.DEVICEP.removeAll();
            }
            while((output=buff.readLine())!=null){
                if(!(output.equals("List of devices attached "))){
                   if(!(output.equals(""))){
                    edited=output.substring(0,output.indexOf("\t"));
                    //System.out.println(edited);
                    this.RADIOS.addElement(new JRadioButton(edited));
                   }
               }
            }
            for(int i=0; i<this.RADIOS.size();i++){
                this.DEVICEP.add(this.RADIOS.elementAt(i));
                this.GRP.add(this.RADIOS.elementAt(i));
                //System.out.println("Selected"+selected);
                this.DEVICEP.updateUI();
            }
             for(int k=0; k<this.RADIOS.size();k++){
                    if(selected.equals(this.RADIOS.elementAt(k).getText())){
                        this.RADIOS.elementAt(k).setSelected(true);
                        System.out.println("OPPA OPPA Gangnam Style");
                    }
                    else if(selected.equals(""))
                        this.RADIOS.elementAt(0).setSelected(true);
             }
             if(this.RADIOS.isEmpty()){
                this.devices.setText("No Devices Found");
                this.REBOOT.setEnabled(false);
             }
             else {
                this.devices.setText("Device:");
                this.REBOOT.setEnabled(true);
             }
            this.T.sleep(this.TIMEOUT);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void actionPerformed(ActionEvent e){
        String command=e.getActionCommand();
        String tmp="";
        if(command.equals("About")){
            About a=new About(this.VERSION);
            a.setVisible(true);
        }
        else if(command.equals("Riavvia")){
            for(int i=0;i<this.RADIOS.size();i++){
                if(this.RADIOS.elementAt(i).isSelected())
                    tmp=this.RADIOS.elementAt(i).getText();
            }
            Rebooter rt=new Rebooter(tmp,this.workdir,this.RNT);
            rt.setVisible(true);
        }
    }
}

