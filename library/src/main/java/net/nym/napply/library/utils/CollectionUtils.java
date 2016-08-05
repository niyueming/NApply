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

package net.nym.napply.library.utils;

import java.util.Map;

/**
 * @author nym
 * @date 2015/5/6 0006.
 * @since 1.0
 */
public class CollectionUtils {

    public static boolean isNullOrEmpty(Iterable<? extends Object> elements)
    {
        if (elements == null){
            return true;
        }
        if (!elements.iterator().hasNext())
        {
            return true;
        }
        return false;
    }

    public static boolean isMapNullOrEmpty(Map elements)
    {
        if (elements == null){
            return true;
        }
        if (!elements.entrySet().iterator().hasNext())
        {
            return true;
        }
        return false;
    }


}
