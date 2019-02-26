package com.clxk.h.sdustcamp.helper;

import com.othershe.groupindexlib.bean.BaseItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public abstract class SortHelper<T extends BaseItem> {
    public SortHelper() {
    }

    public void sortByLetter(List<T> datas) {
        Iterator var2 = datas.iterator();

        while(var2.hasNext()) {
            T data = (T) var2.next();
            String fist = this.sortField(data);
            data.setTag(fist);
        }

        Collections.sort(datas, new Comparator<T>() {
            public int compare(T o1, T o2) {
                if (o1.getTag().equals("#")) {
                    return 1;
                } else {
                    return o2.getTag().equals("#") ? -1 : o1.getTag().compareTo(o2.getTag());
                }
            }
        });
    }

    public List<String> getTags(List<T> datas) {
        List<String> tags = new ArrayList();
        Iterator var3 = datas.iterator();

        while(var3.hasNext()) {
            BaseItem data = (BaseItem)var3.next();
            tags.add(data.getTag());
        }

        return tags;
    }

    public abstract String sortField(T var1);
}

