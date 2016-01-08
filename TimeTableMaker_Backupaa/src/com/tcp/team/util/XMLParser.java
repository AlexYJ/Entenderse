package com.tcp.team.util;

import java.io.FileReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.tcp.team.logger.LogManager;

/**
 * @author Anh
 * 	Name	::	XMLParser
 * 	Func	::	XML문서를 XPath를 이용해 SELECT 하듯이 값을 가져온다
 * 	수정		::	2015-03-19 ArrayList -> HashMap으로 변경

 *  용도 :: XMLParser의 용도는 Protocol 및 구조를 Leopard 구현하는 팀에서 알고 있는 상황에서
 *        Init 과정에서 XML File IO 처리를 통해 Data를 메모리에 구조화해서 올리고
 *        그것을 가져다 쓰는 상황을 만드는 기본 parser 이다.
 *        
 *        parse method 를 통해 ArrayList , HashMap 등으로 메모리에 올려진 Data는
 *        별도 클래스에서 Storage 로 관리한다.
 */
public class XMLParser {
	
	// 싱글톤 구현
	private static volatile XMLParser inst = null;
	
	private XMLParser(){ }
	
	public static XMLParser getInstance(){
		if(inst == null){
			synchronized (XMLParser.class) {
				LogManager.getInstance().logging("[Singleton] XMLParser 임계구역 진입");
				if(inst == null){
					LogManager.getInstance().logging("[Singleton] XMLParser 인스턴스 생성");
					inst = new XMLParser();
				}
			}
		}
		return inst;
	}

	/**
	 * @param filePath  -- XML 문서가 있는 경로명
	 * @param target    -- //* 와 같은 XPath에게 XML중 어느 부분을 가져올지를 지정한다
	 * @param map       -- XML파일로 부터 쿼리를 로드한 다음 메모리에 저장할 자료구조를 지정
	 * @return			-- XML 쿼리를 파싱하여 LeopardHash<String,String>으로 메모리에 저장
	 * @throws Exception
	 */
	public LeopardHash<String, String> parseFromFile(String filePath ,String target,LeopardHash<String, String> map) throws Exception{
		
		InputSource ipSrc = new InputSource(new FileReader(filePath));
		
		return parse(ipSrc,target,map);
	}
	
	private LeopardHash<String, String> parse(InputSource src,String target,LeopardHash<String, String> map) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src);
		XPath xpath = XPathFactory.newInstance().newXPath();

		NodeList nodeList = (NodeList)xpath.evaluate(target,doc,XPathConstants.NODESET);
		LeopardHash<String,String> retVal = getChildNodeVal(nodeList,map);
		
		return retVal;
	}
	/**
	 * @param list
	 * @param map
	 * @return
	 * 자식노드의 리스트를 돌면서 키를 이름으로, 값을 값으로 AnhHash에 넣어서 그 Hash값을 리턴한다.
	 */
	private LeopardHash<String,String> getChildNodeVal(NodeList list,LeopardHash<String,String> map){
		
		String name = null;
		String value = null;
		
		for(int listIdx=0,listLen=list.getLength(); listIdx<listLen; listIdx++){
			if(list.item(listIdx).hasChildNodes()){
				getChildNodeVal(list.item(listIdx).getChildNodes(),map);
			}
			
			name = list.item(listIdx).getNodeName();
			if(name.equals("#text")){
				continue;
			}
			else{
				try{
					value = list.item(listIdx).getTextContent();
					
					System.out.println(value);
					
					if(value != null){
						map.put(name, value);
					}
				}catch(Exception e ){
					LogManager.getInstance().logging("XML 파싱 에러 : "+e.getMessage());
				}
			}
		}
		return map;
	}

	// XML Parser Test 
	public static void main(String[] args) throws Exception{
		
		XMLParser parser = XMLParser.getInstance();
		LeopardHash<String,String> map = new LeopardHash<String, String>();
		map = parser.parseFromFile("C:/Users/Anh/OneDrive/Tcp/leopard/xml_test.txt","//root/row",map);
		
		ArrayList<String> list = map.getKeysList();
		ArrayList<String> val = null;
		
		for(int i=0,len=list.size(); i<len; i++){
			val = map.get(list.get(i));
			System.out.println("키 : "+list.get(i));
			for(int j=0,jLen=val.size(); j<jLen; j++){
				System.out.println("값 : "+val.get(j));
			}
		}
	}
}
