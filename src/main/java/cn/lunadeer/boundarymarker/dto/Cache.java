package cn.lunadeer.boundarymarker.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cache {
    private Map<String, Map<String, Area>> areas = new HashMap<>(); // <world, <name, Area>>
}
