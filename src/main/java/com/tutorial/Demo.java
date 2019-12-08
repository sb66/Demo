package com.tutorial;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public final class Demo {

    private static DemoI demoI;

    private static String className = "com.tutorial.NoOpDemo";
    private static int delay =0;

    public static void main(String[] args)  {
        String filePath = null;
        if (args.length >= 1) {
            File f = new File(args[0]);
            if (args.length >= 2) {
                className=args[1];
            }
            if (args.length >= 3) {
                delay = Integer.valueOf(args[2]);
            }
            if (f.exists()) {
                 try {
                     loadDemoClass();
                     demo(args[0]);
                } catch (Exception e) {
                }
            }
        }
    }

    public static void demo(String filePath) throws  IOException, InterruptedException {
        demoI.display();
        int size = 800;
        String fullText = readFile(filePath);
        String[] textArray = splitStringEvery(fullText, 1000);
        String[] messages = new String[textArray.length];
        int length = textArray.length;
        int f = 1;
        for (String s : textArray) {
            for (int i = 0; i < 4; i++) {
                String message = buildMessage(f, length, i, s);
			    if (demoI.test(message,size)) {
			        messages[f-1] = message;
			        System.out.println(f+":"+pad.substring(0, i));
			        break;
                }
            }
            f++;
        }
        for (String s : messages) {
            demoI.display(s, size);
            if (delay>0) {
                Thread.sleep(delay);
            }
        }
    }

    private static void loadDemoClass() {
        try {
            ClassLoader classLoader = Demo.class.getClassLoader();
            Class clazz = classLoader.loadClass(className);
            demoI = (DemoI)clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String pad = "ABCDE";

    private static String buildMessage(int frameNo, int maxFrame, int padIndex, String payload) {
        String crcStr = String.format("%04X", hash(payload.getBytes()));
        String prefix = "" + (frameNo++) + "/" + maxFrame + "/" + pad.substring(0, padIndex) + "/" + crcStr + "|";
        return prefix + payload;
    }

    public static String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double) interval)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        } //Add the last bit
        result[lastIndex] = s.substring(j);

        return result;
    }

    private static String readFile(String pathname) throws IOException {

        String content = new String(Files.readAllBytes(Paths.get(pathname)));
        return content;
    }

    private static int hash(final byte[] buffer) {
        int hash = 0xFFFF;
        
        for (int j = 0; j < buffer.length; j++) {
            hash = ((hash >>> 8) | (hash << 8)) & 0xffff;
            hash ^= (buffer[j] & 0xff);//byte to int, trunc sign
            hash ^= ((hash & 0xff) >> 4);
            hash ^= (hash << 12) & 0xffff;
            hash ^= ((hash & 0xFF) << 5) & 0xffff;
        }
        hash &= 0xffff;
        return hash;

    }

}