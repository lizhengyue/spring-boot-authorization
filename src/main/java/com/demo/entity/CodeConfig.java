package com.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.demo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.persistence.Entity;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 编码生成配置
 * </p>
 *
 * @author lzy
 * @since 2021-02-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CodeConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(exist = false)
    private ExpressionAnalyze expressionAnalyze;

    /**
     * 商户ID
     */
    private Long tenantId;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型，一般代表一种业务类型，或者说是一种生成类型
     */
    private String bizType;

    /**
     * 编码表达式
     */
    private String expression;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer deleted;



    public String analyzeExpression(){
        System.out.println("========================="+ getExpressionAnalyze());
     //   return null;
       return   getExpressionAnalyze().getAnalyzeResult();
    }


     class ExpressionAnalyze {

        private int serialNumberStart;
        private int serialNumberEnd;
        private String analyzeResult;
        private String currentDateStr;
        private String expression;

        public ExpressionAnalyze(String expression) {
            this.expression = expression;
        }

        private void initData() {
            String tempExpression = this.expression;
            StringBuffer result = new StringBuffer();
            Map<String, Integer[]> indexMap = new HashMap<>();
            boolean isFindingString = false;
            int strLength = tempExpression.length();
            for (int i = 0, j = 0; i < tempExpression.length(); i++) {
                char c = tempExpression.charAt(i);
                if(c == '"') {
                    isFindingString = !isFindingString;
                    continue;
                }
                boolean isEnd = i >= strLength - 1 || c != tempExpression.charAt(i + 1);
                String dateStr = this.currentDateStr;
                if (!isFindingString) {
                    switch (c) {
                        case 'y':
                            int length = addToIndexMap(indexMap, j, c);
                            if(isEnd) {
                                String yearStr = dateStr.substring(0, 4);
                                yearStr = StringUtils.right(yearStr, length);
                                yearStr = StringUtils.leftPad(yearStr, length, "0");
                                result.append(yearStr);
                            }
                            break;
                        case 'M':
                            length = addToIndexMap(indexMap, j, c);
                            if(isEnd) {
                                String monthStr = dateStr.substring(4, 6);
                                monthStr = StringUtils.right(monthStr, length);
                                monthStr = StringUtils.leftPad(monthStr, length, "0");
                                result.append(monthStr);
                            }
                            break;
                        case 'd':
                            length = addToIndexMap(indexMap, j, c);
                            if(isEnd) {
                                String monthStr = dateStr.substring(6, 8);
                                monthStr = StringUtils.right(monthStr, length);
                                monthStr = StringUtils.leftPad(monthStr, length, "0");
                                result.append(monthStr);
                            }
                            break;
                        case 'H':
                            length = addToIndexMap(indexMap, j, c);
                            if(isEnd) {
                                String monthStr = dateStr.substring(8, 10);
                                monthStr = StringUtils.right(monthStr, length);
                                monthStr = StringUtils.leftPad(monthStr, length, "0");
                                result.append(monthStr);
                            }
                            break;
                        case 'm':
                            length = addToIndexMap(indexMap, j, c);
                            if(isEnd) {
                                String monthStr = dateStr.substring(10, 12);
                                monthStr = StringUtils.right(monthStr, length);
                                monthStr = StringUtils.leftPad(monthStr, length, "0");
                                result.append(monthStr);
                            }
                            break;
                        case 's':
                            length = addToIndexMap(indexMap, j, c);
                            if(isEnd) {
                                String monthStr = dateStr.substring(12, 14);
                                monthStr = StringUtils.right(monthStr, length);
                                monthStr = StringUtils.leftPad(monthStr, length, "0");
                                result.append(monthStr);
                            }
                            break;
                        case 'N':
                            addToIndexMap(indexMap, j, c);
                            result.append(c);
                            if(isEnd) {
                                Integer[] range = indexMap.get(c + "");
                                serialNumberStart = range[0];
                                serialNumberEnd = range[1];
                            }
                            break;
                        default:
                    }
                } else {

                    result.append(c);
                }
                j++;
            }
            analyzeResult = result.toString();
        }

        private int addToIndexMap(Map<String, Integer[]> map, int i, char c) {
            if (!map.containsKey(c + "")) {
                map.put(c + "", new Integer[]{i, i + 1});
                return 1;
            } else {
                map.get(c + "")[1] = i + 1;
                return map.get(c + "")[1] - map.get(c + "")[0];
            }
        }

        public String getAnalyzeResult() {
            Date date = new Date();
            String dateStr = DateFormatUtils.format(date, "yyyyMMddHHmmss");
            System.out.println("===================="+dateStr);
            System.out.println("===================="+currentDateStr);
            if (!dateStr.equals(this.currentDateStr)) {
                this.currentDateStr = dateStr;
                initData();
            }
            return analyzeResult;
        }
    }


}


