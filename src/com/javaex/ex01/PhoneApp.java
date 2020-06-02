package com.javaex.ex01;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PhoneApp {

    public static void main(String[] args) throws IOException {
        //ArrayList 생성 후 파일로부터 리스트 내부 객체 삽입
        List<Person> personList = new ArrayList<Person>();
        readDB(personList);

        //입력받을 준비
        Scanner sc = new Scanner(System.in);

        //시스템 시작 - 텍스트파일은 프로그램 종료 후 변경되도록 설정
        System.out.println("===================================");
        System.out.println("*        전화번호부 관리 프로그램        *");
        System.out.println("===================================");
        boolean menuEnd = false;
        do {
            System.out.println("");
            System.out.println("1.리스트  2.등록  3.삭제  4.검색  5.종료");
            System.out.println("-----------------------------------");
            System.out.print(">메뉴번호: ");
            String menu = sc.nextLine();
            switch (menu) {
                case "1":
                    System.out.println("<1.리스트>");
                    showList(personList,"");
                    break;
                case "2":
                    System.out.println("<2.등록>");
                    System.out.print(">이름: ");
                    String name = sc.nextLine();
                    System.out.print(">휴대전화: ");
                    String mobile = sc.nextLine();
                    System.out.print(">회사전화: ");
                    String company = sc.nextLine();
                    personList.add(new Person(name, mobile, company));
                    updateDB(personList);
                    System.out.println("[등록되었습니다.]");
                    break;
                case "3":
                    System.out.println("<3.삭제>");
                    while (true) {
                        try {
                            System.out.print(">번호: ");
                            int order = sc.nextInt();
                            removePerson(personList, order);
                            System.out.println("[삭제되었습니다.]");
                            sc = new Scanner(System.in);
                            break;
                        } catch (InputMismatchException ime) {
                            System.out.println("잘못 입력하셨습니다, 다시 입력해주세요");
                            sc = new Scanner(System.in);
                        } catch (IndexOutOfBoundsException iobe) {
                            System.out.println("없는 번호입니다, 다시 입력해주세요");
                            sc = new Scanner(System.in);
                        }
                    }
                    break;
                case "4":
                    System.out.println("<4.검색>");
                    System.out.print("이름: ");
                    String searchName = sc.nextLine();
                    showList(personList,searchName);
                    break;
                case "5":
                    menuEnd = true;
                    System.out.println("===================================");
                    System.out.println("*             감사합니다             *");
                    System.out.println("===================================");
                    break;
                default:
                    System.out.println("[다시 입력해 주세요.]");
                    break;
            }
        } while (!menuEnd);

        sc.close();
    }

    //프로그램 가동 시에 리스트 읽어오는 초기화 메소드
    public static void readDB(List<Person> list) throws IOException {
        InputStream input = new FileInputStream("./PhoneDB.txt");
        InputStreamReader inputReader = new InputStreamReader(input, "UTF-8");
        BufferedReader buffR = new BufferedReader(inputReader);

        while (true) {
            String infoString = buffR.readLine();
            if (infoString == null) {
                break;
            }
            addPerson(list, infoString);
        }

        buffR.close();
    }

    //목록 호출 메소드
    public static void showList(List<Person> list,String str) {
        int i = 1;
        boolean result = false;
        for (Person p : list) {
            if(p.getName().contains(str)) {
                System.out.println(i + "." + p.showInfo());
                result = true;
            }
            i++;
        }
        if(!result) {
            System.out.println("[일치하는 이름이 없습니다.]");
        }
    }

    //인물 추가 메소드
    public static void addPerson(List<Person> list, String str) throws IOException {
        String[] infoArray = str.split(",");
        String name = infoArray[0].trim();
        String mobile = infoArray[1].trim();
        String company = infoArray[2].trim();
        list.add(new Person(name, mobile, company));
        updateDB(list);
    }

    //인물 삭제 메소드
    public static void removePerson(List<Person> list, int order) throws IOException {
        list.remove(order - 1);
        updateDB(list);
    }

    //시스템 종료시에 텍스트 파일 변경 메소드
    public static void updateDB(List<Person> list) throws IOException {
        OutputStream target = new FileOutputStream("./PhoneDB.txt");
        OutputStreamWriter ow = new OutputStreamWriter(target, "UTF-8");
        BufferedWriter bw = new BufferedWriter(ow);

        for (Person p : list) {
            bw.write(p.toString());
        }
        bw.close();
    }

}