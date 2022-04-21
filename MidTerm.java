package scripts;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.lang.model.element.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MidTerm {
	public void midterm(String path, String query) throws SAXException, IOException, ParserConfigurationException {
		// collection.xml 파일 읽어오기
		File file = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
		Document document = docBuilder.parse(file);
		document.getDocumentElement().normalize();
		
		// title 
		NodeList titlelist = document.getElementsByTagName("title");
//		System.out.println(titlelist.item(0).getTextContent());
		
	
		// 질문에서 키워드 추출
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(query, true);
		
		String parsed_kwrd = "";
		for (int i=0; i < kl.size(); i++) {
			Keyword kwrd = kl.get(i);
			parsed_kwrd = parsed_kwrd.concat(kwrd.getString());
		}

		
		// 스니펫
		NodeList bodylist = document.getElementsByTagName("body");
//		System.out.println(bodylist.item(0).getTextContent());
		
		for (int k=0; k < bodylist.getLength(); k++){
			String body_content = bodylist.item(k).getTextContent();
//			System.out.println(body_content);
			
			String sentence = "";	
			int match_count = 0;
			for (int j=0; j < body_content.length(); j++) {
				if (j+30 <= body_content.length()) {
					sentence = body_content.substring(j,j+30);
				}
				else {
					sentence = body_content.substring(j);
				}
			
			System.out.println(sentence.contains(parsed_kwrd));
			KeywordList wl = ke.extractKeyword(sentence, true);
				
			
			for (int i=0; i < wl.size(); i++) {
				Keyword kwrd = wl.get(i);
				wordlist = parsed_sent.concat(kwrd.getString());
			}

			}
				
		
		
	}
	

}