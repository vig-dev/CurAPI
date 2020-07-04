package curapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class CurAPI implements ActionListener, ItemListener, KeyListener {
        JFrame f;
private JComboBox<String> cb1, cb2;
private JLabel l1,l2,l3,l4,lt1,lt2,lt3,lt4,lt5,lt6,lt7,lt8,lt9;
private JButton b1,b2;
private JTextField t1;
HashMap<String, Float> map = null; //for storing the data from JSON file.


public CurAPI() throws Exception{
        map = LoadContent();
        Object[] keyArray = map.keySet().toArray();
      
        f = new JFrame();//creating instance of JFrame 
        
        
        float today1=todaysRates("USD");
        float today2=todaysRates("JPY");
        float today3=todaysRates("GBP");
        float today4=todaysRates("RUB");
        float today5=todaysRates("CNY");
        float today6=todaysRates("AUD");
        
        l1=new JLabel("CurAPI");
        l1.setBounds(250, 20, 200, 50);
        l1.setFont(new Font("TimesRoman", Font.BOLD, 30));
        f.add(l1);
        
        l2=new JLabel("please enter input.");
        l2.setBounds(350, 100, 100, 40);
        l2.setToolTipText("Amount after conversion");
        l2.setFont(new Font("TimesRoman", Font.BOLD, 15));
        f.add(l2);
        
        t1=new JTextField();
        t1.setBounds(50, 100, 100, 40);
        t1.addKeyListener(this);
        t1.addActionListener(this);
        t1.setToolTipText("Enter amount");
        f.add(t1);
        
        cb1 = new JComboBox<>();
        cb1.setBounds(150, 100, 100, 40);
        cb1.addActionListener(this);
        //cb1.setBackground(Color.yellow);
        cb1.setToolTipText("Select a currency");
        f.add(cb1);
        
        cb2 = new JComboBox<>();
        cb2.setBounds(450, 100, 100, 40);
        cb2.addActionListener(this);
        //cb2.setBackground(Color.green);
        cb2.setToolTipText("Select a currency");
        f.add(cb2);
        
        l3=new JLabel(" = ");
        l3.setBounds(300, 100, 50, 40);
        l3.setFont(new Font("TimesRoman", Font.BOLD, 20));
        f.add(l3);
        
        
        b1 = new JButton("clear");
        b1.setBounds(150, 200, 100, 40);
        b1.addActionListener(this);
        f.add(b1); 
        
        b2 = new JButton("convert");
        b2.setBounds(350, 200, 100, 40);
        b2.addActionListener(this);
        f.add(b2); 
        
        l4=new JLabel("Today's exchange rates:");
        l4.setFont(new Font("TimesRoman", Font.BOLD, 20));
        l4.setBounds(190, 290, 500, 50);
        f.add(l4);
        
        lt1=new JLabel("USD to INR = "+today1);
        lt1.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        lt1.setBounds(20, 350, 500, 40);
        f.add(lt1);
        
        lt2=new JLabel("JPY to INR = "+today2);
        lt2.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        lt2.setBounds(220, 350, 500, 40);
        f.add(lt2);
        
        lt3=new JLabel("GBP to INR = "+today3);
        lt3.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        lt3.setBounds(420, 350, 500, 40);
        f.add(lt3);
        
        lt4=new JLabel("RUB to INR = "+today4);
        lt4.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        lt4.setBounds(20, 400, 500, 40);
        f.add(lt4);
        
        lt5=new JLabel("CNY to INR = "+today5);
        lt5.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        lt5.setBounds(220, 400, 500, 40);
        f.add(lt5);
        
        lt6=new JLabel("AUD to INR = "+today6);
        lt6.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        lt6.setBounds(420, 400, 500, 40);
        f.add(lt6);
        
        f.setSize(600, 500);//600 width and 600 height  
        f.setLayout(null);//using no layout managers  
        f.setVisible(true);//making the frame visible  
        f.setResizable(false);
        f.setLocationRelativeTo(null); // application opens in middle of screen.
        f.setTitle("Currency Convertor using API");
        
        for (Object key : keyArray) {
            cb1.addItem(key.toString());
            cb2.addItem(key.toString());
        }
        
    }
public float todaysRates(String a){
        float tdy=map.get("INR");
        float tdyX=map.get(a);
        float todays=tdy/tdyX;
        return todays;
    }
    
    public HashMap<String, Float> LoadContent() throws Exception{
        HashMap<String, Float> t = new HashMap();
        String sURL = "https://api.exchangeratesapi.io/latest"; //just a string
        // Connect to the URL using java's native library
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.connect();

        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
        String rates = rootobj.get("rates").toString(); //just grab the rates.
        // java.net.http.HttpClient

        String[] Cname = new String[32];
        String trate = rates;
        while (trate.contains(":")) {
            String temp = "";
            if (trate.contains(",")) {
                temp = trate.substring(trate.indexOf("\""), trate.indexOf(","));
                trate = trate.substring(trate.indexOf(",") + 1);
            } else {
                temp = trate.substring(trate.indexOf("\""), trate.indexOf("}"));
                trate = "";
            }
            t.put(temp.substring(temp.indexOf("\"")+1, temp.lastIndexOf("\"")), Float.parseFloat(temp.substring(temp.indexOf(":") + 1)));
        }
        return t;
    }

    public static void main(String[] args) throws Exception{
            try{
                new CurAPI();
            }catch(java.net.UnknownHostException ar){
                System.out.println("Where is your internet!");
            }    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        float rate1=0, rate2=0;
        String s1 = (String) cb1.getSelectedItem();
        String s2 = (String) cb2.getSelectedItem();
        if(e.getSource()==cb1){
            l2.setText("");
        }
        if(e.getSource() == cb2){
            l2.setText(""); 
        }
        if(e.getSource() == b2){
            try {
                rate1 = map.get(s1);        
                rate2 = map.get(s2);
                float amount = Float.parseFloat(t1.getText());
                float total = (rate2 / rate1) * amount;
                l2.setText(String.valueOf(total));
             
            } catch (Exception m) {

                l2.setText("Invalid input.");
            }
        }
        if(e.getSource()==b1){
            t1.setText("");
            l2.setText("");
        }
        
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
