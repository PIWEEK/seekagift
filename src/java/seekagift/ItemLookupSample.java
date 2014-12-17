/**********************************************************************************************
 * Copyright 2009 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file 
 * except in compliance with the License. A copy of the License is located at
 *
 *       http://aws.amazon.com/apache2.0/
 *
 * or in the "LICENSE.txt" file accompanying this file. This file is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License. 
 *
 * ********************************************************************************************
 *
 *  Amazon Product Advertising API
 *  Signed Requests Sample Code
 *
 *  API Version: 2009-03-31
 *
 */

package seekagift;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * This class shows how to make a simple authenticated ItemLookup call to the
 * Amazon Product Advertising API.
 * 
 * See the README.html that came with this sample for instructions on
 * configuring and running the sample.
 */
public class ItemLookupSample {
	
	
    /*
     * Your AWS Access Key ID, as taken from the AWS Your Account page.
     */
    private static final String AWS_ACCESS_KEY_ID = "AKIAIRWGPKSK6RFO244Q";

    /*
     * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
     * Your Account page.
     */
    private static final String AWS_SECRET_KEY = "A2lN2NPeF/I6xW8MIEZumepwM1wmT7xTX3oB8i6s";

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
    private static final String ITEM_ID = "0545010225";

    public static void main(String[] args) {
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
        System.out.println("Map form example:");
        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Version", "2009-03-31");
        params.put("AssociateTag", "seekagift-21");
        params.put("ResponseGroup", "Large");
        
        params.put("Operation", "ItemSearch");
        params.put("SearchIndex", "Books");
//        params.put("SearchIndex", "Apparel");
        params.put("ItemSearch.1.Keywords", "Shirt");
        params.put("ItemSearch.2.Keywords", "Agile");

        requestUrl = helper.sign(params);
        System.out.println("Signed Request is \"" + requestUrl + "\"");

//        title = fetchTitle(requestUrl);
        fetchTitles(requestUrl);
        
//        System.out.println("Signed Title is \"" + title + "\"");
        System.out.println();

        /* Here is an example with string form, where the requests parameters have already been concatenated
         * into a query string. */
//        System.out.println("String form example:");
//        String queryString = "Service=AWSECommerceService&AssociateTag=aztag-20&Version=2009-03-31&Operation=ItemLookup&ResponseGroup=Small&ItemId="
//                + ITEM_ID;
//        requestUrl = helper.sign(queryString);
//        System.out.println("Request is \"" + requestUrl + "\"");
//
//        title = fetchTitle(requestUrl);
//        System.out.println("Title is \"" + title + "\"");
//        System.out.println();

    }

    /*
     * Utility function to fetch the response from the service and extract the
     * title from the XML.
     */
    private static String fetchTitle(String requestUrl) {
        String title = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestUrl);
            Node titleNode = doc.getElementsByTagName("Title").item(0);
            title = titleNode.getTextContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return title;
    }

    /*
     * Utility function to fetch the response from the service and extract the
     * title from the XML.
     */
    private static void fetchTitles(String requestUrl) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestUrl);
            NodeList nodeList = doc.getElementsByTagName("Item");
            		
    		for(int i=0; i<nodeList.getLength(); i++){
    			  Node childNode = nodeList.item(i);
    			  
    			  Element eElement = (Element) childNode;
    			  
    		      System.out.println("Title: " + eElement.getElementsByTagName("Title").item(0).getFirstChild().getNodeValue());
    		      
    		      System.out.println("From price: " + eElement.getElementsByTagName("FormattedPrice").item(0).getFirstChild().getNodeValue());
    		      System.out.println("productUrl: " + eElement.getElementsByTagName("URL").item(0).getFirstChild().getNodeValue());
    		      
    		      System.out.println("imageUrl: " + eElement.getElementsByTagName("LargeImage").item(0).getFirstChild().getFirstChild().getNodeValue());
    			  
    		}
            		
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private static String getTagValue(String sTag, Element eElement) {
    	NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        
    	return nValue.getNodeValue();
    }    

}
