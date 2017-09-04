package hello.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * String字符串工具类.
 */
public final class StringUtil {

    private static final Log LOG = LogFactory.getLog(StringUtil.class);

    /**
     * 私有构造方法,将该工具类设为单例模式.
     */
    private StringUtil() {
    }

    /**
     * 合并字符串  和String.split()相反
     * @param stringGroup
     * @param mergeString
     * {1,2,3,4}merge“_” = “1_2_3_4”
     * @return
     */
    public static  String merge(Collection<String> stringGroup, String mergeString){
        StringBuilder stringBuilder = new StringBuilder();
        stringGroup.forEach((groupString)->stringBuilder.append(groupString).append(mergeString));
        return removeEnd(stringBuilder.toString(),mergeString);
    }

    /**
     * 合并字符串  和String.split()相反
     * @param stringGroup
     * @param mergeString
     * {1,2,3,4}merge“_” = “1_2_3_4”
     * @return
     */
    public static  String merge(String[] stringGroup,String mergeString){
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(stringGroup).forEach((groupString)->stringBuilder.append(groupString).append(mergeString));
        return removeEnd(stringBuilder.toString(),mergeString);
    }

    /**
     * 截断结尾字符串
     * @param string
     * @param endString
     * @return
     */
    public static String removeEnd(String string,String endString){
        if(string.endsWith(endString)){
            return string.substring(0,string.length()-endString.length());
        }
        throw new IllegalArgumentException(string+"  not endWith  "+endString);
    }

    /**
     * 函数功能说明 ： 判断字符串是否为空 . 修改者名字： 修改日期： 修改内容：
     * 
     * @参数： @param str
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }

    /**
     * 函数功能说明 ： 判断对象数组是否为空. 修改者名字： 修改日期： 修改内容：
     * 
     * @参数： @param obj
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static boolean isEmpty(Object[] obj) {
        return null == obj || 0 == obj.length;
    }

    /**
     * 函数功能说明 ： 判断对象是否为空. 修改者名字： 修改日期： 修改内容：
     * 
     * @参数： @param obj
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).trim().isEmpty();
        }
        return !(obj instanceof Number) ? false : false;
    }

    /**
     * 函数功能说明 ： 判断集合是否为空. 修改者名字： 修改日期： 修改内容：
     * 
     * @参数： @param obj
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static boolean isEmpty(List<?> obj) {
        return null == obj || obj.isEmpty();
    }

    /**
     * 函数功能说明 ： 判断Map集合是否为空. 修改者名字： 修改日期： 修改内容：
     * 
     * @参数： @param obj
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static boolean isEmpty(Map<?, ?> obj) {
        return null == obj || obj.isEmpty();
    }

    /**
     * 函数功能说明 ： 获得文件名的后缀名. 修改者名字： 修改日期： 修改内容：
     * 
     * @参数： @param fileName
     * @参数： @return
     * @return String
     * @throws
     */
    public static String getExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取去掉横线的长度为32的UUID串.
     * 
     * @author WuShuicheng.
     * @return uuid.
     */
    public static String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取带横线的长度为36的UUID串.
     * 
     * @author WuShuicheng.
     * @return uuid.
     */
    public static String get36UUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 验证一个字符串是否完全由纯数字组成的字符串，当字符串为空时也返回false.
     * 
     * @author WuShuicheng .
     * @param str
     *            要判断的字符串 .
     * @return true or false .
     */
    public static boolean isNumeric(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else {
            return str.matches("\\d*");
        }
    }

    /**
     * 计算采用utf-8编码方式时字符串所占字节数
     * 
     * @param content
     * @return
     */
    public static int getByteSize(String content) {
        int size = 0;
        if (null != content) {
            try {
                // 汉字采用utf-8编码时占3个字节
                size = content.getBytes("utf-8").length;
            } catch (UnsupportedEncodingException e) {
                LOG.error(e);
            }
        }
        return size;
    }

    /**
     * 函数功能说明 ： 截取字符串拼接in查询参数. 修改者名字： 修改日期： 修改内容：
     * 
     * @参数： @param ids
     * @参数： @return
     * @return String
     * @throws
     */
    public static List<String> getInParam(String param) {
        boolean flag = param.contains(",");
        List<String> list = new ArrayList<String>();
        if (flag) {
            list = Arrays.asList(param.split(","));
        } else {
            list.add(param);
        }
        return list;
    }

    /**
     * 处理较大的消息
     * @param log
     * @param big
     * @return
     */
    public static String handerBigLog(String log, int big) {

        if (log == null) {
            return "";
        }
        if (log.length() > big) {
            return log.substring(0, big);
        }
        return log;
    }

    public static String ObejctToString(Object o){
        if(o==null){
            return null;
        }else{
            return o.toString();
        }
    }



    /**
     * 将emoji表情替换成*
     *
     * @param source
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source) {
//		if(StringUtils.isNotBlank(source)){
//			String pattern="[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
//			String reStr="*";
//			Pattern emoji=Pattern.compile(pattern);
//			Matcher emojiMatcher=emoji.matcher(source);
//			source=emojiMatcher.replaceAll(reStr);
//			return source;
//		}else{
//			return source;
//		}
        if (source == null) {
            return null;
        }
        source = source.replaceAll("[^\\u0000-\\uFFFF]", "*");
        return source;
    }

    /**
     * 函数功能说明 ： 判断字符串是否为空
     *
     * @参数： @param str
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static boolean hasEmpty(String ...strs) {
        for(String str: strs){
            if(null == str || "".equals(str)){
                return true;
            }
        }
        return false;
    }

    public static String rightPad(String s, int minLength, String filling) {
        int ln = s.length();
        if(minLength <= ln) {
            return s;
        } else {
            StringBuffer res = new StringBuffer(minLength);
            res.append(s);
            int dif = minLength - ln;
            int fln = filling.length();
            if(fln == 0) {
                throw new IllegalArgumentException("The \"filling\" argument can\'t be 0 length string.");
            } else {
                int start = ln % fln;
                int end = fln - start <= dif?fln:start + dif;

                int cnt;
                for(cnt = start; cnt < end; ++cnt) {
                    res.append(filling.charAt(cnt));
                }

                dif -= end - start;
                cnt = dif / fln;

                int i;
                for(i = 0; i < cnt; ++i) {
                    res.append(filling);
                }

                cnt = dif % fln;

                for(i = 0; i < cnt; ++i) {
                    res.append(filling.charAt(i));
                }

                return res.toString();
            }
        }
    }
    
    /**
     * 函数功能说明 ： bigDicimal add
     *
     * @参数： @param str
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static String add(String ...strs) {
    	BigDecimal bd = new BigDecimal(0);
        for(String str: strs){
            if(null != str && !"".equals(str)){
               bd = bd.add(new BigDecimal(str));
            }
        }
        return bd.toPlainString();
    }
    
    /**
     * 函数功能说明 ： bigDicimal 乘法
     *
     * @参数： @param str
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static String multiply(String ...strs) {
    	BigDecimal bd = null;
        for(String str: strs){
            if(null == str || "".equals(str)){
               str = "0";
            }
            if(bd == null){
        		bd = new BigDecimal(str);
        	}else{
        		bd = bd.multiply(new BigDecimal(str));
        	}
        }
        return bd.toPlainString();
    }
    
    /**
     * 函数功能说明 ： bigDicimal 减
     *
     * @参数： @param str
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static String sub(String ...strs) {
    	BigDecimal bd = null;
        for(String str: strs){
        	if(null == str || "".equals(str)){
                str = "0";
             }
             if(bd == null){
         		bd = new BigDecimal(str);
         	}else{
         		bd = bd.subtract(new BigDecimal(str));
         	}
        }
        return bd.toPlainString();
    }

    /**
     * 函数功能说明 ： bigDicimal 出
     *
     * @参数： @param str
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static String divide(String ...strs) {
        BigDecimal bd = null;
        for(String str: strs){
            if(null == str || "".equals(str)){
                str = "0";
            }
            if(bd == null){
                bd = new BigDecimal(str);
            }else{
                bd = bd.divide(new BigDecimal(str));
            }
        }
        return bd.toPlainString();
    }
    
    /**
     * 函数功能说明 ： bigDicimal 数值比较
     *
     * @参数： @param str
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static boolean mathequals(String ...strs) {
    	BigDecimal bd = null;
        for(String str: strs){
        	if(null == str || "".equals(str)){
                str = "0";
             }
             if(bd == null){
         		bd = new BigDecimal(str);
         	}else{
         		if((new BigDecimal(str)).compareTo(bd) !=0){
         			return false;
         		}
         	}
        }
        return true;
    }

    /**
     * 函数功能说明 ： bigDicimal 数值比较
     *
     * @参数： @param str
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static int mathcompare(String a, String b) {
        if(isEmpty(a)){
            a = "0";
        }
        if(isEmpty(b)){
            b = "0";
        }
        BigDecimal abd = new BigDecimal(a);
        BigDecimal bbd = new BigDecimal(b);
        return abd.compareTo(bbd);
    }

}
