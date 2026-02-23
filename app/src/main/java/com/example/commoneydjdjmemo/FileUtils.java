package com.example.commoneydjdjmemo;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static final String FILE_NAME = "memos.xml";

    // ==========================================
    //  1. 儲存資料 (寫入 XML)
    // ==========================================
    public static void saveToXML(Context context, List<Memo> memoList) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);

            // 使用 XmlSerializer 來產生 XML 內容
            XmlSerializer serializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();

            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "resources"); // 根目錄



            for (Memo memo : memoList) {
                serializer.startTag("", "memo");

                // 標題
                serializer.startTag("", "title");
                serializer.text(memo.getTitle() != null ? memo.getTitle() : "");
                serializer.endTag("", "title");

                // 內容
                serializer.startTag("", "content");
                serializer.text(memo.getContent() != null ? memo.getContent() : "");
                serializer.endTag("", "content");

                // 日期 (注意：我們統一用 "time" 這個標籤)
                serializer.startTag("", "time");
                serializer.text(memo.getTime() != null ? memo.getTime() : "");
                serializer.endTag("", "time");


                // ==========================================
                // 🎯 這是你要加的地方：寫入 isCompleted 狀態
                // ==========================================
                serializer.startTag("", "isCompleted");
                serializer.text(String.valueOf(memo.isCompleted()));
                serializer.endTag("", "isCompleted");
                // ==========================================

                serializer.endTag("", "memo");
            }

            serializer.endTag("", "resources");
            serializer.endDocument();

            // 真正寫入檔案
            serializer.flush();
            String result = writer.toString();
            fos.write(result.getBytes());
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    //  2. 讀取資料 (解析 XML) - 防護罩版
    // ==========================================
    public static List<Memo> readFromXML(Context context) {
        List<Memo> memos = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_NAME);

        // 防呆：如果檔案不存在，直接回傳空清單
        if (!file.exists()) {
            return memos;
        }

        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);

            // 建立解析器
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(fis, "UTF-8");

            int eventType = parser.getEventType();
            Memo currentMemo = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("memo".equals(tagName)) {
                            currentMemo = new Memo();
                        } else if (currentMemo != null) {
                            // 這裡一定要跟上面的 saveToXML 標籤名稱一模一樣！
                            if ("title".equals(tagName)) {
                                currentMemo.setTitle(parser.nextText());
                            } else if ("content".equals(tagName)) {
                                currentMemo.setContent(parser.nextText());
                            } else if ("time".equals(tagName)) {
                                currentMemo.setTime(parser.nextText());
                            } else if ("date".equals(tagName)) {
                                // 💡 為了救回舊資料，我們多判斷一個 "date"，以防舊檔案是用 date 存的
                                currentMemo.setTime(parser.nextText());
                            } else if ("isCompleted".equals(tagName)) {
                                // 🎯 新增這個判斷：把字串轉回布林值 (true/false)
                                currentMemo.setCompleted(Boolean.parseBoolean(parser.nextText()));
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if ("memo".equals(tagName) && currentMemo != null) {
                            memos.add(currentMemo);
                        }
                        break;
                }
                eventType = parser.next();
            }
            fis.close();

        } catch (Exception e) {
            e.printStackTrace(); // 如果還有錯，這行會把錯誤印在 Logcat
            // 發生錯誤時，回傳目前讀到的部分，避免全空
        }

        return memos;
    }
}