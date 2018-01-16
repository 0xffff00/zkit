package party.threebody.zkit.tally.util;

import com.github.promeg.pinyinhelper.Pinyin;

import static com.github.promeg.pinyinhelper.Pinyin.toPinyin;

public class PinyinUtils {
    public static String[] toAcronym(String hanzi){
        if (hanzi==null){
            return new String[0];
        }
        String s=Pinyin.toPinyin(hanzi,",");
        return s.split(",");
    }
}
