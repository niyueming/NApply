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

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author niyueming
 * @date 2016-11-02
 * @time 14:22
 */

public class IOUtils {
    private final static int BUFFER_SIZE = 32 * 1024;

    public static void copy(InputStream in, OutputStream out)throws IOException{
        byte[] buffer = new byte[BUFFER_SIZE];
        while (true) {
            int count = in.read(buffer);
            if (count >= 0) {
                out.write(buffer, 0, count);
            } else {
                return;
            }
        }
    }

    public static String readString(InputStream in)throws IOException{
        StringBuilder builder = new StringBuilder();
        byte[] buffer = new byte[BUFFER_SIZE];
        while (true){
            int count = in.read(buffer);
            if (count >= 0){
                builder.append(new String(buffer,0,count));
            }else {
                return builder.toString();
            }
        }
    }

    public static void writeString(String file,String content){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileWriter != null){
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void close(InputStream in){
        if (in != null){
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(OutputStream out){
        if (out != null){
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
