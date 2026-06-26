package com.academic.menu;

import com.academic.constant.MessageConstant;
import com.academic.util.ConsoleUtil;
import com.academic.util.InputUtil;

public abstract class BaseMenu {

    protected void printMenu(String title, String[] menus) {
        ConsoleUtil.title(title);

        for (String menu : menus) {
            System.out.println(menu);
        }
    }

    protected int inputMenu() {
        return InputUtil.inputInteger("Pilih menu: ");
    }

    protected void invalidMenu() {
        ConsoleUtil.error(MessageConstant.MENU_NOT_AVAILABLE);
    }
}