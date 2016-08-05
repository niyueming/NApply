/*
 * Copyright (c) 2016  Ni YueMing<niyueming@163.com>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package net.nym.napply.library.entity;



import android.os.Parcel;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

/**类Entity
 * <p>父类
 * @author nym
 * @version 2013-6-20
 * @time 下午6:17:30
 */
public abstract class Entity {

    protected static Comparator<Field> mComparator = new  Comparator<Field>() {
        @Override
        public int compare(Field lhs, Field rhs) {
            if (lhs.getName().compareTo(rhs.getName()) > 0)
                return 1;
            else if (lhs.getName().compareTo(rhs.getName()) < 0)
                return -1;
            return 0;
        }
    };

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("{");
        Class clazz = getClass();
        string(sb, clazz);

        if (sb.length() > 1)
            sb.deleteCharAt(sb.length() - 1);
        sb.append("}");

        return sb.toString();
    }

    private void string(StringBuffer sb, Class clazz) {
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            string(sb,clazz.getSuperclass());
        }
        try {
            for (Field item : clazz.getDeclaredFields()) {

                if (Modifier.toString(item.getModifiers()).contains("static"))
                {
                    //不要static修饰的属性
                    continue;
                }
                boolean accessFlag = item.isAccessible();
                /**
                 * 设置是否有权限访问反射类中的私有属性的
                 * */
                item.setAccessible(true);
                sb.append(item.getName()).append(":").append(item.get(this) + "").append(",");
                item.setAccessible(accessFlag);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected static void writeObject(Parcel source, Object mMember) {
        Field[] fields = mMember.getClass().getDeclaredFields();
        Arrays.sort(fields, mComparator);
        try {
            for (Field field : fields)
            {
                if (Modifier.toString(field.getModifiers()).contains("static"))
                {
                    //不要static修饰的属性
                    continue;
                }
                boolean accessFlag = field.isAccessible();
                /**
                 * 设置是否有权限访问反射类中的私有属性的
                 * */
                field.setAccessible(true);
                field.set(mMember, source.readValue(mMember.getClass().getClassLoader()));
                field.setAccessible(accessFlag);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void readObject(Parcel dest,Object mMember) {
        Field[] fields = mMember.getClass().getDeclaredFields();
        Arrays.sort(fields, mComparator);
        try {
            for (Field field : fields)
            {
                if (Modifier.toString(field.getModifiers()).contains("static"))
                {
                    //不要static修饰的属性
                    continue;
                }
                boolean accessFlag = field.isAccessible();
                /**
                 * 设置是否有权限访问反射类中的私有属性的
                 * */
                field.setAccessible(true);
                dest.writeValue(field.get(mMember));
                field.setAccessible(accessFlag);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
