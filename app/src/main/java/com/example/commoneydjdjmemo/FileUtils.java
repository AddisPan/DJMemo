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

/**
 * 角色: XML 檔案持久化工具類
 * 
 * 責任:
 * - 將 Memo 物件清單序列化為 XML 格式並儲存至內部空間 (memos.xml)
 * - 從內部空間讀取並解析 XML 檔案還原為 Memo 物件清單
 * 
 * 需求對應:
 * - 符合需求 4.1：使用 XML 檔案儲存資料 (RecyclerView 讀取源)
 * 
 * 業界標準:
 * - 使用 try-with-resources 或確保 Stream 關閉，避免檔案鎖死。
 * - 實作防護邏輯，當檔案不存在時回傳空清單而非 null，避免 NullPointerException。
 */
public class FileUtils {

    private static final String FILE_NAME = "memos.xml";

    // 儲存資料到 XML
    public static void saveToXML(Context context, List<Memo> memoList) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            XmlSerializer serializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();

            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "resources");

            for (Memo memo : memoList) {
                serializer.startTag("", "memo");
                
                writeTag(serializer, "title", memo.getTitle());
                writeTag(serializer, "tag", memo.getTag());
                writeTag(serializer, "content", memo.getContent());
                writeTag(serializer, "time", memo.getTime());
                writeTag(serializer, "isCompleted", String.valueOf(memo.isCompleted()));
                
                serializer.endTag("", "memo");
            }

            serializer.endTag("", "resources");
            serializer.endDocument();

            fos.write(writer.toString().getBytes("UTF-8"));
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 讀取 XML 檔案
    public static List<Memo> readFromXML(Context context) {
        List<Memo> memos = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_NAME);

        if (!file.exists()) return memos;

        try (FileInputStream fis = context.openFileInput(FILE_NAME)) {
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
                            if ("title".equals(tagName)) currentMemo.setTitle(parser.nextText());
                            else if ("tag".equals(tagName)) currentMemo.setTag(parser.nextText());
                            else if ("content".equals(tagName)) currentMemo.setContent(parser.nextText());
                            else if ("time".equals(tagName)) currentMemo.setTime(parser.nextText());
                            else if ("isCompleted".equals(tagName)) currentMemo.setCompleted(Boolean.parseBoolean(parser.nextText()));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("memo".equals(tagName) && currentMemo != null) memos.add(currentMemo);
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memos;
    }

    private static void writeTag(XmlSerializer serializer, String tag, String text) throws Exception {
        serializer.startTag("", tag);
        serializer.text(text != null ? text : "");
        serializer.endTag("", tag);
    }
}
