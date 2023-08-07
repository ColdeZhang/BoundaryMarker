package cn.lunadeer.boundarymarker.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Boolean animal_killing = false;     // √ 对动物造成伤害
    private Boolean use = false;                // √ 是否可以使用功能方块（工作台、铁砧、信标、附魔、蛋糕等）
    private Boolean bed = false;                // 是否可以使用床
    private Boolean container = false;          // √ 是否可以使用容器
    private Boolean destroy = false;            // √ 是否可以破坏方块
    private Boolean red_stone = false;          // √ 是否可以使用红石元件
    private Boolean door = false;               // √ 是否可以使用门
    private Boolean dye = false;                // √ 是否可以染色
    private Boolean throwing = false;           // √ 是否可以投掷东西
    private Boolean plant = false;              // √ 是否可以种植
    private Boolean hook = false;               // 是否可以使用钩子
    private Boolean ignite = false;             // √ 是否可以点火
    private Boolean leash = false;              // 是否可以使用栓绳
    private Boolean mob_killing = false;        // √ 是否可以攻击怪物
    private Boolean move = false;               // √ 是否可以移动
    private Boolean name_tag = false;           // 是否可以使用命名牌
    private Boolean place = false;              // √ 是否可以放置方块
    private Boolean riding = false;             // √ 是否可以骑乘
    private Boolean shear = false;              // 是否可以剪羊毛
    private Boolean shoot = false;              // 是否可以射箭
    private Boolean hopper = false;             // 特殊容器（漏斗、漏斗矿车、发射器、投掷器）
    private Boolean trade = false;              // √ 是否可以交易
    private Boolean vehicle_destroy = false;    // 是否可以破坏载具
    private Boolean harvest = false;            // 是否可以收获
}
