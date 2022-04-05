package scripts;

import java.io.ByteArrayInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class indexer {



	public void getIndex(String path) throws SAXException, IOException, ParserConfigurationException, TransformerException{
		
		File file = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
		Document document = docBuilder.parse(file);
		document.getDocumentElement().normalize();

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
	
		HashMap<String, ArrayList> wordMap = new HashMap<String, ArrayList>();
		ArrayList<Double> zero = new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0));
		
		
		for(int i = 0; i < document.getElementsByTagName("body").getLength(); i++) {
			String body = document.getElementsByTagName("body").item(i).getTextContent();
			
			String[] pairsOfWordCount = body.split("#");
			
			for(String pair : pairsOfWordCount) {
				ArrayList<Double> tmp;
				String word = pair.split(":")[0];
				double tf = Double.parseDouble(pair.split(":")[1]);
				
				if (wordMap.containsKey(word))
					tmp = (ArrayList<Double>) wordMap.get(word).clone();
				else
					tmp = (ArrayList<Double>) zero.clone();
				
				tmp.set(i, tf);
				wordMap.put(word, tmp);
			}
		}
		
		for(String word : wordMap.keySet()) {
			ArrayList<Double> tmp = (ArrayList<Double>)wordMap.get(word).clone();
			int numberOfDoc = 5;
			double df = (double)(numberOfDoc - Collections.frequency(tmp, 0.0));
			
			for(int i = 0; i < numberOfDoc; i++) {
				if(tmp.get(i) == 0)
					continue;
				double weight = tmp.get(i) * Math.log((double)numberOfDoc / df);
				tmp.set(i, Math.round(weight*100)/100.0);
			wordMap.replace(word, tmp);
			}
		}
		
		HashMap<String, String> post = new HashMap<String, String>();

		int numberOfDoc = 5;
		for(String word : wordMap.keySet()) {
			StringBuilder sb = new StringBuilder();
			ArrayList<Double> tmp = wordMap.get(word);
			for(int i = 0; i < numberOfDoc; i++) {
				sb.append(i).append(" ");
				sb.append(tmp.get(i)).append(" ");
			}
			post.put(word, sb.toString().strip());
			
		}
		
		// 직렬화된 hashmap 저장
		FileOutputStream fos = new FileOutputStream("./index.post");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(post);
		oos.close();
			
	}
	
	public Object loadHashMap(String path) throws IOException, ClassNotFoundException {
		File file = new File("./index.post");
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object data = ois.readObject();
		ois.close();
		return data;
		
	}

		}
	

