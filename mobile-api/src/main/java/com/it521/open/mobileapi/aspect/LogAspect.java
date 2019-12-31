package com.it521.open.mobileapi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author X
 * @描述.操作日志记录处理
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.it521.open.common.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切入点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            // 获得注解
//            Log controllerLog = getAnnotationLog(joinPoint);

            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            System.out.println("className...." + className);
            System.out.println("methodName...." + methodName);
            System.out.println("插入数据库中....");

        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

//    /**
//     * 获取注解中对方法的描述信息 用于Controller层注解
//     *
//     * @param log     日志
//     * @param operLog 操作日志
//     * @throws Exception
//     */
//    public void getControllerMethodDescription(Log log, SysOperLog operLog) throws Exception {
//        // 设置action动作
//        operLog.setBusinessType(log.businessType().ordinal());
//        // 设置标题
//        operLog.setTitle(log.title());
//        // 设置操作人类别
//        operLog.setOperatorType(log.operatorType().ordinal());
//        // 是否需要保存request，参数和值
//        if (log.isSaveRequestData()) {
//            // 获取参数的信息，传入到数据库中。
//            setRequestValue(operLog);
//        }
//    }
//
//    /**
//     * 获取请求的参数，放到log中
//     *
//     * @param operLog 操作日志
//     * @throws Exception 异常
//     */
//    private void setRequestValue(SysOperLog operLog) throws Exception {
//        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
//        String params = JSON.marshal(map);
//        operLog.setOperParam(StringUtils.substring(params, 0, 2000));
//    }
//
//    /**
//     * 是否存在注解，如果存在就获取
//     */
//    private Log getAnnotationLog(JoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method method = methodSignature.getMethod();
//
//        if (method != null) {
//            return method.getAnnotation(Log.class);
//        }
//        return null;
//    }
}
