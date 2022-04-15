package org.itstack.demo.jvm.lzc;

import org.itstack.demo.jvm.rtda.heap.methodarea.Method;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.regex.Pattern.compile;

/**
 * @author: 悟心
 * @time: 2022/4/2 16:52
 * @description:
 */
public class MethodDescriptorUtil {

    public static MethodInfo getMethodInfo(Method memberInfo, String simpleClassName, boolean isClinitMethod) {
        return getMethodInfo(memberInfo.name(), memberInfo.descriptor(), simpleClassName, isClinitMethod);
    }


//
//    public static MethodInfo getMethodInfo(MemberInfo memberInfo, String simpleClassName) {
//        return getMethodInfo(memberInfo.name(),memberInfo.descriptor(),simpleClassName);
//    }

    public static MethodInfo getMethodInfo(String originMethodName, String descriptor, String simpleClassName, boolean isClinitMethod) {
        String methodName = readAbleMethodName(originMethodName, simpleClassName);
        boolean isConstruction = methodName.equals(simpleClassName) ? true : false;

        Matcher matcher = compile("(\\(.*\\))(.*)").matcher(descriptor);
        boolean flag = matcher.find();
        if (!flag) {
            return new MethodInfo();
        }

        String paramType = matcher.group(1);
        String valueType = matcher.group(2);

        if (isClinitMethod) {
            MethodInfo methodInfo = MethodInfo.builder()
                    .methodName("{}")
                    .isConstruction(false)
                    .paramType("")
                    .valueType("")
                    .build();
            return methodInfo;
        } else if (isConstruction) {
            MethodInfo methodInfo = MethodInfo.builder()
                    .methodName(methodName)
                    .isConstruction(true)
                    .paramType(readAbleParamType(paramType))
                    .valueType("")
                    .build();
            return methodInfo;
        }

        MethodInfo methodInfo = MethodInfo.builder()
                .methodName(methodName)
                .isConstruction(isConstruction)
                .paramType(readAbleParamType(paramType))
                .valueType(readAbleValueType(valueType))
                .build();

        return methodInfo;
    }


    public static String readAbleMethodName(String methodName, String simpleClassName) {
        if ("<init>".equals(methodName)) {
            return simpleClassName;
        }
        if ("<clinit>".equals(methodName)) {
            return "";
        }
        return methodName;
    }

    private static String readAbleParamType(String paramType) {
        if (paramType.length() <= 2) {
            return paramType;
        }
        String tmpParamType = paramType.substring(1, paramType.length() - 1);
        String tmpParamTypeArr[] = tmpParamType.split(";");

        List list = Stream.of(tmpParamTypeArr).map(one -> readAbleOneParamType(one)).collect(Collectors.toList());
        String readAbleParamType = joinWith(",", list);

        return "(" + readAbleParamType + ")";
    }

    private static String readAbleOneParamType(String paramType) {
        if ("[Ljava/lang/String".equals(paramType)) {
            return "String[]";
        }
        return paramType;
    }

    private static String readAbleValueType(String valueType) {
        if ("V".equals(valueType)) {
            return "void ";
        }
        return valueType;
    }


    public static String joinWith(final String separator, List objects) {
        if (objects == null) {
            throw new IllegalArgumentException("Object varargs must not be null");
        }

        final String sanitizedSeparator = defaultString(separator);

        final StringBuilder result = new StringBuilder();

        final Iterator<Object> iterator = objects.iterator();
        while (iterator.hasNext()) {
            final String value = Objects.toString(iterator.next(), "");
            result.append(value);

            if (iterator.hasNext()) {
                result.append(sanitizedSeparator);
            }
        }

        return result.toString();
    }

    public static final String EMPTY = "";

    public static String defaultString(final String str) {
        return defaultString(str, EMPTY);
    }

    public static String defaultString(final String str, final String defaultStr) {
        return str == null ? defaultStr : str;
    }
}
