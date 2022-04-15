package org.itstack.demo.jvm.lzc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 悟心
 * @time: 2022/4/15 00:22
 * @description:
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClassInfo {
    long lastModified;
    String md5CheckSum;
}
