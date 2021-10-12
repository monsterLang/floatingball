package com.example.demo_v7_addshell;

import android.content.Context;
import android.widget.Toast;

import java.lang.Runtime;
import java.lang.Process;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;







public class ShellCmd {

    Runtime  runtime;
    Process  proc;

    public ShellCmd(String cmd) throws IOException {
        runtime  =  Runtime.getRuntime();
        try {
            proc  =  runtime.exec(cmd);
//        is  =  proc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream(), "gbk"));
            String line = null;
            while ((line = reader.readLine()) != null){
                System.out.println(line);
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }


    }
}
