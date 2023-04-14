package com.example.batch.entity;

/**
 * @author chenzhiqin
 * @date 13/4/2023 16:41
 * @info XX
 */

import lombok.Data;
import lombok.ToString;

/**
 * @Author : JCccc
 * @CreateTime : 2020/3/17
 * @Description :
 **/

@Data
@ToString
public class BlogInfo {

    private Integer id;
    private String blogAuthor;
    private String blogUrl;
    private String blogTitle;
    private String blogItem;
    
}