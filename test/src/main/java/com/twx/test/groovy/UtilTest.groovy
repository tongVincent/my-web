package com.twx.test.groovy

import com.twx.core.util.json.other.ClassToJsonUtil

/**
 * Created by vincent.tong on 2017/2/24.
 */
String className = "change_order";
String json = """
{
                "change_order_id": 1,
                "current_stage": "水电阶段",
                "type": "衣柜洽商单",
                "change_order_status": "已审核",
                "postpone_days": 5,
                "remark": "备注",
                "create_date": "2016-07-14 13:30:20",
                "creator": {
                    "name": "李时珍",
                    "role": "监理",
                    "portrait_url": "a.jpg"
                },
                "current_operator": [
                    {
                        "name": "李时珍",
                        "role": "监理",
                        "portrait_url": "a.jpg"
                    }
                ],
                "photos": [
                    {
                        "name": "设计变更",
                        "url": "qc7/het/178457528.jpg"
                    }
                ],
                "details": [
                    {
                        "change_type": "增项",
                        "content": "橱柜（吊柜/套餐标配）",
                        "unit": "M",
                        "quantity": 1.02,
                        "remark": "备注"
                    }
                ]
            }
""";
println(ClassToJsonUtil.fromJson(json, className));

println()