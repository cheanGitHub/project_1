package com.cc.jvm;

/**
 * 字段不参与多态
 */
public class _8_10_FieldHasNoPolymorphic {

    public static void main(String[] args) {
        Father obj = new Son();
        System.out.println(obj.money);
    }

    static class Father {
        public int money = 1;
        public Father(){
            money = 2;
            showMoney();
        }
        public void showMoney(){
            System.out.println("Father money = " + money);
        }
    }

    static class Son extends Father {
        public int money = 3;
        public Son(){
            // money = 4;
            showMoney();
        }
        public void showMoney(){
            System.out.println("Son money = " + money);
        }
    }

}
