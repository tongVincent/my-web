package com.twx.test.groovy

import com.twx.core.util.json.other.ClassToJsonUtil

/**
 * Created by vincent.tong on 2017/2/24.
 */
String className = "Photo_Document_App";
String json = """
{
    "id": 1,
    "stage_name": "水电阶段",
    "important_process": true,
    "create_date": "2016-07-18 18:33:30",
    "operations": ["通过"],
    "status": "待审核",
    "process_photo_documents": [
        {
            "id": 1,
            "stage_name": "水电阶段",
            "node_name": "材料验收及码放",
            "process_name": "请及时整改，并注意材料的保护。",
            "creator": {
                "name": "李时珍",
                "role": "监理"
            },
            "create_date": "2016-07-18 18:33:30",
            "operations": ["修改", "不通过"],
            "status": "未通过",
            "photo_groups": [
                {
                    "date": "2016-11-17 20:30:30",
                    "note": "备注",
                    "photos": [
                        {
                            "id": 1,
                            "name":"b",
                            "url":"project/photos/b.jpg",
                            "can_delete": true
                        }
                    ]
                }
            ],
            "records": [
                {
                    "creator": {
                        "name": "李时珍",
                        "role": "监理",
                        "portrait_url": "a.jpg"
                    },
                    "date": "2016-11-17 20:30:30",
                    "note": "备注"
                }
            ]
        }
    ]
}
""";
println(ClassToJsonUtil.fromJson(json, className));

println()