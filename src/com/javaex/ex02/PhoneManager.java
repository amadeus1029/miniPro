package com.javaex.ex02;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PhoneManager {

	private List<Person> pList;
	private Scanner sc;

	public PhoneManager() throws IOException {
		sc = new Scanner(System.in);
		pList = new ArrayList<Person>();

		pList = getList();
	}

	// 시작준비 (시작화면 출려과 리스트 가져온다)
	public void showTitle() {
		System.out.println("===================================");
		System.out.println("*        전화번호부 관리 프로그램        *");
		System.out.println("===================================");
	}

	// 메뉴 출력과 입력을 받는다.
	public int showMenu() {
		System.out.println("");
		System.out.println("1.리스트  2.등록  3.삭제  4.검색  5.종료");
		System.out.println("-----------------------------------");
		System.out.print(">메뉴번호: ");
		return sc.nextInt();
	}

	// 1.리스트선택시
	public void showList() {
		printList();
	}

	// 2.등록선택시
	public void showAdd() throws IOException {
		sc.nextLine();
		System.out.println("<2.등록>");
		System.out.print(">이름: ");
		String name = sc.nextLine();
		System.out.print(">휴대전화: ");
		String mobile = sc.nextLine();
		System.out.print(">회사전화: ");
		String company = sc.nextLine();
		pList.add(new Person(name, mobile, company));
		saveList();
		System.out.println("[등록되었습니다.]");
	}

	// 3.삭제선택시
	public void showRemove() {
		System.out.println("<3.삭제>");
		while (true) {
			try {
				System.out.print(">번호: ");
				int order = sc.nextInt();
				pList.remove(order - 1);
				System.out.println("[삭제되었습니다.]");
				saveList();
				break;
			} catch (InputMismatchException ime) {
				System.out.println("잘못 입력하셨습니다, 다시 입력해주세요");
				sc.nextLine();
			} catch (IndexOutOfBoundsException iobe) {
				System.out.println("없는 번호입니다, 다시 입력해주세요");
				sc.nextLine();
			} catch (IOException e) {
				System.out.println("저장에 실패했습니다");
			}
		}
	}

	// 4.검색선택시
	public void showSearch() {
		sc.nextLine();
		System.out.println("<4.검색>");
		System.out.print("이름: ");
		String keyword = sc.nextLine();
		printList(keyword);
	}

	// 5.종료시
	public void showEnd() {
		System.out.println("===================================");
		System.out.println("*             감사합니다             *");
		System.out.println("===================================");
		sc.close();
	}
	
	
	// 메뉴번호를 잘못 입력시 안내문구를 출력하는 메소드
	public void showEtc() {
		System.out.println("[다시 입력해 주세요.]");
	}
	
	
	// 파일을 읽어 리스트에 담아 전달한다.
	public List<Person> getList() throws IOException {
		InputStream input = new FileInputStream("./PhoneDB.txt");
		InputStreamReader inputReader = new InputStreamReader(input, "UTF-8");
		BufferedReader buffR = new BufferedReader(inputReader);

		while (true) {
			String infoString = buffR.readLine();
			if (infoString == null) {
				break;
			}
			String[] infoArray = infoString.split(",");
			String name = infoArray[0].trim();
			String mobile = infoArray[1].trim();
			String company = infoArray[2].trim();
			pList.add(new Person(name, mobile, company));
		}
		buffR.close();

		return pList;
	}

	// 리스트를 파일에 저장한다.
	private void saveList() throws IOException {
		OutputStream target = new FileOutputStream("./PhoneDB.txt");
		OutputStreamWriter ow = new OutputStreamWriter(target, "UTF-8");
		BufferedWriter bw = new BufferedWriter(ow);

		for (Person p : pList) {
			bw.write(p.toString());
		}
		bw.close();
	}

	// 전체 리스트를 출력한다
	private void printList() {
		printList("");// 아래 메소드에 키워드값을 비워서 호출
	}

	// 키워드로 검색한 리스트를 출력한다
	private void printList(String keyword) {
		int i = 1;
		boolean result = false;
		for (Person p : pList) {
			if(p.getName().contains(keyword)) {
				System.out.println(i + "." + p.showInfo());
				result = true;
			}
			i++;
		}
		if(!result) {
			System.out.println("[일치하는 이름이 없습니다.]");
		}
	}
	
}
