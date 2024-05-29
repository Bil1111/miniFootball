package com.example.kr;//package OOP.KR;
//
//import java.util.Scanner;
//
//public class Main {
//    public static Scanner in;
//
//    static { //Статичний блок ініціалізації
//        in = new Scanner(System.in);
//    }
//
//    public static Fan object1;
//
//    static {
//        try {
//            System.out.println("Static initializer 'Main'");
//            System.out.print("Enter the parameters of object1 - static 'Main' element?(0-no/1-yes):");
//            int ask = in.nextInt();
//            in.nextLine();
//            if (ask != 0) {
//                object1 = Fan.askStudentParameters();
//            } else object1 = new Fan();
//        } catch (Exception e) {
//            System.out.println("There were errors when entering - the values are set by default");
//            object1 = new Fan();
//        }
//    }
//
//    public Fan object2;
//
//    {
//        try {
//            System.out.println("Non-static initializer 'Main'");
//            System.out.print("Enter the parameters of object2 - non-static 'Main' element?(0-no/1-yes):");
//            int ask = in.nextInt();
//            in.nextLine();
//            if (ask != 0) {
//                object2 = Fan.askStudentParameters();
//            } else object2 = new Fan();
//        } catch (Exception e) {
//            System.out.println("There were errors when entering - the values are set by default");
//            object2 = new Fan();
//        }
//    }
//
//    public Main() {
//        System.out.println("'Main' constructor was called");
//    }
//
//    public static int selectObject() {
//        System.out.println("         Оберіть об'єкт:");
//        System.out.println("          1. Object1 - static 'Main' element.");
//        System.out.println("          2. Object2 - non-static 'Main' element.");
//        System.out.println("          3. Object3 - local 'main' element.");
//        System.out.print("         Your choice:");
//
//        int ch = in.nextInt();
//        in.nextLine();
//
//        if ((ch >= 1) && (ch <= 3)) return ch;
//
//        System.out.println("Wrong number - select the third object");
//        return 3;
//    }
//
//    public static void main(String[] args) {
//        System.out.println("Start of work!");
//
//        Main objMain = new Main();
//        Fan object3;
//
//        try {
//            System.out.print("Enter the parameters of object3 - local 'main' element?(0-no/1-yes):");
//            int ask = in.nextInt();
//            in.nextLine();
//            if (ask != 0) {
//                object3 = Fan.askStudentParameters();
//            } else object3 = new Fan();
//        } catch (Exception e) {
//            System.out.println("There were errors when entering - the values are set by default");
//            object3 = new Fan();
//
//        }
////Меню:
//        boolean flag = true;
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("You are welcome to my menu!");
//        System.out.println("List of commands:");
//        System.out.println("1) Вивести на екран Об'єкт 1;");
//        System.out.println("2) Змінити параметри Об'єкта 1;");
//        System.out.println("3) Вивести на екран Об'єкт 2;");
//        System.out.println("4) Змінити параметри Об'єкта 2;");
//        System.out.println("5) Вивести на екран Об'єкт 3;");
//        System.out.println("6) Змінити параметри Об'єкта 3;");
//        System.out.println("7) Обрати пару з трьох створених об’єктів і здійснити їх взаємодію;");
//        System.out.println("8) Завершення програми");
//
//        while (flag) {
//            System.out.print("Please, choose command: ");
//
//            String number = scanner.nextLine();
//            int num = Integer.parseInt(number);
//
//            switch (num) {
//                case 1:
//                    System.out.println("Object1 - static 'Main' element");
//                    object1.print();
//                    break;
//                case 2:
//                    try {
//                        System.out.println("Enter the parameters of object1:");
//                        Fan tmp = Fan.askStudentParameters();
//                        object1.setMoney(tmp.getMoney());
//                        object1.setHasTicket((tmp.isHasTicket()));
//                    } catch (Exception e) {
//                        System.out.println("No changes were made - input error");
//                    }
//                    break;
//                case 3:
//                    System.out.println("Object2 - non-static 'Main' element");
//                    objMain.object2.print();
//                    break;
//                case 4:
//                    try {
//                        System.out.println("Enter the parameters of object2:");
//                        Fan tmp = Fan.askStudentParameters();
//                        objMain.object2.setMoney(tmp.getMoney());
//                        objMain.object2.setHasTicket((tmp.isHasTicket()));
//
//                    } catch (Exception e) {
//                        System.out.println("No changes were made - input error");
//                    }
//                    break;
//                case 5:
//                    System.out.println("Object3 - local 'main' element");
//                    object3.print();
//                    break;
//                case 6:
//                    try {
//                        System.out.println("Enter the parameters of object3:");
//                        Fan tmp = Fan.askStudentParameters();
//                        object3.setMoney(tmp.getMoney());
//                        object3.setHasTicket((tmp.isHasTicket()));
//                    } catch (Exception e) {
//                        System.out.println("No changes were made - input error");
//                    }
//                    break;
//                case 7: {
//                    int choice1 = selectObject();
//                    int choice2 = selectObject();
//
//                    Fan objChoice1;
//                    Fan objChoice2;
//
//                    if (choice1 == 1) objChoice1 = object1;
//                    else if (choice1 == 2) objChoice1 = objMain.object2;
//                    else objChoice1 = object3;
//
//                    if (choice2 == 1) objChoice2 = object1;
//                    else if (choice2 == 2) objChoice2 = objMain.object2;
//                    else objChoice2 = object3;
//
//                    objChoice1.interact(objChoice2);
//                }
//                break;
//                case 8:
//                    flag = false;
//                    break;
//                default:
//                    System.out.println("I dont know such command!!!");
//                    break;
//            }
//        }
//
//        System.out.println("End of work!");
//    }
//}
