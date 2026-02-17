package com.example.commoneydjdjmemo;
import android.content.Context;
import org.simpleframework.xml.core.Persister;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    private static final String FILE_NAME = "my_memos.xml";

    // 存入 File
    public static void saveToXML(Context context, List<Memo> memoList) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        Persister persister = new Persister();
        try {
            MemoXmlWrapper wrapper = new MemoXmlWrapper(memoList);
            persister.write(wrapper, file); // 寫入實體檔案！
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 從 File 讀取
    public static List<Memo> readFromXML(Context context) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        if (!file.exists()) return new ArrayList<>(); // 沒檔案就回傳空清單

        Persister persister = new Persister();
        try {
            MemoXmlWrapper wrapper = persister.read(MemoXmlWrapper.class, file);
            return wrapper.memoList != null ? wrapper.memoList : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}