package scripts;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.text.AbstractDocument.Content;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;




public class kuir{
	
	public static File[] makeFileList(String path) {
		File dir = new File(path);
		return dir.listFiles();
	}
	
	public void writeToXml(Element e, String file) throws FileNotFoundException, TransformerException{
		// xml ���Ϸ� ����
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
		
		DOMSource source = new DOMSource(e);
		
		File a = new File(file+"\\collection.xml");
		a.setReadable(true);
		a.setWritable(true);
		StreamResult result = new StreamResult(new FileOutputStream(a));
		
		transformer.transform(source, result);
		
	}
	
	public void getKeywords(String filepath) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		File file = new File(filepath + "\\collection.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
		Document document = docBuilder.parse(file);
		document.getDocumentElement().normalize();
		
		NodeList bodylist = document.getElementsByTagName("body");				
		
			
		for (int k=0; k < bodylist.getLength(); k++){
			Element body = (Element) bodylist.item(k); 
			String body_content = bodylist.item(k).getTextContent();
			KeywordExtractor ke = new KeywordExtractor();
			KeywordList kl = ke.extractKeyword(body_content, true);

			
			String parsed_kwrd = "";
			for (int i=0; i < kl.size(); i++) {
				Keyword kwrd = kl.get(i);
				parsed_kwrd = parsed_kwrd.concat(kwrd.getString() + ":" + kwrd.getCnt() + "#");
			
			}
			body.setTextContent(parsed_kwrd);;
//			System.out.println(body.getTextContent());
		}
			
		// xml ���Ϸ� ����
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
		
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new FileOutputStream(new File(filepath + "\\index.xml")));
		
		transformer.transform(source, result);
	}
	

	
	
	public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException, ClassNotFoundException {
		
//				
//		String filepath = "./";
//
//
//		kuir kuir = new kuir();
//		File[] fileList = makeFileList(filepath);
//		
//		//document ��ü ����
//		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder docBuilder = docFactory.newDocumentBuilder(); 
//		Document document = docBuilder.newDocument();
//		document.setXmlStandalone(true);
//			
//		
//		Element docs = document.createElement("docs");
//		document.appendChild(docs);
//		
//		int i = 0;
//		for (File file : fileList) {
//			if (file.isFile()) {
//				// System.out.println(file);
//				org.jsoup.nodes.Document html = Jsoup.parse(file, "UTF-8");
//				String titleData = html.title();
//				String bodyData = html.body().text();
//				
//	
//				//element ��ü�� �̿��Ͽ� ������ ����
//				Element doc = document.createElement("doc");
//				docs.appendChild(doc);
//				doc.setAttribute("id", Integer.toString(i++));
//				
//				
//				Element title = document.createElement("title");
//				title.appendChild(document.createTextNode(titleData));
//				doc.appendChild(title);
//				
//				Element body = document.createElement("body");
//				body.appendChild(document.createTextNode(bodyData));
//				doc.appendChild(body);				
//			}
//}
//		simpleir.writeToXml(docs, filepath);
//		simpleir.getKeywords(filepath);
		
		
		String command = args[0];
		String path = args[1];
		String q = args[2];
		String query = args[3];
		
		if (command.equals("-i")) {
			indexer indexer = new indexer();
			System.out.println("실행");
			indexer.getIndex(path);
			
			// 역직렬화된 hashmap 불러오기
			Object object = indexer.loadHashMap(path);
			
			HashMap<String, String> hashmap = (HashMap<String, String>) object;
			Iterator<String> it = hashmap.keySet().iterator();
			
			while(it.hasNext()) {
				String key = it.next();
				String value = String.valueOf(hashmap.get(key));
						hashmap.get(key);
				System.out.println(key + " → " + value);
				}}
				
		else if (command.equals("-s")){
			
			searcher searcher = new searcher();
			searcher.search(path, query);
		}}
			
		}
		


		

		
		
		
		
		






	
	

