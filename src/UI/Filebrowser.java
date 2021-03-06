/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Filebrowser.java
 *
 * Created on Jun 26, 2010, 12:10:12 PM
 */

package UI;

import duplicatefinder.Operations;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author goyal
 */
public class Filebrowser extends javax.swing.JDialog implements ActionListener{

    private static int numFields;
    private static ArrayList<String> list = new ArrayList<String>();
    private static ArrayList<String> headers = new ArrayList<String>();
    private static ArrayList<JLabel> labels = new ArrayList<JLabel>();
    private JPanel jPanel2,jPanel3;
    private JButton jButton1,jButton2;
    private ArrayList<JComboBox> comboBox = new ArrayList<JComboBox>();
    
    /** Creates new form Filebrows */
    public Filebrowser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadHeaders();
        loadPreferences();
        initUI();
        Home.importsetting = null;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 602, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Filebrowser dialog = new Filebrowser(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

        private void loadHeaders()
    {


        String line = null;
        BufferedReader bufRdr = null;
        headers.clear();
        try
        {
            bufRdr = new BufferedReader(new FileReader(Home.path));
            if((line = bufRdr.readLine()) != null )
            {
                StringTokenizer st = new StringTokenizer(line, ",");
                while(st.hasMoreTokens())
                {
                    headers.add(st.nextToken());
                }
            }
            bufRdr.close();

           /* for(int i = 0;i<headers.size();i++)
                System.out.print(headers.get(i)+",");
            System.out.println("");
            *
            */
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(Filebrowser.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException e)
        {
            e.printStackTrace();
        }

    }


  private void loadPreferences()
    {
        Properties prop = new Properties();
        list.clear();

    	try {
               
    		prop.load(new FileInputStream("config.properties"));
                numFields = Integer.parseInt(prop.getProperty("numberOfFields"));

                for(int i = 0;i < numFields ; i++)
                {
                    list.add(prop.getProperty("Field"+i));
                }

    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
    }



    private void initUI()
    {
        TitledBorder title = null;
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jButton1 = new JButton("done");
        jButton2 = new JButton("cancel");
        jButton1.setSize(80, 40);
        jButton2.setSize(80, 40);
        jButton1.setActionCommand("done");
        jButton2.setActionCommand("cancel");
        jButton1.addActionListener(this);
        jButton2.addActionListener(this);

        jPanel2.setLayout(new GridLayout(0, 2,1,8));
        title = BorderFactory.createTitledBorder("Select corresponding fields");
        setLayout(new BorderLayout(30, 20));
        jPanel2.setBorder(title);
        add(jPanel2, BorderLayout.WEST);
        add(jPanel2, BorderLayout.NORTH);

        JComboBox tembox;

        for(int i = 0; i<list.size() ;i++)
        {
            labels.add(new JLabel(list.get(i)));

            tembox = new JComboBox(headers.toArray());
            tembox.setPrototypeDisplayValue("#####################");
            comboBox.add(tembox);
            jPanel2.add(labels.get(i));
            jPanel2.add(comboBox.get(i));
        }

        jPanel3.setLayout(new GridLayout(1, 2));
        jPanel3.setLayout(new FlowLayout(FlowLayout.CENTER,60,40));
        add(jPanel3);
       jPanel3.add(jButton1);
       jPanel3.add(jButton2);

    }

    public void actionPerformed(ActionEvent e) {

        if("cancel".equals(e.getActionCommand()))
        {
            System.out.println("cancelling");
            this.dispose();
        }
        else if("done".equals(e.getActionCommand()))
        {
            importData();
            System.out.println("doning");
            this.dispose();

        }
    }

    private void importData()
    {
        String txt = "";
        String fields = "";
        ArrayList<String> str = new ArrayList<String>();


        for(int i = 0 ; i< numFields; i++)
        {
           fields = fields.concat(list.get(i) + " TEXT,");
        }
        fields = fields.substring(0, fields.lastIndexOf(","));
        new Operations().createTable(fields);


        for(int i = 0 ; i< headers.size(); i++)
        {
          str.add("@");
        }

        for(int i = 0 ; i< numFields; i++)
        {
          str.set(comboBox.get(i).getSelectedIndex(),list.get(i));
        }

        for(int i = 0; i< str.size() ; i++)
        {
            txt=txt.concat(str.get(i)+",");

         }
        txt = txt.substring(0, txt.lastIndexOf(","));
        Home.importsetting = txt;
        //System.out.println(txt);

    }
}
