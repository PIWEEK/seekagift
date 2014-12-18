package seekagift

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class AmazonService {

        
    /*
     * Your AWS Access Key ID, as taken from the AWS Your Account page.
     */
    private static final String AWS_ACCESS_KEY_ID = "AKIAJUX2YYXE6K4UABSQ";

    /*
     * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
     * Your Account page.
     */
    private static final String AWS_SECRET_KEY = "2gPkFcQB18dNF//dzE+1GRJlBDL9zHginB4QyAh3";

    /*
     * Use one of the following end-points, according to the region you are
     * interested in:
     * 
     *      US: ecs.amazonaws.com 
     *      CA: ecs.amazonaws.ca 
     *      UK: ecs.amazonaws.co.uk 
     *      DE: ecs.amazonaws.de 
     *      FR: ecs.amazonaws.fr 
     *      JP: ecs.amazonaws.jp
     * 
     */
    private static final String ENDPOINT = "ecs.amazonaws.com";
//    private static final String ENDPOINT = "webservices.amazon.es";

    /*
     * The Item ID to lookup. The value below was selected for the US locale.
     * You can choose a different value if this value does not work in the
     * locale of your choice.
     */
    // private static final String ITEM_ID = "0545010225";

    private static List<Map> operation(String item, String searchIndex) {
        /*
         * Set up the signed requests helper 
         */
        SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        String requestUrl = null;
        String title = null;

        /* The helper can sign requests in two forms - map form and string form */
        
        /*
         * Here is an example in map form, where the request parameters are stored in a map.
         */
        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Version", "2009-03-31");
        params.put("AssociateTag", "seekagift09-20");
        params.put("ResponseGroup", "Large");
        
        params.put("Operation", "ItemSearch");
        params.put("SearchIndex", searchIndex);
//        params.put("SearchIndex", "Apparel");
        params.put("Keywords", item);

        requestUrl = helper.sign(params);
        System.out.println("Signed Request is \"" + requestUrl + "\"");

        return fetchTitles(requestUrl);
    }

    /*
     * Utility function to fetch the response from the service and extract the
     * title from the XML.
     */
    private static List<Map> fetchTitles(String requestUrl) {
                def items = []
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestUrl);
            NodeList nodeList = doc.getElementsByTagName("Item");
                        
                for(int i=0; i<nodeList.getLength(); i++){
                          Node childNode = nodeList.item(i);
                          
                          Element eElement = (Element) childNode;
                          
                                  def item = [:]
                                  
                                  if (eElement.getElementsByTagName("ASIN").item(0)) {
                                  item.id = eElement.getElementsByTagName("ASIN").item(0).getFirstChild().getNodeValue()
                          }
                                  if (eElement.getElementsByTagName("Title").item(0)) {
                                  item.name = eElement.getElementsByTagName("Title").item(0).getFirstChild().getNodeValue()
                          }
//                    System.out.println(item);
                                  
                                  if (eElement.getElementsByTagName("LargeImage").item(0)) {
                                          item.photoURL = eElement.getElementsByTagName("LargeImage").item(0).getFirstChild().getFirstChild().getNodeValue()
                                  }
//                    System.out.println(item);
                                  
                                  if (eElement.getElementsByTagName("FormattedPrice").item(0)) {
                                          item.price = eElement.getElementsByTagName("FormattedPrice").item(0).getFirstChild().getNodeValue()
                                  }
//                    System.out.println(item);
                                  
                                  if (eElement.getElementsByTagName("URL").item(0)) {
                                          item.url = eElement.getElementsByTagName("URL").item(0).getFirstChild().getNodeValue()
                                  }
//                                System.out.println(item);
                                  
                                  items << item

                          
                }
                        
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
                
                return items
    }
        
        public List<Gift> searchByKeywords(String data, String searchIndex) {
                def gifts = []
                        
                println "data: " + data
                def items = operation(data, searchIndex)
                println "items: " + items
                def photoDef
                def priceDef
                
                items.each {amazonGift->
                        println "---> item: "+amazonGift.name+",photo:"+amazonGift.photoURL+",price:"+amazonGift.price+",url:"+amazonGift.url
                        if (amazonGift.price){
                                priceDef = amazonGift.price
                        }else{
                                priceDef = 'FREE!!'
                        }
                        if (amazonGift.photoURL == null){
                                photoDef = '../images/GiftBox.jpg'
                        }else{
                                photoDef = amazonGift.photoURL                          
                        }
                        gifts << new Gift(id:amazonGift.id,
                                                                name:amazonGift.name, 
                                                                photoURL:photoDef, 
                                                                price:priceDef, 
                                                                giftURL:amazonGift.url, 
                                                                category:data)
                }       
                
                return gifts    
                
        }
    
}
