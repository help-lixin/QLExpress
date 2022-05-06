package com.alibaba.qlexpress4.runtime.instruction;

import com.alibaba.qlexpress4.QLOptions;
import com.alibaba.qlexpress4.exception.ErrorReporter;
import com.alibaba.qlexpress4.member.MethodHandler;
import com.alibaba.qlexpress4.runtime.QRuntime;
import com.alibaba.qlexpress4.runtime.Value;
import com.alibaba.qlexpress4.runtime.data.DataMethodInvoke;
import com.alibaba.qlexpress4.utils.BasicUtils;
import com.alibaba.qlexpress4.utils.CacheUtils;
import com.alibaba.qlexpress4.utils.PropertiesUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Operation: invoke specified method of object on the top of stack
 * @Input: 1
 * @Output: 1, method return value, null for void method
 *
 * equivalent to GetMethodInstruction + CallInstruction
 *
 * Author: DQinYuan
 */
public class MethodInvokeInstruction extends QLInstruction {

    private final String methodName;

    private final int argNum;

    public MethodInvokeInstruction(ErrorReporter errorReporter, String methodName, int argNum) {
        super(errorReporter);
        this.methodName = methodName;
        this.argNum = argNum;
    }

    @Override
    public void execute(QRuntime qRuntime, QLOptions qlOptions) {
        Object bean = parameters.get(0).get();
        Object[] params = this.argNum > 0 ? new Object[this.argNum] : null;
        for(int i = 0; i < this.argNum; i++){
            params[i] = parameters.get(i+1).get();
        }

        if(bean == null){
            throw errorReporter.report("GET_METHOD_INVOKE_INPUT_BEAN_NULL","input parameters is null");
        }
        try {
            Class<?>[] type = BasicUtils.getTypeOfObject(params);
            Object cacheElement = CacheUtils.getMethodCacheElement(bean,this.methodName,type);
            if(cacheElement == null){
                List<Method> methods;
                if (bean instanceof Class) {
                    methods = PropertiesUtils.getClzMethod((Class<?>)bean,methodName, qlOptions.isAllowAccessPrivateMethod());
                }else {
                    methods = PropertiesUtils.getMethod(bean, methodName, qlOptions.isAllowAccessPrivateMethod());
                }
                Method method = MethodHandler.Preferred.findMostSpecificMethod(type, methods.toArray(new Method[0]));
                Value dataMethodInvoke = new DataMethodInvoke(method,bean,params,qlOptions.isAllowAccessPrivateMethod());
                qRuntime.push(dataMethodInvoke);
                if(cacheElement!=null){
                    CacheUtils.setMethodCacheElement(bean,this.methodName,dataMethodInvoke,type);
                }
            }else {
                qRuntime.push((Value) cacheElement);
            }
        } catch (Exception e){
            throw errorReporter.report("GET_METHOD_INVOKE_VALUE_ERROR","UnExpected exception: "+e.getMessage());
        }
    }
}
