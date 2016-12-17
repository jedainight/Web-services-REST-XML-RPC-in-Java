import javax.swing.*;
import javax.swing.event.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.w3c.dom.Document;
import java.awt.*;

 
public class WeatherForecast extends JPanel implements ListSelectionListener{
  
	private static final long serialVersionUID = 1L;
	JTextArea output;
    JTable table;
    ListSelectionModel listSelectionModel;
    String[] listData = { "Kipseli", "Patissia", "Lofos Strefi", "Psychiko",
            "Zappeio", "Pagkpati", "Artemida","Papagos","Vyronas","Ilioupoli","Kaisariani","Panepistimioupolis" };
    String[] WOEIDData = { "953485", "959393", "22723925", "960921",
            "23701322", "22723927", "955812","958462","965848","950453","950640","22723928" };
    JList list = new JList(listData); 
   // JList list = new JList(WOEIDData); Για λειτουργία μόνο με τα WOEID's, χωρίς τον πίνακα με τις πόλεις listData
    public WeatherForecast() {
        super(new BorderLayout());
        listSelectionModel = list.getSelectionModel();
        listSelectionModel.addListSelectionListener(this);
        
        JScrollPane listPane = new JScrollPane(list);
        JPanel controlPane = new JPanel();
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 
        //Build output area.
        output = new JTextArea(1, 10);
        output.setEditable(false);
        JScrollPane outputPane = new JScrollPane(output,
                         ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                         ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
 
        //Do the layout.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);
 
        JPanel topHalf = new JPanel();
        topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.LINE_AXIS));
        JPanel listContainer = new JPanel(new GridLayout(1,1));
        listContainer.setBorder(BorderFactory.createTitledBorder("Choose area for weather forecast!"));
        listContainer.add(listPane);
         
        topHalf.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
        topHalf.add(listContainer);
        
        topHalf.setMinimumSize(new Dimension(100, 70));
        topHalf.setPreferredSize(new Dimension(100, 155));
        splitPane.add(topHalf);
 
        JPanel bottomHalf = new JPanel(new BorderLayout());
        bottomHalf.add(controlPane, BorderLayout.PAGE_START);
        bottomHalf.add(outputPane, BorderLayout.CENTER);
        bottomHalf.setPreferredSize(new Dimension(500, 200));
        splitPane.add(bottomHalf);
    }
    
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Yahoo Weather web service client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        WeatherForecast demo = new WeatherForecast();
        demo.setOpaque(true);
        frame.setContentPane(demo);
 
        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);//Παράθυρο στο κέντρο
        frame.setVisible(true);
    }
    
    public String weatherStringForWOEID(String woeid) throws IOException
    { 	
    	Display disp = new Display();
		String s="";
		String days[]=new String[5];
		
        Document doc = generateXML(woeid);
        days = disp.getConditions(doc);
        s=days[0];// Για το String "Weather forecast for " + city+": \n". Η πόλη διαβάζεται απο το XML και όχι απο τον πίνακα listData.
        for (int i = 1; i <days.length; i++)        
        	s=s+days[i]+"\n";        
        System.out.print(s);  
        return s;
    }
    
    public void valueChanged(ListSelectionEvent e)  { 
    	if (!e.getValueIsAdjusting()) {
	        ListSelectionModel lsm = (ListSelectionModel)e.getSource();     
	        String printmsg=null;
	        
	     
	            int minIndex = lsm.getMinSelectionIndex();
	            int maxIndex = lsm.getMaxSelectionIndex();
	            try
	            {
	            for (int i = minIndex; i <= maxIndex; i++) {
	                if (lsm.isSelectedIndex(i)) {
	                	printmsg=weatherStringForWOEID(WOEIDData[i]);
	                    output.setText("Weather forecast for (WOEID:"+WOEIDData[i]+")\n"+"--------\n"+printmsg);	                 
	                }
	            }
	            }catch (Exception ex) {
	                ex.printStackTrace();
	            }
	                
    	}
    }
    public static Document generateXML(String code) throws IOException {

        String url = null;
        url = "http://weather.yahooapis.com/forecastrss?w=" + code+"&u=c";
      
        URL xmlUrl = new URL(url);
        InputStream in = xmlUrl.openStream();
        Document doc = parse(in);
        return doc;     
    }

    public static Document parse(InputStream is) {
        Document doc = null;
        DocumentBuilderFactory domFactory;
        DocumentBuilder builder;

        try {
            domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(false);
            domFactory.setNamespaceAware(false);
            builder = domFactory.newDocumentBuilder();

            doc = builder.parse(is);
        } catch (Exception ex) {
            System.err.println("unable to load XML: " + ex);
        }
        return doc;
    }
 
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}