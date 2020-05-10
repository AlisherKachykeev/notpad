import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.datatransfer.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


public class Notepad extends JFrame implements ActionListener, WindowListener {

    JTextArea jta=new JTextArea();
    File fnameContainer;
    

    public Notepad(){

        Font fnt=new Font("Calibri",Font.PLAIN,14);
        Container con=getContentPane();


        JMenuBar jmb=new JMenuBar();
        JMenu jmfile = new JMenu("File");
        JMenu jmedit = new JMenu("Edit");
        JMenu jmfont = new JMenu("Font");
        JMenu jmhelp = new JMenu("Help");
        JMenu jmtime = new JMenu("Time");

        con.setLayout(new BorderLayout());
        //trying to add scrollbar
        JScrollPane sbrText = new JScrollPane(jta);
        sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sbrText.setVisible(true);

        jta.setFont(fnt);
        jta.setLineWrap(true);
        jta.setWrapStyleWord(true);

        con.add(sbrText);

        createMenuItem(jmfile,"New");
        createMenuItem(jmfile,"Open");
        createMenuItem(jmfile,"Save");
        jmfile.addSeparator();
        createMenuItem(jmfile,"Exit");
        createMenuItem(jmedit,"Undo");
        createMenuItem(jmedit,"Select All");
        createMenuItem(jmedit,"Copy");
        createMenuItem(jmedit,"Paste");

        createMenuItem(jmfont, "Calibri");
        createMenuItem(jmfont, "Times New Roman");
        createMenuItem(jmfont, "Arial");
        createMenuItem(jmfont, "Comic Sans MS");
        createMenuItem(jmfont, "MS Gothic");
        createMenuItem(jmfont, "Microsoft YaHei");
        jmfont.addSeparator();
        createMenuItem(jmfont, "Bold");
        createMenuItem(jmfont, "Plain");
        createMenuItem(jmfont, "Italic");

        createMenuItem(jmhelp,"About Notepad");

        createMenuItem(jmtime, "Time");

        jmb.add(jmfile);
        jmb.add(jmedit);
        jmb.add(jmfont);
        jmb.add(jmhelp);
        jmb.add(jmtime);
        setJMenuBar(jmb);
        setIconImage(Toolkit.getDefaultToolkit().getImage("images/alatoo.gif"));
        addWindowListener(this);
        setSize(500,500);
        setTitle("Untitled.txt - Notepad");



        setVisible(true);

    }

    public void createMenuItem(JMenu jm,String txt){
        JMenuItem jmi=new JMenuItem(txt);
        jmi.addActionListener(this);
        jm.add(jmi);
    }

    public void actionPerformed(ActionEvent e){
        JFileChooser jfc=new JFileChooser();

        if(e.getActionCommand().equals("New")){
            //new
            this.setTitle("Untitled.txt - Notepad");
            jta.setText("");
            fnameContainer=null;
        }else if(e.getActionCommand().equals("Open")){
            //open
            int ret=jfc.showDialog(null,"Open");

            if(ret == JFileChooser.APPROVE_OPTION)
            {
                try{
                    File fyl=jfc.getSelectedFile();
                    OpenFile(fyl.getAbsolutePath());
                    this.setTitle(fyl.getName()+ " - Notepad");
                    fnameContainer=fyl;
                }catch(IOException ers){}
            }

        }else if(e.getActionCommand().equals("Save")){
            //save
            if(fnameContainer != null){
                jfc.setCurrentDirectory(fnameContainer);
                jfc.setSelectedFile(fnameContainer);
            }
            else {
                //jfc.setCurrentDirectory(new File("."));
                jfc.setSelectedFile(new File("Untitled.txt"));
            }

            int ret=jfc.showSaveDialog(null);

            if(ret == JFileChooser.APPROVE_OPTION){
                try{

                    File fyl=jfc.getSelectedFile();
                    SaveFile(fyl.getAbsolutePath());
                    this.setTitle(fyl.getName()+ " - Notepad");
                    fnameContainer=fyl;

                }catch(Exception ers2){}
            }

        }else if(e.getActionCommand().equals("Exit")){
            //exit
            Exiting();
        }else if(e.getActionCommand().equals("Copy")) {
            //copy
            jta.copy();
        }else if(e.getActionCommand().equals("Paste")) {
            //paste
            jta.paste();
        }else if(e.getActionCommand().equals("Select All"))  {
            //select
            jta.selectAll();
        }else if(e.getActionCommand().equals("About Notepad")){
            //about
            JOptionPane.showMessageDialog(this,"Created by: Kachykeev Alisher","Notepad",JOptionPane.INFORMATION_MESSAGE);
        }else if(e.getActionCommand().equals("Cut")){
            jta.cut();
        }else if(e.getActionCommand().equals("Time")) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            JOptionPane.showMessageDialog(this, "Date: " + dtf.format(now), "Time", JOptionPane.INFORMATION_MESSAGE);

        }else if(e.getActionCommand().equals("Calibri")){
            jta.setFont(new Font("Calibri", Font.PLAIN, 14));
        }else if(e.getActionCommand().equals("Times New Roman")){
            jta.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        }else if(e.getActionCommand().equals("Arial")){
            jta.setFont(new Font("Arial", Font.PLAIN, 14));
        }else if(e.getActionCommand().equals("Comic Sans MS")){
            jta.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        }else if(e.getActionCommand().equals("MS Gothic")){
            jta.setFont(new Font("MS Gothic", Font.PLAIN, 14));
        }else if(e.getActionCommand().equals("Microsoft YaHei")){
            jta.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        }else if(e.getActionCommand().equals("Bold")){
            Font f = jta.getFont();
            jta.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        }else if(e.getActionCommand().equals("Plain")){
            Font f = jta.getFont();
            jta.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
        }else if(e.getActionCommand().equals("Italic")){
            Font f = jta.getFont();
            jta.setFont(f.deriveFont(f.getStyle() | Font.ITALIC));

        }
    }

    public void OpenFile(String fname) throws IOException {
        //open file code here
        BufferedReader d=new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
        String l;
        //clear the textbox
        jta.setText("");

        setCursor(new Cursor(Cursor.WAIT_CURSOR));

        while((l=d.readLine())!= null) {
            jta.setText(jta.getText()+l+"\r\n");
        }

        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        d.close();
    }

    public void SaveFile(String fname) throws IOException {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataOutputStream o=new DataOutputStream(new FileOutputStream(fname));
        o.writeBytes(jta.getText());
        o.close();
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void windowDeactivated(WindowEvent e){}
    public void windowActivated(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowIconified(WindowEvent e){}
    public void windowClosed(WindowEvent e){}

    public void windowClosing(WindowEvent e) {
        Exiting();
    }

    public void windowOpened(WindowEvent e){}

    public void Exiting() {
        System.exit(0);
    }

    public static void main (String[] args) {
        Notepad notp=new Notepad();
    }

}