//package com.example.kr;
//
//import java.util.*;
//
//public class Main2 {
//    public static void main(String[] args) {
//
//        Scanner scanner = new Scanner(System.in);
//        String numberOfObjects;
//        int nOfO;
//        List<Fan> currentList = new ArrayList<>();
//
//        System.out.print("Enter the number of objects: ");
//        numberOfObjects = scanner.nextLine();
//        nOfO = Integer.parseInt(numberOfObjects);
//        for (int i = 0; i < nOfO; i++) {
//            currentList.add(Fan.askStudentParameters());
//        }
//
//        //МЕНЮ:
//        boolean flag = true;
//
//        System.out.println("You are welcome to my menu!");
//        System.out.println("List of commands:+\n");
//        System.out.println("1) Вивести всі об’єкти поточного ліста у зручній формі (по одному в рядку).\n ");
//        System.out.println("2) Передивитись i-й об’єкт поточного ліста.\n ");
//        System.out.println("3) Додати на i-у позицію у поточний ліст об’єкт (запитати параметри об’єкта).\n " +
//                "Мається на увазі, що об’єкт, який перед цим займав i-ту позицію, посунеться і буде обіймати\n " +
//                "i+1-шу позицію і так далі. Кількість елементів у лісті при цьому збільшиться на один.\n ");
//        System.out.println("4) Видалити  i-й об’єкт поточного ліста.\n");
//        System.out.println("5) Відсортувати за певною ознакою об’єкти поточного ліста об’єктів. Двома способами: вкладеним класом\n " +
//                "(nested class) класу мікрооб’єкта та анонімним класом;\n");
//        System.out.println("6) Реалізувати глибинне копіювання для класу мікрооб’єкту. По команді з меню спитати в користувача позицію\n" +
//                " i об’єкта, який буде скопійовано, та продемонструвати використання глибинного копіювання шляхом додання до поточного\n" +
//                " ліста копії i-го об’єкта.\n");
//        System.out.println("7) Реалізувати пошук об’єкта у поточному лісті з допомогою функції Collections.binarySearch. Для цього відсортувати\n" +
//                " ліст згідно критерію пошуку(by Money) по зростанню. Запитати в користувача параметри пошуку. Створити тимчасовий об'єкт з параметрами\n" +
//                " пошуку. Здійснити пошук тимчасового об'єкта в поточному лісті функцією Collections.binarySearch і вивести на екран результати\n" +
//                " пошуку. Факультативне завдання: якщо в лісті міститься більше одного об’єкта, який задовольняє параметри пошуку, то видати\n" +
//                " на екран позиції в лісті всіх подібних об’єктів.\n ");
//        System.out.println("8) З об’єктів поточного ліста утворити тимчасовий ліст. шляхом глибинного копіювання. Утворений тимчасовий\n" +
//                " ліст вивести на екран, якимось чином змінивши 0-й елемент ліста. Після цього користувач може обрати п.1 з меню і\n" +
//                " подивитись, чи змінився 0й об’єкт поточного ліста\n ");
//        System.out.println("9) Запитати в користувача певний параметр, який дозволяє поділити об’єкти поточного ліста на дві категорії.\n" +
//                " Після запиту в користувача видалити з поточного ліста об’єкти однієї з двох категорій.\n ");
//        System.out.println("10) Завершення програми\n");
//
//        while (flag) {
//            System.out.print("Please, choose command: ");
//
//            String number = scanner.nextLine();
//            int num = Integer.parseInt(number);
//
//            switch (num) {
//                case 1:
//                    for (Fan fan : currentList)
//                        System.out.println(fan);
//                    System.out.println();
//                    break;
//                case 2:
//                    System.out.print("Enter the index of the object you want to view: ");
//                    String indexShow = scanner.nextLine();
//                    int iS = Integer.parseInt(indexShow);
//                    System.out.println(currentList.get(iS));
//                    System.out.println();
//                    break;
//                case 3:
//                    System.out.print("Enter the index at which position you want to add the new object: ");
//                    String indexPut = scanner.nextLine();
//                    int iP = Integer.parseInt(indexPut);
//                    currentList.add(iP, Fan.askStudentParameters());
//                    System.out.println("New object was created on position " + iP + "\n");
//                    break;
//                case 4:
//                    System.out.print("Enter the index of the object you want to delete: ");
//                    String indexRemove = scanner.nextLine();
//                    int iR = Integer.parseInt(indexRemove);
//                    currentList.remove(iR);
//                    System.out.println("Object with index = " + iR + " was deleted\n");
//                    break;
//                case 5:
//                    System.out.println("Sorting by anonymous class (by Money): ");
//                    Collections.sort(currentList, new Comparator<Fan>() {
//                        @Override
//                        public int compare(Fan o1, Fan o2) {
//                            if (o1.getMoney() > o2.getMoney()) {
//                                return 1;
//                            } else if (o1.getMoney() < o2.getMoney()) {
//                                return -1;
//                            } else {
//                                return 0;
//                            }
//
//                        }
//                    });
//                    for (Fan fan : currentList)
//                        System.out.println(fan);
//                    Fan fanToShowAnotherMethod=new Fan("To show another method",40,10,false);
//                    currentList.add(fanToShowAnotherMethod);
//                    System.out.println("Sort by nested class (by Money): ");
//                    Collections.sort(currentList, new Fan.FanComparator());
//                    for (Fan fan : currentList)
//                        System.out.println(fan);
//                    System.out.println();
//                    break;
//                case 6:
//                    System.out.print("Enter the index of the object you want to copy: ");
//                    String indexCopy = scanner.nextLine();
//                    int iC = Integer.parseInt(indexCopy);
//                    Fan fan = currentList.get(iC);
//                    fan.setAccessory(1, "glasses");
//                    Fan fanC;
//                    try {
//                        fanC = (Fan) fan.clone();
//                        currentList.add(fanC);
//                        fan.setAccessory(2, "gloves");
//                    } catch (CloneNotSupportedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    System.out.println("Object with index = " + iC + " was copied\n");
//                    break;
//                case 7:
//                    Collections.sort(currentList);
//                    System.out.print("Enter Money to search: ");
//                    String MoneyToSearch = scanner.nextLine();
//                    int MoneyToS = Integer.parseInt(MoneyToSearch);
//
//                    Fan tmp = new Fan();
//                    tmp.setMoney(MoneyToS);
//                    int indexResultBinarySearch = Collections.binarySearch(currentList, tmp);
//
//                    if (indexResultBinarySearch >= 0) {
//                        System.out.println("Object found at index: " + indexResultBinarySearch);
//                        // Якщо знайдено більше одного об'єкта, який задовольняє параметрам пошуку
//                        // Вивести позиції всіх подібних об'єктів
//                        int startIndex = indexResultBinarySearch;
//                        while (startIndex > 0 && currentList.get(startIndex - 1).compareTo(tmp) == 0) {
//                            startIndex--;
//                        }
//                        int endIndex = indexResultBinarySearch;
//                        while (endIndex < currentList.size() - 1 && currentList.get(endIndex + 1).compareTo(tmp) == 0) {
//                            endIndex++;
//                        }
//                        for (int i = startIndex; i <= endIndex; i++) {
//                            if (i == indexResultBinarySearch) {
//                                continue;
//                            }
//                            System.out.println("Position in array of similar objects: " + i);
//                        }
//                    } else {
//                        System.out.println("Object not found.");
//                    }
//                    System.out.println();
//                    break;
//                case 8:
//                    System.out.println("Changed list: ");
//                    List<Fan> copiedCurrentList = new ArrayList<>();
//                    Fan fanCl;
//                    for (Fan b : currentList) {
//                        try {
//                            fanCl = (Fan) b.clone();
//                            copiedCurrentList.add(fanCl);
//
//                        } catch (CloneNotSupportedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                    copiedCurrentList.get(0).setName("Changed name in copiedCurrentList");
//                    for (Fan b : copiedCurrentList)
//                        System.out.println(b);
//                    System.out.println();
//                    break;
//                case 9:
//                    System.out.print("Enter a parameter (ticket availability) that will delete the objects: ");
//                    String parameterToRemove = scanner.nextLine();
//                    boolean parameterToR = Boolean.parseBoolean(parameterToRemove);
//                    currentList.removeIf(a -> a.isHasTicket() == parameterToR);
//                    System.out.println("Objects with isHasTicket = " + parameterToR + " were deleted!\n");
//                    break;
//                case 10:
//                    flag = false;
//                    System.out.println();
//                    break;
//                default:
//                    System.out.println("I dont know such command!!!");
//                    break;
//            }
//        }
//    }
//}
