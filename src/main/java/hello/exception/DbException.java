package hello.exception;

/**
 * 业务异常基类，所有业务异常都必须继承于此异常 .
 */
public class DbException extends RuntimeException {

    private static final long serialVersionUID = -5875371379845226068L;

    /**
     * 数据库操作,insert返回0
     */
    public static final DbException DB_INSERT_RESULT_0 = new DbException(
            10040001, "数据库操作,insert返回0");

    /**
     * 数据库操作,update返回0
     */
    public static final DbException DB_UPDATE_RESULT_0 = new DbException(
            10040002, "数据库操作,update返回0");

    /**
     * 数据库操作,selectOne返回null
     */
    public static final DbException DB_SELECTONE_IS_NULL = new DbException(
            10040003, "数据库操作,selectOne返回null");

    /**
     * 数据库操作,list返回null
     */
    public static final DbException DB_LIST_IS_NULL = new DbException(
            10040004, "数据库操作,list返回null");

    /**
     * Token 验证不通过
     */
    public static final DbException TOKEN_IS_ILLICIT = new DbException(
            10040005, "Token 验证非法");
    /**
     * 会话超时　获取session时，如果是空，throws 下面这个异常 拦截器会拦截爆会话超时页面
     */
    public static final DbException SESSION_IS_OUT_TIME = new DbException(
            10040006, "会话超时");

    /**
     * 生成序列异常时
     */
    public static final DbException DB_GET_SEQ_NEXT_VALUE_ERROR = new DbException(
            10040007, "序列生成超时");

    /**
     * 异常信息
     */
    protected String msg;

    /**
     * 具体异常码
     */
    protected int code;

    public DbException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public DbException() {
        super();
    }

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbException(Throwable cause) {
        super(cause);
    }

    public DbException(String message) {
        super(message);
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    /**
     * 实例化异常
     * 
     * @param msgFormat
     * @param args
     * @return
     */
    public DbException newInstance(String msgFormat, Object... args) {
        return new DbException(this.code, msgFormat, args);
    }

}
