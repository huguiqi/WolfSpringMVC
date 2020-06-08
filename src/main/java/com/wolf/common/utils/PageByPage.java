package com.huazhu.im.imadminapi.common;

import java.util.ArrayList;
import java.util.List;

public class PageByPage {
    public static <T> void execute(List<T> items, int pageSize, Action<T> action) {
        if (items == null || items.size() < 1) {
            return;
        }

        ArrayList<T> itemsInOnePage = new ArrayList<>();
        for (int i = 0, size = items.size(); i < size; i += pageSize) {
            itemsInOnePage.clear();

            for (int j = i; j < i + pageSize && j < size; j++) {
                itemsInOnePage.add(items.get(j));
            }

            if (itemsInOnePage.size() < 1) {
                break;
            }

            action.execute(itemsInOnePage);
        }
    }

    @FunctionalInterface
    public static interface Action<T> {
        void execute(List<T> itemsInOnePage);
    }
}
