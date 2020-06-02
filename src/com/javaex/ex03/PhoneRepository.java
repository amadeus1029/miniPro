package com.javaex.ex03;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PhoneRepository {

    
    //phoneDB.txt 파일을 읽어 모든 전화번호(리스트)를 전달하는 메소드
	public List<Person> getList() throws IOException {
		InputStream input = new FileInputStream("./PhoneDB.txt");
		InputStreamReader inputReader = new InputStreamReader(input, "UTF-8");
		BufferedReader buffR = new BufferedReader(inputReader);

		List<Person> list = new ArrayList<Person>();

		while (true) {
			String infoString = buffR.readLine();
			if (infoString == null) {
				break;
			}
			String[] infoArray = infoString.split(",");
			String name = infoArray[0].trim();
			String mobile = infoArray[1].trim();
			String company = infoArray[2].trim();
			list.add(new Person(name, mobile, company));
		}
		buffR.close();

		return list;
	}

	//phoneDB.txt 에 모든 전화번호 리스트를 저장하는 메소드
	private void saveInfo(List<Person> list) throws IOException {
		OutputStream target = new FileOutputStream("./PhoneDB.txt");
		OutputStreamWriter ow = new OutputStreamWriter(target, "UTF-8");
		BufferedWriter bw = new BufferedWriter(ow);

		for (Person p : list) {
			bw.write(p.toString());
		}
		bw.close();
	}
	
	//기존데이터에 새로입력받은 데이터를 추가하여 모두저장하는 메소드 
	public void addInfo(Person phoneVO) throws IOException {
		List<Person> list = this.getList();
		list.add(phoneVO);
		saveInfo(list);
	}

	//선택한 번호의 데이터를 삭제하고 저장하는 메소드(모두 다시저장)
	public void delInfo(int num) throws IOException {
		List<Person> list = this.getList();
		list.remove(num-1);
		saveInfo(list);
	}
	

}
