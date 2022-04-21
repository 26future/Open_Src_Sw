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
	public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException, ClassNotFoundException {
		
		String command = args[0];
		String path = args[1];
		String q = args[2];
		String query = args[3];
		
		if (command.equals("-c")) {
			makeCollection collection = new makeCollection();
			collection.makeXml(path);
		}
		
		if (command.equals("-k")) {
			makeKeyword keyword = new makeKeyword();
			keyword.convertXml(path);
		}
		
		
		if (command.equals("-i")) {
			indexer indexer = new indexer();
			indexer.getIndex(path);
				}
				
		if (command.equals("-s")){
			searcher searcher = new searcher();
			searcher.calcSim(path, query);
		}
		
		else if (command.equals("-m")) {
			MidTerm midterm = new MidTerm();
			midterm.midterm(path, query);
			
			
		}
		
	}
			
	}
		


		

		
		
		
		
		






	
	

