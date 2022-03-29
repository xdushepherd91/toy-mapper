package com.xdushepherd.parser;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangqianyi03
 * @date 2022/3/26 20:22
 */
@Data
public class PrepareSqlBuilder {

    private static final String openToken = "#{";
    private static final String closeToken = "}";
    private List<String> properties = new ArrayList<>();


    public String build(String originUrl) {
        if (!StringUtils.contains(originUrl, "#{")) {
            return originUrl;
        }
        if (StringUtils.isBlank(originUrl)) {
            return "";
        }
        // search open token
        int start = originUrl.indexOf(openToken);
        if (start == -1) {
            return originUrl;
        }
        char[] src = originUrl.toCharArray();
        int offset = 0;
        final StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        do {
            if (start > 0 && src[start - 1] == '\\') {
                // this open token is escaped. remove the backslash and continue.
                builder.append(src, offset, start - offset - 1).append(openToken);
                offset = start + openToken.length();
            } else {
                // found open token. let's search close token.
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                builder.append(src, offset, start - offset);
                offset = start + openToken.length();
                int end = originUrl.indexOf(closeToken, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        // this close token is escaped. remove the backslash and continue.
                        expression.append(src, offset, end - offset - 1).append(closeToken);
                        offset = end + closeToken.length();
                        end = originUrl.indexOf(closeToken, offset);
                    } else {
                        expression.append(src, offset, end - offset);
                        break;
                    }
                }
                if (end == -1) {
                    // close token was not found.
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                } else {
                    properties.add(expression.toString());
                    builder.append("?");
                    offset = end + closeToken.length();
                }
            }
            start = originUrl.indexOf(openToken, offset);
        } while (start > -1);
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }

}
