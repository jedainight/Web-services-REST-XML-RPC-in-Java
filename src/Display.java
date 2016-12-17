import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Display {

     String[] getConditions(Document doc) {

        String city = null;
        String s1 = null;
        String s2 = null;
        String s3 = null;
        String s4 = null;
        String s5 = null;
        String s6 = null;
        String days[]=new String[5];


        try {

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("rss");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    NodeList nl = eElement
                            .getElementsByTagName("yweather:location");

                    for (int tempr = 0; tempr < nl.getLength(); tempr++) {

                        Node n = nl.item(tempr);

                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element e = (Element) n;
                            city = e.getAttribute("city");
                          
                            s1="Weather forecast for " + city+": \n";
                        }
                    }
                    NodeList nl5 = eElement
                            .getElementsByTagName("yweather:forecast");

                    for (int tempr=0; tempr < nl5.getLength(); tempr++) {

                        Node n5 = nl5.item(tempr);

                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element e5 = (Element) n5;
                            s2=e5.getAttribute("day")+" ";
                                                      
                            Element e6 = (Element) n5;
                            s3=e6.getAttribute("date")+" --- ";
                         
                            Element e9 = (Element) n5;
                            s4="Temperature: "+ e9.getAttribute("low")+"-";
                           
                            Element e10 = (Element) n5;
                            s5=e10.getAttribute("high")+"C";
                           
                            Element e8 = (Element) n5;
                            s6=" (" + e8.getAttribute("text")+")";
                           

                            
                        } System.out.println(s2+s3+s4+s5+s6);days[0]=s1;days[tempr+1]=s2+s3+s4+s5+s6;
                    } 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }
}