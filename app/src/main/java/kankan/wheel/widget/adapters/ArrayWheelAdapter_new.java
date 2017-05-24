/*
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package kankan.wheel.widget.adapters;

import android.content.Context;

import com.hldj.hmyg.bean.CityGsonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * The simple Array wheel adapter
 *
 * @param < > the element type
 */
public class ArrayWheelAdapter_new extends AbstractWheelTextAdapter {

    // items
    private List<CityGsonBean.ChildBeans> items;

    /**
     * Constructor
     *
     * @param context the current context
     * @param items   the items
     */
    public ArrayWheelAdapter_new(Context context, List<CityGsonBean.ChildBeans> items) {
        super(context);
        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        if (items == null) {
            items = new ArrayList<>();
        }
        this.items = items;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.size()) {
            CityGsonBean.ChildBeans item = items.get(index);
            return item.name;
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }

    public void notifyAllDatas(List<CityGsonBean.ChildBeans> newDatas) {

        if (newDatas != null) {
            if (items != null) {
                items.clear();
                items.addAll(newDatas);
            }
        }
        this.notifyDataChangedEvent();

    }  public void notifyAllDatas1(List<CityGsonBean.ChildBeans> newDatas) {

        if (newDatas != null) {
            if (items != null) {
                items.clear();
                items.addAll(newDatas);
            }
        }
        this.notifyDataInvalidatedEvent();
    }


}
