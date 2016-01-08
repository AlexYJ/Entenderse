package com.client.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.client.bean.Beanable;
import com.client.bean.BreakTimeBean;
import com.client.bean.LectureInfoBean;
import com.client.bean.TimeTable;

/**
 * @author	Nightmare
 * @설명		사용자로부터 제외할 과목, 선택할 과목, 공강 시간을 입력으로 받아들여 나오는 시간표의 경우의 수를 사용자에게 보여준다
 * 			현재, 나오는 경우의 수의 시간표가 너무 많으므로 이를 어떻게 처리해야 하는지를 적어 넣는다
 * @추가사항	앞으로 선이수 과목을 보여주는 기능을 추가한다
 *
 */
public class TimetableMixer {
	
	private String json = null;
	private ArrayList<LectureInfoBean> lectureList = null;
	private ArrayList<LectureInfoBean> mustHaveLectList = null;
	private HashMap<String, ArrayList<LectureInfoBean>> lectureInfoMap = null;
	private int maxCredit = 100;
	private int minCredit = 0;
	
	public TimetableMixer(String json) {
		
		this.json = json;
		ArrayList<Beanable> beanList = Converter.getInstance().convertBean(json, new LectureInfoBean());
		lectureList = Converter.getInstance().convertLectureInfo(beanList);
		LectureListMap makeLectureInfo = new LectureListMap();
		makeLectureInfo.makeLectureList(lectureList);
		lectureInfoMap = makeLectureInfo.getLectureInfo();
	}
	
	public int getMaxCredit() {
		return maxCredit;
	}

	public void setMaxCredit(int maxCredit) {
		this.maxCredit = maxCredit;
	}

	public int getMinCredit() {
		return minCredit;
	}

	public void setMinCredit(int minCredit) {
		this.minCredit = minCredit;
	}
	
	/**
	 * @설명	사용자로 부터 공강시간을 입력받는다
	 */
	public void setBreakTime(ArrayList<BreakTimeBean> breakList) {
		
		
	}
	
	public ArrayList<LectureInfoBean> getLectureList() {
		
		return lectureList;
	}
	
	
	
	/**
	 * @param lectureList	
	 */
	public void setMustHaveLectID(ArrayList<String> lectureID) {
		
		if(mustHaveLectList == null)
			mustHaveLectList = new ArrayList<LectureInfoBean>();

		for(int i=0,iLen=lectureID.size(); i<iLen; i++) {
			
			String targetID = lectureID.get(i);
			for(int j=0,jLen=lectureList.size(); j<jLen; j++) {
				
				if(lectureList.get(j).getLectureID().equals(targetID)) {
					
					LectureInfoBean bean = lectureList.get(j);
					mustHaveLectList.add(bean);
					lectureList.remove(j);
					jLen--;
				}
			}
		}
	}

	/**
	 * @param lectureIDList
	 * @주석으로한이유	-	이름으로 정해야 할 과목을 지정해야 하는 경우에는 모든 ID를 리스트 목록으로 정해서 가져오게 만들었으나
	 * 					조금만 더 생각해 보면 당연히 이렇게 하면 안된다. 같은 과목이 시간표에 2~3개 가 동시에 포함되기 때문..
	 * 					따라서 이 경우에는 어떻게 처리해야 할까 고민을 해본다
	 */
	/*
	public void setMustHaveLectName(ArrayList<String> lectName) {

		if (mustHaveLectList == null)
			mustHaveLectList = new ArrayList<LectureInfoBean>();

		for (int i = 0, iLen = lectName.size(); i < iLen; i++) {

			String targetName = lectName.get(i);
			for (int j = 0, jLen = lectureList.size(); j < jLen; j++) {

				if (lectureList.get(j).getLectureName().equals(targetName)) {

					LectureInfoBean bean = lectureList.get(j);
					mustHaveLectList.add(bean);
					lectureList.remove(j);
					jLen--;
				}
			}
		}
	}
	*/
	
	public void setExceptLectureID(ArrayList<String> lectureIDList) {
		
		for(int idIndex=0,idLen=lectureIDList.size(); idIndex<idLen; idIndex++) {
			
			String targetID = lectureIDList.get(idIndex);
			String targetName = null;
			for(int lectureIndex=0,lectureLen=lectureList.size(); lectureIndex<lectureLen; lectureIndex++) {
				
				if(lectureList.get(lectureIndex).getLectureID().equals(targetID)) {
					targetName = lectureList.get(lectureIndex).getLectureName();
					break;
				}
			}
			if (targetName != null) {
				for (int lectIndex = 0, lectLen = lectureList.size(); lectIndex < lectLen; lectIndex++) {

					if (lectureList.get(lectIndex).getLectureName().equals(targetName)) {
						lectureList.remove(lectureList.get(lectIndex));
						lectLen--;
					}
				}
			}
		}
	}

	public ArrayList<TimeTable> mixLectureList() throws CloneNotSupportedException {

		LectureMixer mixer = new LectureMixer(lectureList);
		mixer.setMaxCredit(maxCredit);
		mixer.setMinCredit(minCredit);
		
		TimeTable table = null;
		if (mustHaveLectList != null) {
			LectureListMap lectListMap = new LectureListMap();
			lectListMap.makeLectureList(mustHaveLectList);
			table = new TimeTable(lectureInfoMap);
			table.setIfNotHasLecture(mustHaveLectList);
		}
		
		LectureListMap lectListMap = new LectureListMap();
		lectListMap.makeLectureList(lectureList);
		
		if(table != null) {
			
			mixer.setBaseTable(table);
		}
		
		return mixer.mixLecture();
	}
	
	/**
	 * @param args
	 * @throws CloneNotSupportedException
	 * @주의사항 주어지는 최소 학점이 낮을수록 중복되는 데이터가 생긴다. 어디서 부터 찾아야 할 지 솔직히 막막하다.
	 * 			처음에 있던 데이터 중복은 대부분 막았으나.. 지금 생긴 중복은 분석하려면 시간이 굉장히 오래 걸릴듯 하다.
	 * 			어쩔수없이, 데이터가 다 만들어 진 경우에, 아니면 데이터를 등록할 때 중복되는 데이터를 막기 위한 캐싱을 하던지
	 * 			아니면 다 만들어 진 다음에 리스트를 한번씩 돌면서 중복되는 데이터를 찾던지 해야겠다
	 * 			하지만 역시나 지금 생각도 데이터를 캐싱하여 막는게 더 시간면이나 여러 면에서 효율적 일 듯 한데
	 * 			그 방식을 어떻게 적용하냐 그게 문제일 듯 하다
	 * 			이 알고리즘의 경우 시간복잡도가 중요하므로 최대한 시간을 줄일 수 있도록 한다.
	 */
	public static void main(String[] args) throws CloneNotSupportedException {
		
		String json = HttpConnection.getInstance().request("http://localhost:8080/TimeTableMaker_Backup/getLectureInfo.do");
		TimetableMixer tableMixer = new TimetableMixer(json);
		
		//전체 과목 list
		ArrayList<LectureInfoBean> lectureList = tableMixer.getLectureList();

		
		ArrayList<String> exceptList = new ArrayList<String>();
		
		tableMixer.setMaxCredit(22);
		tableMixer.setMinCredit(15);
		
		// 제외 목록을 설정한다. 

		// 컴퓨터 시스템 구조 월 9 9 목 2 3
		exceptList.add("10934338");
		// JAVA (In English) 화 2 5 
		exceptList.add("10941942");
		// 유닉스 개론 목 6 8
		exceptList.add("10948849");
		// 코업	일	0	6
		exceptList.add("10950051");
		// 수치해석 프로그래밍	6	9
		exceptList.add("10948748");
		// 컴파일러 구조
		exceptList.add("10927033");
		
		
		// 포함 목록을 설정한다. 유닉스 개론[10948850] 화 2 4, 웹프로그래밍[10926131] 월 1 4, JAVA[10915727] 수 6 9
//		ArrayList<String> mustHaveList = new ArrayList<String>();
		
//		ArrayList<LectureInfoBean> lectureList = tableMixer.getLectureList();              //제외된 과목이 빠진 list

		
//		tableMixer.setExceptLectureID(exceptList);
//		tableMixer.setMustHaveLectID(mustHaveList);
		ArrayList<TimeTable> tableList = tableMixer.mixLectureList();
		for(int i=0,len=tableList.size(); i<len; i++) {
			System.out.println("---------Lecture Info-------");
			tableList.get(i).printAllLectureNameAndTime();
		}

		/**
		 * 앞으로의 수정 방향
		 * 
		 * 현재 가지고 있는 문제점
		 * 	1. 생각보다 나오는 경우의 수가 많다
		 * 	2. 경우의 수가 많은 경우에 싱글 스레드로 구현되면 사용자는 중간에 프로그램이 잠깐 멈추는 경우가 발생한다
		 * 	3. 앞으로 구현된 모바일 환경에서 성능 이슈가 존재할 것으로 생각된다. 이 부분을 서버에서 어떻게 처리할지 생각한다
		 * 
		 * 해결 방법
		 * 	1. 경우의 수가 많은 경우에는, 구해진 경우의 수를 분류 및 검색하는 방법을 추가한다
		 * 	2. 두개의 스레드를 사용하여 하나는 경우의 수를 조합하며 나머지 하나는 조합된 경우의 수를 가지고 와서 사용자에게 보여준다
		 * 		- 생각되는 문제점 : 생산자 - 소비자 스레드 문제
		 * 		- 일단 공유객체를 만들어 데이터를 저장시키며 검색, 공유 객체에 대한 관리자를 만들어 해당 클래스에 검색 기능을 추가한다
		 * 		- 하지만 이렇게 할 경우에도 사용자는 총 몇개의 경우의 수가 나왔는지 한번에 볼 수 없게되는 문제점이 있으며,
		 * 			조합이 다 완료되지 않은 상태에서 검색이 이루어 질 경우에 파생되는 문제들에 대해서도 다시 한번 생각해 본다
		 * 			그리고 검색에 필요한 기능은 무엇무엇이 있을지 생각해 본다
		 * 			공유객체의 경우 2-스레드 알고리즘을 사용할 것인지 동기화를 사용할 것인지 아니면 sleep(), notify()를 사용할 지에 대해서 결정한다
		 */
	}
}