package com.com.example.inf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author chenzhiqin
 * @date 31/3/2023 14:02
 * @info XX
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User  implements Serializable {

    private String name;

    private int age;
}
